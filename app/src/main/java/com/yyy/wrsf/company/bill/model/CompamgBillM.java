package com.yyy.wrsf.company.bill.model;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;

public class CompamgBillM extends BaseM {
    public PagerRequestBean PageParams(int page, int size, String field, String order, String beginTime) {
        PagerRequestBean pagerRequestBean = new PagerRequestBean();
        pagerRequestBean.setPageIndex(page);
        pagerRequestBean.setPageSize(size);
        pagerRequestBean.setSortField(field);
        pagerRequestBean.setSortOrder(order);
        pagerRequestBean.setBeginTime(beginTime);
        return pagerRequestBean;
    }

}
