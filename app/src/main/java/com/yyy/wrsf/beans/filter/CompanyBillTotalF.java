package com.yyy.wrsf.beans.filter;

public class CompanyBillTotalF {

    /**
     * contractTotal : 0
     * customer : string
     * customerId : 0
     * customerType : 0
     * customerTypeName : string
     * month : 2020-04-24T02:01:46.227Z
     * orderCount : 0
     * paid : 0
     * transCompanyName : string
     * transCompanyRecNo : 0
     * transShopRecNo : 0
     * unpaid : 0
     */

    private Integer contractTotal;
    private String customer;
    private Integer customerId;
    private Integer customerType;
    private String customerTypeName;
    private String month;
    private Integer orderCount;
    private Integer paid;
    private String transCompanyName;
    private Integer transCompanyRecNo;
    private Integer transShopRecNo;
    private Integer unpaid;

    public Integer getContractTotal() {
        return contractTotal;
    }

    public void setContractTotal(Integer contractTotal) {
        this.contractTotal = contractTotal;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public String getMonth() {
        return month;
    }

    public CompanyBillTotalF setMonth(String month) {
        this.month = month;
        return this;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public String getTransCompanyName() {
        return transCompanyName;
    }

    public void setTransCompanyName(String transCompanyName) {
        this.transCompanyName = transCompanyName;
    }

    public Integer getTransCompanyRecNo() {
        return transCompanyRecNo;
    }

    public void setTransCompanyRecNo(Integer transCompanyRecNo) {
        this.transCompanyRecNo = transCompanyRecNo;
    }

    public Integer getTransShopRecNo() {
        return transShopRecNo;
    }

    public void setTransShopRecNo(Integer transShopRecNo) {
        this.transShopRecNo = transShopRecNo;
    }

    public Integer getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(Integer unpaid) {
        this.unpaid = unpaid;
    }
}
