package com.yyy.wrsf.base;

import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

public class BaseSelectBean implements IPickerViewData {
    private String data;

    public BaseSelectBean(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getPickerViewText() {
        return data;
    }
}
