package com.yyy.wrsf.mine.bill.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.company.bill.CompanyBillTotalB;
import com.yyy.wrsf.beans.person.PersonBillB;

import java.util.List;

public interface IBillMonthV extends ILoadingV {
    String getMonth();

    String getField();

    String getOrder();

    void addList(List<PersonBillB> list);

    void setTotal(CompanyBillTotalB item);

    void refreshList();

    void loadMore(boolean canLoad);
}
