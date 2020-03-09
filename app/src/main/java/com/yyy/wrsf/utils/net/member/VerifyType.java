package com.yyy.wrsf.utils.net.member;

public enum VerifyType {
    REGISTER("1"), BACK("2"), LOGIN("3");
    private final String code;

    VerifyType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
