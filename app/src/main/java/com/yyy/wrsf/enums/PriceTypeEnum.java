package com.yyy.wrsf.enums;

/**
 * [计费方式枚举类]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/22
 */
public enum PriceTypeEnum {
    YUAN_KG(1,"元/kg"),
    YUAN_M(2,"元/m³");

    private Integer priceType;
    private String desc;

    PriceTypeEnum(Integer priceType,String desc){
        this.priceType = priceType;
        this.desc = desc;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
