package com.yyy.wrsf.mine.month.model;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.beans.filter.MonthFilterB;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;

public class MonthApplyM extends BaseM {
    public MonthFilterB getMonthFilter(int status) {
        MonthFilterB monthFilterB = new MonthFilterB();
        monthFilterB.setCheckFinish(status);
        return monthFilterB;
    }
}
