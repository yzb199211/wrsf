package com.yyy.wrsf.enums;

import java.math.BigDecimal;

/**
 * [description]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/23
 */
public enum QianTypeEnum {
    NO(1,"无需签单",BigDecimal.ZERO),

    PAPER(2,"纸质签收单",new BigDecimal(5)),

    ELECTRON(3,"电子签收单",new BigDecimal(3));

    private Integer qianType;
    private String desc;
    private BigDecimal fee;

    QianTypeEnum(Integer qianType,String desc,BigDecimal fee){
        this.qianType = qianType;
        this.desc = desc;
        this.fee = fee;
    }

    public Integer getQianType() {
        return qianType;
    }

    public void setQianType(Integer qianType) {
        this.qianType = qianType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
}
