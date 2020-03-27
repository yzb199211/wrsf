package com.yyy.wrsf.enums.util;

import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

import java.io.Serializable;

public class EnumEntity implements Serializable , IPickerViewData {

    private static final long serialVersionUID = 7860724585801435210L;
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

    @Override
    public String getPickerViewText() {
        return name;
    }
}
