package com.yyy.wrsf.beans.filter;

public class OrderCollectionTotalF {

    /**
     * beginTime : string
     * transCompanyRecNo : 0
     * transShopRecNo : 0
     */

    private String beginTime;
    private Integer transCompanyRecNo;
    private Integer transShopRecNo;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public int getTransCompanyRecNo() {
        return transCompanyRecNo;
    }

    public void setTransCompanyRecNo(int transCompanyRecNo) {
        this.transCompanyRecNo = transCompanyRecNo;
    }

    public int getTransShopRecNo() {
        return transShopRecNo;
    }

    public void setTransShopRecNo(int transShopRecNo) {
        this.transShopRecNo = transShopRecNo;
    }
}
