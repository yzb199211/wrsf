package com.yyy.wrsf.enums;

import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.beans.publicm.PublicBean;
import com.yyy.wrsf.enums.util.EnumEntity;
import com.yyy.wrsf.enums.util.EnumUtils;

import java.util.ArrayList;
import java.util.List;

public enum StatusEnum {
    Normal(0, BaseApplication.getInstance().getString(R.string.common_normal)),
    STOP(1, BaseApplication.getInstance().getString(R.string.common_stop));

    private Integer type;
    private String name;

    StatusEnum(Integer type, String name) {
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

    public static String getName(Integer type) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.getType().equals(type))
                return statusEnum.getName();
        }
        return "";
    }

    public static List<PublicBean> getStatus() {
        List<PublicBean> list = new ArrayList<>();
        List<EnumEntity> enumEntityList = EnumUtils.getEnumList(StatusEnum.values(), StatusEnum::getType, StatusEnum::getName);
        for (EnumEntity item : enumEntityList) {
            list.add(new PublicBean(item.getId(), item.getName()));
        }
        return list;
    }
}
