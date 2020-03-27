package com.yyy.wrsf.enums;

/**
 * [运输方式]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/21
 */
public enum  TransTypeEnum {

    PRICE_COMMON(1,"普件"),

    PRICE_TWO(2,"次日达");


    private Integer transType;
    private String desc;

    TransTypeEnum(Integer transType,String desc){
        this.transType = transType;
        this.desc = desc;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
