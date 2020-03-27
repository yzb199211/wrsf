package com.yyy.wrsf.enums;

/**
 * [订单支付状态枚举]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/23
 */
public enum PayStatusEnum {
    UNPAID(0,"未付款"),
    PAID(1,"已付款"),
    WAIT_PAID(2,"等待付款"),
    WAIT_ADD(3,"等待补款"),
    PAID_CANCEL(4,"支付取消");

    private Integer payStatus;
    private String desc;

    PayStatusEnum(Integer payStatus,String desc){
        this.payStatus = payStatus;
        this.desc = desc;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
