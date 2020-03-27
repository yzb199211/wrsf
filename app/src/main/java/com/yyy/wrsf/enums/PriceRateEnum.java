package com.yyy.wrsf.enums;

import java.math.BigDecimal;

/**
 * [费率枚举]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/23
 */
public enum PriceRateEnum {
    BAO_RATE(new BigDecimal(0.005),"保价服务费率",new BigDecimal(50000),new BigDecimal(10)),

    DAI_RATE(new BigDecimal(0.005),"代收货款费率",null,new BigDecimal(10));

    private BigDecimal rate;
    private String  desc;
    private BigDecimal highLimit;
    private BigDecimal lowPrice;

    PriceRateEnum(BigDecimal rate,String desc,BigDecimal highLimit,BigDecimal lowPrice){
        this.rate = rate;
        this.desc = desc;
        this.highLimit = highLimit;
        this.lowPrice = lowPrice;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getHighLimit() {
        return highLimit;
    }

    public void setHighLimit(BigDecimal highLimit) {
        this.highLimit = highLimit;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }
}
