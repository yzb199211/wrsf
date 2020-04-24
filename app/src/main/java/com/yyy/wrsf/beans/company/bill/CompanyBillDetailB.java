package com.yyy.wrsf.beans.company.bill;

public class CompanyBillDetailB {

    /**
     * transCompanyRecNo : 13
     * transCompanyName : 测试公司莫
     * recName : 工具人6
     * createDate : 2020-04-21 09:33:06
     * packNumber : 1
     * weight : 1000
     * size : 10
     * sendType : 1
     * sendTypeName : 上门提货
     * deliveryType : 1
     * deliverTypeName : 自提
     * contractNo : 2020042109330601
     * density : 0.1
     * unpaid : 10000
     * contractTotal : 10000
     */

    private int transCompanyRecNo;
    private String transCompanyName;
    private String recName;
    private String createDate;
    private int packNumber;
    private int weight;
    private double size;
    private int sendType;
    private String sendTypeName;
    private int deliveryType;
    private String deliverTypeName;
    private String contractNo;
    private double density;
    private double unpaid;
    private double contractTotal;

    public int getTransCompanyRecNo() {
        return transCompanyRecNo;
    }

    public void setTransCompanyRecNo(int transCompanyRecNo) {
        this.transCompanyRecNo = transCompanyRecNo;
    }

    public String getTransCompanyName() {
        return transCompanyName;
    }

    public void setTransCompanyName(String transCompanyName) {
        this.transCompanyName = transCompanyName;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getPackNumber() {
        return packNumber;
    }

    public void setPackNumber(int packNumber) {
        this.packNumber = packNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public String getSendTypeName() {
        return sendTypeName;
    }

    public void setSendTypeName(String sendTypeName) {
        this.sendTypeName = sendTypeName;
    }

    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliverTypeName() {
        return deliverTypeName;
    }

    public void setDeliverTypeName(String deliverTypeName) {
        this.deliverTypeName = deliverTypeName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(double unpaid) {
        this.unpaid = unpaid;
    }

    public double getContractTotal() {
        return contractTotal;
    }

    public void setContractTotal(double contractTotal) {
        this.contractTotal = contractTotal;
    }
}
