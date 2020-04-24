package com.yyy.wrsf.mine.bill.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.company.bill.CompanyBillDetailB;

import java.util.List;

public interface IBillMonthDetailV extends ILoadingV {
    String getMonth();

    int getCustomerId();

    int getCustomerTypeId();

    int getCompanyId();

    void setLoad(boolean canLoad);

    void addList(List<CompanyBillDetailB> list);

    void refreshList();
}
