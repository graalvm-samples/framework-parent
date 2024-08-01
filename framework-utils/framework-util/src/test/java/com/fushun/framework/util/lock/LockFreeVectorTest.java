package com.fushun.framework.util.lock;

import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 测试 {@link LockFreeVector}
 */
public class LockFreeVectorTest {

    @Test
    public void test() throws InterruptedException {
        LockFreeVector<Integer> vector=new LockFreeVector<>();

        List<Thread> threadList=new ArrayList<>();
        for (int k = 0; k < 10; k++) {
            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        vector.push_back(i);
                    }
                }
            });
            t.start();
            threadList.add(t);
        }

        for( Thread t : threadList){
            t.join();
        }

        int count=0;
        for (int i = 0; i <vector.size(); i++) {
            if(ObjectUtils.isEmpty(vector.get(i))){
                count=i;
                break;
            }
        }
        System.out.println("vector count："+count);
    }
}