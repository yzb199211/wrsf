package com.yyy.wrsf.model;

public class ShippingAddValueModel {
    private int insureValue;
    private int insureFee;
    private int colletionValue;
    private int colletionFee;
    private int signType;
    private int signFee;
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


    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
    }

    public int getColletionValue() {
        return colletionValue;
    }

    public void setColletionValue(int colletionValue) {
        this.colletionValue = colletionValue;
    }

    public int getColletionFee() {
        return colletionFee;
    }

    public void setColletionFee(int colletionFee) {
        this.colletionFee = colletionFee;
    }

    public int getSignFee() {
        return signFee;
    }

    public void setSignFee(int signFee) {
        this.signFee = signFee;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }
}
