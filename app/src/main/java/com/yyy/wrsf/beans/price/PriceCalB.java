package com.yyy.wrsf.beans.price;

public class PriceCalB {

    /**
     * deliverType : 0
     * recRegion : 0
     * sendRegion : 0
     * sendType : 0
     * size : 0
     * transCompanyRecNo : 0
     * transType : 0
     * weight : 0
     */

    private int deliveryType;
    private int recRegion;
    private int sendRegion;
    private int sendType;
    private double size;
    private int transCompanyRecNo;
    private int transType;
    private int weight;

    public int getDeliverType() {
        return deliveryType;
    }

    public void setDeliverType(int deliverType) {
        this.deliveryType = deliverType;
    }

    public int getRecRegion() {
        return recRegion;
    }

    public void setRecRegion(int recRegion) {
        this.recRegion = recRegion;
    }

    public int getSendRegion() {
        return sendRegion;
    }

    public void setSendRegion(int sendRegion) {
        this.sendRegion = sendRegion;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getTransCompanyRecNo() {
        return transCompanyRecNo;
    }

    public void setTransCompanyRecNo(int transCompanyRecNo) {
        this.transCompanyRecNo = transCompanyRecNo;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
