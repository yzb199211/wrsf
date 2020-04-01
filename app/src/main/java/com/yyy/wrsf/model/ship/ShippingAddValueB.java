package com.yyy.wrsf.model.ship;

import com.yyy.wrsf.mine.shipping.ShipUtil;

import java.math.BigDecimal;

public class ShippingAddValueB {
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
    public double getTotal(){
        try {
            BigDecimal b1 = new BigDecimal(insureFee + "");
            BigDecimal b2 = new BigDecimal(colletionFee + "");
            BigDecimal b3 = new BigDecimal(signFee + "");
            return b1.add(b2).add(b3).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (ArithmeticException e) {
            return 0;
        }
    }
}
