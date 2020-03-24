package com.yyy.wrsf.model;

public class ShippingAddValueModel {
    private int insureValue;
    private int insureFee;
    private int colletionMoney;
    private int signType;
    private int signMoney;
    private int receiveType;

    public int getInsureValue() {
        return insureValue;
    }

    public void setInsureValue(int insureValue) {
        this.insureValue = insureValue;
    }

    public int getInsureFee() {
        return insureFee;
    }

    public void setInsureFee(int insureFee) {
        this.insureFee = insureFee;
    }

    public int getColletionMoney() {
        return colletionMoney;
    }

    public void setColletionMoney(int colletionMoney) {
        this.colletionMoney = colletionMoney;
    }

    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
    }

    public int getSignMoney() {
        return signMoney;
    }

    public void setSignMoney(int signMoney) {
        this.signMoney = signMoney;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }
}
