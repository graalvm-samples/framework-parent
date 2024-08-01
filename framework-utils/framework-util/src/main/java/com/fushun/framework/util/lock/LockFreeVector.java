package com.fushun.framework.util.lock;

import org.springframework.util.ObjectUtils;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 自己实现的 线程安全数组集合，
 *
 * 偏学习，无锁机制的线程安全对象实现方式
 *   无锁的策略使用一种叫作比较交换（CAS，Compare And Swap）的技术来鉴别线程冲突
 *
 * 重写 {@link java.util.Vector}
 *
 * @param <E>
 */
public class LockFreeVector<E> {

    private final AtomicReferenceArray<AtomicReferenceArray<E>> buckets;

    //桶的小小
    private static final  int N_BUCKET=30;

    //第一个数组的大小，以后扩容的基数
    private static final  int FIRST_BUCKET_SIZE=8;

    //第一个数组的大小的，前导零个数
    private int zeroNumFirst=Integer.numberOfLeadingZeros(FIRST_BUCKET_SIZE);

    //数据写入操作对象
    private AtomicReference<Descriptor<E>> descriptor;

    //打印扩容日志
    private boolean debug=true;

    public LockFreeVector() {
        //初始化桶大小
        buckets=new AtomicReferenceArray<>(N_BUCKET);
        //初始化 第一额数组
        buckets.set(0,new AtomicReferenceArray<E>(FIRST_BUCKET_SIZE));
        //初始化数据操作对象
        descriptor=new AtomicReference<Descriptor<E>>(new Descriptor<>(0,null));
    }

    public void push_back(E e){
        Descriptor<E> desc;
        Descriptor<E> newd;

        do{
            desc=descriptor.get();
            //先将数据写入数组，是为了防止上一个线程设置完descriptor后，还没来得及执行58行的写入，因此做一次预防性操作
            desc.completeWrite();

            //通过当前Vector的大小（desc.size），计算新的元素应该落入那个数组。这里使用了位运算进行计算。
            // 也许你会觉得这几行代码看起来有些奇怪，我的解释如下：LockFreeVector每次都会成倍的扩容。
            //它的第1个数组长度为8，第2个就是16，第3个就是32，依此类推。他们的二进制表示就是
            //
            //它们之和就是整个LockFreeVector的总大小，因此，如果每一个数组都恰好填满，那么总大小应该是类似这样的数值（以4个数组填满为例）
            //00000000 00000000 00000000 0111000;4个数组都恰好填满时的大小
            //
            //导致整个数字进位的最小条件，就是加上二进制的1000。而这个数字正好是8（FIRST_BUCKET_SIZE就是8）。这就是第#8行代码的意义。
            //它可以使得数组大小发生一次二进制的进位（如果不进位说明还在第一个数组），进位后前导零的数量就会发生变化。
            //而元素所在的数组，和pos(第#8行定义的变量)的前导零直接相关。
            //每进行一次数组扩容，它的前导零就是28个，以后逐级减1。
            //这就是第#9行获得pos前导零的原因。
            //第#10行，通过pos的前导零可以立即定位使用那个数组（也就是得到了bucketInd的值）
            //
            //第#11行 判断这个数组是否存在。如果不存在，则创建这个数组，大小为前一个数组的两倍。并把它设置导buckets中
            //
            //再看一下元素没有恰好填满的情况
            // 00000000 00000000 00000000 00001000;第一个数组大小，28个前导零
            // 00000000 00000000 00000000 00010000;第一个数组大小，27个前导零
            // 00000000 00000000 00000000 00100000;第一个数组大小，26个前导零
            // 00000000 00000000 00000000 00000001;第四个数组大小，只有一个元素
            //
            //那么总大小就是：
            // 00000000 00000000 00000000 00111001;第四个数组大小，只有一个元素
            //
            //总个数加上二进制1000后，得到：
            //00000000 00000000 00000000 01000001;
            //
            //显然，通过前导零可以定位导第4个数组。而剩余位，显然就表示元素在当前数组内的偏移量（也就是数组下标）。根据这个理论，我们就可以通过pos计算这个元素应该放在给定数组的那个位置。
            //通过第#19行代码，获得pos的除了第一位数字1以外的其他位的数值。因此，pos的前导零可以表示元素所在数组，而pos的后面几位，则表示元素在这个数组中的位置。
            // 由此第#19行代码就取得了元素的所在位置idx。

            int pos=desc.size+FIRST_BUCKET_SIZE;//#8
            int zeroNumPos=Integer.numberOfLeadingZeros(pos);//#9

            int bucketInd= zeroNumFirst - zeroNumPos;//#10

            if(buckets.get(bucketInd)==null){//#11
                //获得上一个倍数
                int newLen=2*buckets.get(bucketInd-1).length();
                if(debug){
                    System.out.println("New Length is:"+newLen);
                }
                //扩容数组
                buckets.compareAndSet(bucketInd,null,new AtomicReferenceArray<E>(newLen));
            }
            int idx=(0x80000000>>>zeroNumPos)^pos;//#19
            //创建需要写入的数据
            newd=new Descriptor<E>(desc.size+1,new WriteDescriptor<>(buckets.get(bucketInd),idx,null,e));
        }while (!descriptor.compareAndSet(desc,newd));
        //真实数据 写入
        descriptor.get().completeWrite();//#23
    }


    public E get(int index){
        int pos=index+FIRST_BUCKET_SIZE;
        int zeroNumPos=Integer.numberOfLeadingZeros(pos);
        int bucketInd=zeroNumFirst-zeroNumPos;
        int idx=(0x80000000>>>zeroNumPos)^pos;
        AtomicReferenceArray<E> array= buckets.get(bucketInd);
        if(ObjectUtils.isEmpty(array)){
           return null;
        }
        return array.get(idx);
    }

    public int size(){
        int length=buckets.length();
        int size=0;
        for (int i = 0; i < length; i++) {
            AtomicReferenceArray<E> array= buckets.get(i);
            if(ObjectUtils.isEmpty(array)){
                return size;
            }
            size+=array.length();
        }
        return size;
    }

    static class Descriptor<E>{
        public int size;

        volatile WriteDescriptor<E> writeop;

        public Descriptor(int size, WriteDescriptor<E> writeop) {
            this.size = size;
            this.writeop = writeop;
        }


        public void completeWrite(){
            WriteDescriptor<E> tmpOp=writeop;
            if(tmpOp!=null){
                tmpOp.doIt();
                writeop=null;//this is safe since all wirte to writeop use null as r_value;
            }
        }
    }

    static class WriteDescriptor<E> {
        public E oldV;
        public E newV;
        public AtomicReferenceArray<E> addr;
        public int addr_ind;

        public WriteDescriptor(AtomicReferenceArray<E> addr, int addr_ind,E oldV, E newV) {
            this.oldV = oldV;
            this.newV = newV;
            this.addr = addr;
            this.addr_ind = addr_ind;
        }

        public void doIt(){
            addr.compareAndSet(addr_ind,oldV,newV);
        }
    }

}
