package com.yyy.wrsf.enums;

/**
 * [送货方式]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/21
 */
public enum SendTypeEnum {

    ZI_TI(1,"自提"),
    ZHI_SONG(2,"直送");

    private Integer sendType;
    private String desc;

    SendTypeEnum(Integer sendType,String desc){
        this.sendType = sendType;
        this.desc = desc;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
