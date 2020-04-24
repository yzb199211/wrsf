package com.yyy.wrsf.company.bill.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.company.bill.CompanyBillDetailB;

import java.util.List;

public interface ICompanyBillDetailV extends ILoadingV {
    String getMonth();

    int getCustomerId();
    int getCustomerTypeId();

    void setLoad(boolean canLoad);

    void addList(List<CompanyBillDetailB> list);

    void refreshList();
}
