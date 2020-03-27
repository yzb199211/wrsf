package com.yyy.wrsf.enums;

/**
 * [短信模板枚举]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/6
 */
public enum SmsTemp {

    REGISTER_TEMP(95886,"注册验证码模板"),
    BACK_TEMP(95888,"找回密码验证码"),
    LOGIN_TEMP(95889,"快捷登录验证码"),
    MEMBER_TH(46602,"提货发送客户消息模板"),
    MEMBER_SX(46601,"到货发送客户消息模板"),
    MEMBER_ZT(46600,"自提发送客户消息模板"),
    DRIVER_TH(45897,"驾驶员提货短信模板");

    private Integer tempId;
    private String desc;

    SmsTemp(Integer tempId,String desc){
        this.tempId = tempId;
        this.desc = desc;
    }

    public Integer getTempId() {
        return tempId;
    }

    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
