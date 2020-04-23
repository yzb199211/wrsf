package com.yyy.wrsf.company.bill.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;

import java.util.List;

public interface ICompanyBillV extends ILoadingV {
    String getMonth();

    String getField();

    String getOrder();

    void addList(List<CompanyBillB> list);

    void refreshList();

    void loadMore(boolean canLoad);
}
