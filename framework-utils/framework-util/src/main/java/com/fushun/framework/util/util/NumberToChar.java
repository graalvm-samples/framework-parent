package com.fushun.framework.util.util;

public class NumberToChar {

    public static String numberToChar(int intInput) {
        String si = String.valueOf(intInput);
        String sd = "";
        if (si.length() == 1) // 個
        {
            sd += getChar(intInput);
            return sd;
        } else if (si.length() == 2)// 十
        {
            if (si.substring(0, 1).equals("1"))
                sd += "十";
            else
                sd += (getChar(intInput / 10) + "十");
            sd += numberToChar(intInput % 10);
        } else if (si.length() == 3)// 百
        {
            sd += (getChar(intInput / 100) + "百");
            if (String.valueOf(intInput % 100).length() < 2)
                sd += "零";
            sd += numberToChar(intInput % 100);
        } else if (si.length() == 4)// 千
        {
            sd += (getChar(intInput / 1000) + "千");
            if (String.valueOf(intInput % 1000).length() < 3)
                sd += "零";
            sd += numberToChar(intInput % 1000);
        } else if (si.length() == 5)// 萬
        {
            sd += (getChar(intInput / 10000) + "萬");
            if (String.valueOf(intInput % 10000).length() < 4)
                sd += "零";
            sd += numberToChar(intInput % 10000);
        }

        return sd;
    }

    private static String getChar(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 7:
                sd = "七";
                break;
            case 8:
                sd = "八";
                break;
            case 9:
                sd = "九";
                break;
            default:
                break;
        }
        return sd;
    }
}
