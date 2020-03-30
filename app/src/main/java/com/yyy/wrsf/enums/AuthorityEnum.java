package com.yyy.wrsf.enums;

public enum AuthorityEnum {
    COMPANY("COMPANY"), SHOP("SHOP"), MEMBER("MEMBER"), STAFF("STAFF");
    private String name;

    AuthorityEnum(String name) {
        this.name = name;
    }
}
