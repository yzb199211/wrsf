package com.yyy.wrsf.mine.month.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.month.MonthB;

import java.util.List;

public interface IMonthApplyV extends ILoadingV {
    void forbidLoad(boolean b);
    void refreshList();
    void setList(List<MonthB> list);
    int getStatus();
}
