package com.yyy.wrsf.company.bill.view;

import com.yyy.wrsf.base.view.ILoadingV;

import java.util.List;

public interface ICompanyBillDetailV extends ILoadingV {
    String getMonth();

    int getCustomerId();
    int getCustomerTypeId();

    void setLoad(boolean canLoad);

//    void addList(List<>);

    void refreshList();
}
