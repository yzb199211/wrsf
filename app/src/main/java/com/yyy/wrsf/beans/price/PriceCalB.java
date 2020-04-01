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

    private int deliverType;
    private int recRegion;
    private int sendRegion;
    private int sendType;
    private Long size;
    private int transCompanyRecNo;
    private int transType;
    private Long weight;

    public int getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(int deliverType) {
        this.deliverType = deliverType;
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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
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

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }
}
