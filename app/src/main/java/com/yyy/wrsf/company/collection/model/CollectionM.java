package com.yyy.wrsf.company.collection.model;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.beans.filter.OrderCollectionTotalF;
import com.yyy.wrsf.beans.filter.OrderFilterB;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;

public class CollectionM extends BaseM {
    public OrderCollectionTotalF getParams(String month, int companyid) {
        OrderCollectionTotalF orderCollectionTotalF = new OrderCollectionTotalF();
        orderCollectionTotalF.setBeginTime(month);
//        orderCollectionTotalF.setTransCompanyRecNo(companyid);
        return orderCollectionTotalF;
    }

    public PagerRequestBean<OrderFilterB> getParams(int pageIndex, int pageSize, String month, int type) {
        PagerRequestBean<OrderFilterB> pagerRequestBean = new PagerRequestBean<>();
        pagerRequestBean.setBeginTime(month);
        pagerRequestBean.setPageIndex(pageIndex);
        pagerRequestBean.setPageSize(pageSize);
        pagerRequestBean.setQueryParam(getQueryParams(type));
        return pagerRequestBean;
    }

    private OrderFilterB getQueryParams(int type) {
        OrderFilterB orderFilterB = new OrderFilterB();
        orderFilterB.setDaiStatus(type);
        return orderFilterB;
    }
}
