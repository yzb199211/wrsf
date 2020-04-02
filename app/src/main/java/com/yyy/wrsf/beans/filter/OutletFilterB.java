package com.yyy.wrsf.beans.filter;

import java.io.Serializable;

public class OutletFilterB implements Serializable {

    /**
     * detailAdd : string
     * person : 0
     * personName : string
     * recNo : 0
     * recQu : string
     * recTel : string
     * remark : string
     * sendTel : string
     * shopCode : string
     * shopName : string
     * shopStatus : 0
     * transCompanyRecno : 0
     */

    private String detailAdd;
    private Integer person;
    private String personName;
    private Integer recNo;
    private String recQu;
    private String recTel;
    private String remark;
    private String sendTel;
    private String shopCode;
    private String shopName;
    private Integer shopStatus;
    private Integer transCompanyRecno;

    public String getDetailAdd() {
        return detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getRecNo() {
        return recNo;
    }

    public void setRecNo(Integer recNo) {
        this.recNo = recNo;
    }

    public String getRecQu() {
        return recQu;
    }

    public void setRecQu(String recQu) {
        this.recQu = recQu;
    }

    public String getRecTel() {
        return recTel;
    }

    public void setRecTel(String recTel) {
        this.recTel = recTel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendTel() {
        return sendTel;
    }

    public void setSendTel(String sendTel) {
        this.sendTel = sendTel;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }

    public Integer getTransCompanyRecno() {
        return transCompanyRecno;
    }

    public void setTransCompanyRecno(Integer transCompanyRecno) {
        this.transCompanyRecno = transCompanyRecno;
    }
}
