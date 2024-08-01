package com.fushun.framework.util.util;

public class NumberUtil {


    /**
     * 获取i到j之间的随机数
     *
     * @param i
     * @param j
     */
    public static int getRandom(int i, int j) {
        return (int) (i + Math.random() * (j - i + 1));
    }

    /**
     * 获取指定长度的随机
     * @param i 开始数值
     * @param j 结束值
     * @param length 随机字符串长度
     * @return
     */
    public static String getRandom(int i, int j,int length){
        String random=String.valueOf(NumberUtil.getRandom(i,j));
        StringBuffer sb=new StringBuffer();
        for(int k=random.length();k<length;k++){
            sb.append("0");
        }
        sb.append(random);

        return sb.toString();
    }

}
