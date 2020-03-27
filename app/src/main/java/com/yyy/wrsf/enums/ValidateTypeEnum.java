package com.yyy.wrsf.enums;

/**
 * [验证码类型]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/6
 */
public enum ValidateTypeEnum {

    REGISTER_VALIDATE(1,"注册验证码"),

    FOUNDBACK_VALIDATE(2,"找回验证码"),

    FASTLOGIN_VALIDATE(3,"快捷登录验证码")
    ;

    private Integer type;

    private String desc;

    ValidateTypeEnum(Integer type,String desc){
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
