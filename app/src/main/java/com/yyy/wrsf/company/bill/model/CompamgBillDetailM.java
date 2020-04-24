package com.yyy.wrsf.company.bill.model;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;

public class CompamgBillDetailM extends BaseM {
    public PagerRequestBean PageParams(int page, int size, String month, int customerid,int customerType) {
        PagerRequestBean pagerRequestBean = new PagerRequestBean();
        pagerRequestBean.setPageIndex(page);
        pagerRequestBean.setPageSize(size);
        pagerRequestBean.setBeginTime(month);
        pagerRequestBean.setQueryParam(companyBillB(customerid,customerType));
        return pagerRequestBean;
    }

    public CompanyBillB companyBillB(int customerid, int customerType) {
        CompanyBillB companyBillB = new CompanyBillB();
        companyBillB.setCustomerId(customerid);
        companyBillB.setCustomerType(customerType);
        return companyBillB;
    }

}
