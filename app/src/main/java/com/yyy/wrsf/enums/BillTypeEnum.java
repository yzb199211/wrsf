package com.yyy.wrsf.enums;

import com.yyy.wrsf.enums.util.EnumEntity;
import com.yyy.wrsf.enums.util.EnumUtils;
import com.yyy.wrsf.beans.publicm.PublicBean;

import java.util.ArrayList;
import java.util.List;

public enum BillTypeEnum {
    ELECTRONIC(1, "电子发票"),

    NORMAL(2, "纸质发票");
    private Integer type;
    private String name;

    BillTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getTypeName(Integer type) {
        for (BillTypeEnum billTypeEnum : BillTypeEnum.values()) {
            if (billTypeEnum.getType().equals(type))
                return billTypeEnum.getName();
        }
        return "";
    }

    public static List<PublicBean> getBillTypes() {
        List<PublicBean> list = new ArrayList<>();
        List<EnumEntity> enumEntityList = EnumUtils.getEnumList(BillTypeEnum.values(), BillTypeEnum::getType, BillTypeEnum::getName);
        for (EnumEntity item : enumEntityList) {
            list.add(new PublicBean(item.getId(), item.getName()));
        }
        return list;
    }
}
