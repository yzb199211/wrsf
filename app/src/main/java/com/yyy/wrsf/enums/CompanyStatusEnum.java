package com.yyy.wrsf.enums;

/**
 * [物流公司状态枚举]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/9
 */
public enum CompanyStatusEnum {

    UNSUBMIT(0,"未提交"),

    CHECKING(1,"审批中"),

    CONFIRM(4,"审批已通过"),

    REJECT(5,"审批未通过"),

    BAN(10,"禁用");

    private Integer status;

    private String desc;

    CompanyStatusEnum(Integer status,String desc){
        this.status = status;
        this.desc =desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
