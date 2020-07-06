package com.yyy.wrsf.beans.company.bill;

public class CompanyBillCollectionTotalB {

    /**
     * paidTotal : 0
     * unpaidTotal : 0
     */

    private Double paidTotal;
    private Double unpaidTotal;

    public Double getPaidTotal() {
        return paidTotal==null?0:paidTotal;
    }

    public void setPaidTotal(double paidTotal) {
        this.paidTotal = paidTotal;
    }

    public Double getUnpaidTotal() {
        return unpaidTotal==null?0:unpaidTotal;
    }

    public void setUnpaidTotal(double unpaidTotal) {
        this.unpaidTotal = unpaidTotal;
    }
}
