package com.yyy.wrsf.enums;

public enum NoticeRoleEnum {

    SHOP_ROLE(1,"网点"),
    COMPANY_ROLE(2,"物流公司"),
    MEMBER_ROLE(3,"平台会员");

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    NoticeRoleEnum(Integer id, String name){
        this.id=id;
        this.name=name;
    }

    public static String getNameByValue(Integer value){
        if (value == null) {
            return "";
        }
        for (NoticeRoleEnum noticeRoleEnum : NoticeRoleEnum.values()) {
            if (noticeRoleEnum.getId().equals(value)){
                return noticeRoleEnum.getName();
            }
        }
        return null;
    }
}
