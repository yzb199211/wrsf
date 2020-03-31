package com.yyy.wrsf.main.model;

import android.app.Activity;

import com.yyy.wrsf.enums.AuthorityEnum;

public class MainM {
    public boolean isCompany(String string) {
        switch (AuthorityEnum.valueOf(string)) {
            case STAFF:
            case SHOP:
            case COMPANY:
                return true;
            case MEMBER:
                return false;
            default:
                return false;
        }

    }
}
