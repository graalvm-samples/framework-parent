package com.fushun.framework.util.util;

/**
 * {@link MD5Util}
 */
public class MD5UtilTest {

    public void test(){
        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    String s = "123456";
                    System.out.println("原始：" + s);
                    System.out.println("MD5后：" + MD5Util.getMD5(s));
                }
            });
            t1.start();
        }
    }

}