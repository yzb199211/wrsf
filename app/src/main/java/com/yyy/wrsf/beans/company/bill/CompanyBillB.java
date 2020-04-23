package com.yyy.wrsf.beans.company.bill;

import android.text.TextUtils;

import java.math.BigDecimal;

public class CompanyBillB {
    private Long contractTotal;//contractTotal(number,optional):总金额,
    private Long paid;         //paid(number,optional):已付金额,
    private Long unpaid;       // unpaid(number,optional):未付金额
    private String customer;   //customer(string,optional):客户,
    private int customerId;    //customerId(integer,optional):客户Id,
    private int customerType;  //customerType(integer,optional):客户类型,
    private String customerTypeName;
    private String month;      //month(string,optional):月份,
    private Integer orderCount;    //orderCount(integer,optional):订单数量,
    private String transCompanyName; //transCompanyName(string,optional):物流公司名称,
    private int transCompanyRecNo;   //transCompanyRecNo(integer,optional):物流公司Id,
    private int transShopRecNo;      //transShopRecNo(integer,optional):发货网点Id,

    public Long getContractTotal() {
        return contractTotal == null ? 0 : contractTotal;
    }

    public void setContractTotal(Long contractTotal) {
        this.contractTotal = contractTotal;
    }

    public Long getPaid() {
        return paid == null ? 0 : paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }

    public Long getUnpaid() {
        return unpaid == null ? 0 : unpaid;
    }

    public void setUnpaid(Long unpaid) {
        this.unpaid = unpaid;
    }

    public String getCustomer() {
        return TextUtils.isEmpty(customer) ? "" : customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerType() {
        return customerType;
    }

    public String getCustomerTypeName() {
        return TextUtils.isEmpty(customerTypeName) ? "" : customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getOrderCount() {
        return orderCount == null ? 0 : orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getTransCompanyName() {
        return transCompanyName;
    }

    public void setTransCompanyName(String transCompanyName) {
        this.transCompanyName = transCompanyName;
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
