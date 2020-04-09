package com.yyy.wrsf.company.month.model;

import android.text.TextUtils;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.beans.filter.MonthFilterB;

public class MonthM extends BaseM {
    public MonthFilterB getMonthFilter(String filter) {
        if (TextUtils.isEmpty(filter)) return null;
        else {
            MonthFilterB monthFilterB = new MonthFilterB();
            monthFilterB.setContractPerson(filter);
            return monthFilterB;
        }
    }

}
