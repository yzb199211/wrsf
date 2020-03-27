package com.yyy.wrsf.enums;

public enum TradingStatusEnum {
    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    UNKNOWN(-1,"未知");

    private Integer value;
    private String name;

    private TradingStatusEnum(Integer value, String name){
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
