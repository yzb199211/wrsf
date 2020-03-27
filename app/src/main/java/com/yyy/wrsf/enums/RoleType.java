package com.yyy.wrsf.enums;

/**
 * 角色类型枚举
 */
public enum RoleType {

    PLAT_MEMBER(4,"普通会员"),

    TRANS_COMPANY(2,"物流网点"),

    SHOP(3,"网点负责人"),

    SYSTEM(1,"系统用户"),

    SHOP_STAFF(5,"网点员工")
    ;

    private Integer type;

    private String desc;

    RoleType(Integer type,String desc){
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
