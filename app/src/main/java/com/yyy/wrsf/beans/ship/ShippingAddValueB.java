package com.yyy.wrsf.beans.ship;

import android.text.TextUtils;

import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;

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

    public double getTotal() {
        try {
            BigDecimal b1 = new BigDecimal(insureFee + "");
            BigDecimal b2 = new BigDecimal(colletionFee + "");
            BigDecimal b3 = new BigDecimal(signFee + "");
            return b1.add(b2).add(b3).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public String getData() {
        String data = "";
        if (insureValue != 0) {
            data = data + "保价：￥" + insureValue;
        }
        if (colletionValue != 0) {
            data = data + (TextUtils.isEmpty(data) ? "" : "/") + "代收：￥" + colletionValue;
        }
        if (signType == 2) {
            data = data + (TextUtils.isEmpty(data) ? "" : "/") + BaseApplication.getInstance().getString(R.string.sign_back_paper);
        } else if (signType == 3) {
            data = data + (TextUtils.isEmpty(data) ? "" : "/") + BaseApplication.getInstance().getString(R.string.sign_back_electronic);
        }
        if (receiveType == 1) {
            data = data + (TextUtils.isEmpty(data) ? "" : "/") + BaseApplication.getInstance().getString(R.string.send_notice);

        }
        return data;
    }
}
