package com.yyy.wrsf.mine.bill.model;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.beans.person.PersonBillB;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;

public class BillMonthDetailM extends BaseM {
    public PagerRequestBean PageParams(int page, int size, String month, int companyid, int customerType) {
        PagerRequestBean pagerRequestBean = new PagerRequestBean();
        pagerRequestBean.setPageIndex(page);
        pagerRequestBean.setPageSize(size);
        pagerRequestBean.setBeginTime(month);
        pagerRequestBean.setQueryParam(personBillB(companyid, customerType));
        return pagerRequestBean;
    }

    public PersonBillB personBillB(int companyid,int customerType) {
        PersonBillB personBillB = new PersonBillB();
        personBillB.setTransCompanyRecNo(companyid);
        personBillB.setCustomerType(customerType);
        return personBillB;
    }

}
