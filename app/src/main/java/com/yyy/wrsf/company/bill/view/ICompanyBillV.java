package com.yyy.wrsf.company.bill.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.beans.company.bill.CompanyBillTotalB;

import java.util.List;

public interface ICompanyBillV extends ILoadingV {
    String getMonth();

    String getField();

    String getOrder();

    void addList(List<CompanyBillB> list);

    void setTotal(CompanyBillTotalB item);

    void refreshList();

    void loadMore(boolean canLoad);
}
