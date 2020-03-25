package com.yyy.wrsf.model;

public class ShippingAddValueModel {
    private int insureValue;
    private double insureFee;
    private int colletionValue;
    private double colletionFee;
    private int signType;
    private double signFee;
    private int receiveType;

    public int getInsureValue() {
        return insureValue;
    }

    public void setInsureValue(int insureValue) {
        this.insureValue = insureValue;
    }

    public double getInsureFee() {
        return insureFee;
    }

    public void setInsureFee(double insureFee) {
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

    public double getColletionFee() {
        return colletionFee;
    }

    public void setColletionFee(double colletionFee) {
        this.colletionFee = colletionFee;
    }

    public double getSignFee() {
        return signFee;
    }

    public void setSignFee(double signFee) {
        this.signFee = signFee;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }
}
