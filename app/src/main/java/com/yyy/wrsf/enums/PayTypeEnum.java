package com.yyy.wrsf.enums;

/**
 * [支付方式]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/23
 */
public enum PayTypeEnum {
    PAY_NOW(1,"现付"),
    PAY_MONTH(2,"月结"),
    PAY_ARRIVAL(3,"到付");

    private Integer payType;
    private String desc;

    PayTypeEnum(Integer payType,String desc){
        this.payType = payType;
        this.desc = desc;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
