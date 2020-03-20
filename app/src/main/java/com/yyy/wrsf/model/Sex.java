package com.yyy.wrsf.model;

import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

public class Sex implements IPickerViewData {
        private String sex;
        private int id;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sex(String sex, int id) {
            this.sex = sex;
            this.id = id;
        }

        @Override
        public String getPickerViewText() {
            return sex;
        }
    }