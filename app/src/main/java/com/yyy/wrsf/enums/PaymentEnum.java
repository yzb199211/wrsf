package com.yyy.wrsf.enums;

/**
 * 支付记录支付状态枚举
 */
public enum PaymentEnum {
    UNPAY(0,"未支付"),
    PAID(1,"已支付"),
    TIMEOUT(-1,"支付超时"),
    CANCLE(2,"取消");


    private Integer value;
    private String name;

    private PaymentEnum(Integer value, String name){
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

    public static String getNameByValue(Integer value) {
        if (value == null) {
            return "";
        }
        for (PaymentEnum paymentEnum : PaymentEnum.values()) {
            if (paymentEnum.getValue().equals(value))
                return paymentEnum.getName();
        }
        return null;
    }
}
