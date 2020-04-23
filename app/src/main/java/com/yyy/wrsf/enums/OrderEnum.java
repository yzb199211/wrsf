package com.yyy.wrsf.enums;

public enum OrderEnum {
    ASC("ASC"),DESC("DESC");
    private String order;

    OrderEnum(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }
}
