package com.yyy.wrsf.enums;

/**
 * [description]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/21
 */
public enum RecTypeEnum {

    SHANG_MEN(1,"上门提货"),

    SHOP_SEND(2,"网点自寄");

    private Integer recType;
    private String desc;

    RecTypeEnum(Integer recType,String desc){
        this.recType = recType;
        this.desc = desc;
    }

    public Integer getRecType() {
        return recType;
    }

    public void setRecType(Integer recType) {
        this.recType = recType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
