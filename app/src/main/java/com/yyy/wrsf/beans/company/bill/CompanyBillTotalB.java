package com.yyy.wrsf.beans.company.bill;

public class CompanyBillTotalB {

    /**
     * unpaid : 5758612.02
     * paid : 0
     * contractTotal : 5758612.02
     */

    private double unpaid;
    private int paid;
    private double contractTotal;

    public double getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(double unpaid) {
        this.unpaid = unpaid;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public double getContractTotal() {
        return contractTotal;
    }

    public void setContractTotal(double contractTotal) {
        this.contractTotal = contractTotal;
    }
}
