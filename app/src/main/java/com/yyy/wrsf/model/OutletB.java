package com.yyy.wrsf.model;

import android.text.TextUtils;

public class OutletB {

    /**
     * recNo : 2
     * transCompanyRecno : 1
     * shopCode : DQ02
     * shopName : 丁桥网点2
     * detailAdd : 丁桥
     * sendTel : 17858956195
     * recTel : 17858956195
     * recQu : 1351,1444
     * person : 0
     * remark :
     */

    private int recNo;
    private int transCompanyRecno;
    private String shopCode;
    private String shopName;
    private String detailAdd;
    private String sendTel;
    private String recTel;
    private String recQu;
    private int person;
    private String remark;
    private int shopStatus;
    private String personName;

    /**
     * personName : string
     */

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public int getTransCompanyRecno() {
        return transCompanyRecno;
    }

    public void setTransCompanyRecno(int transCompanyRecno) {
        this.transCompanyRecno = transCompanyRecno;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return TextUtils.isEmpty(shopName) ? "" : shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDetailAdd() {
        return TextUtils.isEmpty(detailAdd) ? "" : detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public String getSendTel() {
        return TextUtils.isEmpty(sendTel) ? "" : sendTel;
    }

    public void setSendTel(String sendTel) {
        this.sendTel = sendTel;
    }

    public String getRecTel() {
        return TextUtils.isEmpty(recTel) ? "" : recTel;
    }

    public void setRecTel(String recTel) {
        this.recTel = recTel;
    }

    public String getRecQu() {
        return TextUtils.isEmpty(recQu) ? "" : recQu;
    }

    public void setRecQu(String recQu) {
        this.recQu = recQu;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public String getRemark() {
        return TextUtils.isEmpty(remark) ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(int shopStatus) {
        this.shopStatus = shopStatus;
    }

    public String getPersonName() {
        return TextUtils.isEmpty(personName) ? "" : personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
