package com.yyy.wrsf.company.month.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.month.CustomerMonthB;

import java.util.List;

public interface IMonthV extends ILoadingV {
    String getFilter();

    void refreshList();

    void addList(List<CustomerMonthB> list);

    void stopLoadMore();

}
