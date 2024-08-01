package com.fushun.framework.security.constant;


public enum PlatformEnum {

    MANAGE_BACKSTAGE("后台管理系统"),
    XCX("小程序");

    private String desc;

    PlatformEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean isXCX(String platform) {
        return XCX.name().equalsIgnoreCase(platform);
    }

    public static boolean isManageBackstage(String platform) {
        return MANAGE_BACKSTAGE.name().equalsIgnoreCase(platform);
    }
}
