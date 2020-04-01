package com.yyy.wrsf.model.price;

import java.math.BigDecimal;

public class PriceBackB {

    /**
     * contractTotal : 0
     * fixTotal : 0
     * mixTotal : 0
     * picTotal : 0
     * price : 0
     * sendTotal : 0
     * transCustomerMonthRecNo : 0
     * transTotal : 0
     */

    private double contractTotal;
    private double fixTotal;
    private double mixTotal;
    private double picTotal;
    private double price;
    private double sendTotal;
    private int transCustomerMonthRecNo;
    private double transTotal;
    private int transRecShopRecNo;
    private int transShopRecNo;

    public double getContractTotal() {
        return contractTotal;
    }

    public void setContractTotal(double contractTotal) {
        this.contractTotal = contractTotal;
    }

    public double getFixTotal() {
        return fixTotal;
    }

    public void setFixTotal(double fixTotal) {
        this.fixTotal = fixTotal;
    }

    public double getMixTotal() {
        return mixTotal;
    }

    public void setMixTotal(double mixTotal) {
        this.mixTotal = mixTotal;
    }

    public double getPicTotal() {
        return picTotal;
    }

    public void setPicTotal(double picTotal) {
        this.picTotal = picTotal;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSendTotal() {
        return sendTotal;
    }

    public void setSendTotal(double sendTotal) {
        this.sendTotal = sendTotal;
    }

    public int getTransCustomerMonthRecNo() {
        return transCustomerMonthRecNo;
    }

    public void setTransCustomerMonthRecNo(int transCustomerMonthRecNo) {
        this.transCustomerMonthRecNo = transCustomerMonthRecNo;
    }

    public double getTransTotal() {
        return transTotal;
    }

    public void setTransTotal(double transTotal) {
        this.transTotal = transTotal;
    }

    public int getTransRecShopRecNo() {
        return transRecShopRecNo;
    }

    public void setTransRecShopRecNo(int transRecShopRecNo) {
        this.transRecShopRecNo = transRecShopRecNo;
    }

    public int getTransShopRecNo() {
        return transShopRecNo;
    }

    public void setTransShopRecNo(int transShopRecNo) {
        this.transShopRecNo = transShopRecNo;
    }
}
