package com.yyy.wrsf.mine.month.persenter;

import android.content.Context;
import android.os.Handler;

import com.yyy.wrsf.mine.month.model.MonthApplyM;
import com.yyy.wrsf.mine.month.view.IMonthApplyV;

public class MonthApplyP implements IMonthApplyP {
   private MonthApplyM  monthApplyM;
   private IMonthApplyV iMonthApplyV;
    private boolean destroyFlag;
    private Handler handler = new Handler();
    public MonthApplyP(IMonthApplyV iMonthApplyV) {
        this.iMonthApplyV = iMonthApplyV;
        monthApplyM = new MonthApplyM();
    }

    @Override
    public void getData() {

    }
}
