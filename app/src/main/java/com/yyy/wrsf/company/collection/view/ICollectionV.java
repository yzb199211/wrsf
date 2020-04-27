package com.yyy.wrsf.company.collection.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.OrderBean;
import com.yyy.wrsf.beans.company.bill.CompanyBillCollectionTotalB;

import java.util.List;

public interface ICollectionV extends ILoadingV {
    String getMonth();

    int getPayType();

    int getCompanyId();

    void addList(List<OrderBean> list);

    void refreshList();

    void setLoad(boolean canLoad);

    void setTotal(CompanyBillCollectionTotalB total);
}
