package com.yyy.wrsf.beans.month;

import android.text.TextUtils;

public class MonthB {

    /**
     * companyName : string
     * contractPerson : string
     * contractPersonPics : string
     * contractTel : string
     * detailAdd : string
     * firstAdd : string
     * person : string
     * secondAdd : string
     * thirdAdd : string
     * transCompanyRecNo : 0
     * zhiZhao : string
     * zhiZhaoPics : string
     */
    private String transCompanyName;
    private String companyName;
    private String contractPersonPics;
    private String detailAdd;
    private String firstAdd;
    private String person;
    private String secondAdd;
    private String thirdAdd;
    private int transCompanyRecNo;
    private String zhiZhao;
    private String zhiZhaoPics;
    private int firstId;
    private int secondId;
    private int thridId;
    private Integer recNo;
    private Integer checkFinish;
    private String contractTel;

    public String getContractTel() {
        return contractTel;
    }

    public void setContractTel(String contractTel) {
        this.contractTel = contractTel;
    }

    public String getTransCompanyName() {
        return transCompanyName;
    }

    public void setTransCompanyName(String transCompanyName) {
        this.transCompanyName = transCompanyName;
    }

    public Integer getRecNo() {
        return recNo;
    }

    public void setRecNo(Integer recNo) {
        this.recNo = recNo;
    }

    public int getCheckFinish() {
        return checkFinish==null?-1:checkFinish;
    }

    public void setCheckFinish(Integer checkFinish) {
        this.checkFinish = checkFinish;
    }

    public String getCompanyName() {
        return TextUtils.isEmpty(companyName) ? "" : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContractPersonPics() {
        return TextUtils.isEmpty(contractPersonPics) ? "" : contractPersonPics;
    }

    public void setContractPersonPics(String contractPersonPics) {
        this.contractPersonPics = contractPersonPics;
    }


    public String getDetailAdd() {
        return TextUtils.isEmpty(detailAdd) ? "" : detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public String getFirstAdd() {
        return TextUtils.isEmpty(firstAdd) ? "" : firstAdd;
    }

    public void setFirstAdd(String firstAdd) {
        this.firstAdd = firstAdd;
    }

    public String getPerson() {
        return TextUtils.isEmpty(person) ? "" : person;
    }

    public void setPerson(String person) {
        this.person = person;
    }


    public String getSecondAdd() {
        return TextUtils.isEmpty(secondAdd) ? "" : secondAdd;
    }

    public void setSecondAdd(String secondAdd) {
        this.secondAdd = secondAdd;
    }

    public String getThirdAdd() {
        return TextUtils.isEmpty(thirdAdd) ? "" : thirdAdd;
    }

    public void setThirdAdd(String thirdAdd) {
        this.thirdAdd = thirdAdd;
    }

    public int getTransCompanyRecNo() {
        return transCompanyRecNo;
    }

    public void setTransCompanyRecNo(int transCompanyRecNo) {
        this.transCompanyRecNo = transCompanyRecNo;
    }


    public String getZhiZhao() {
        return TextUtils.isEmpty(zhiZhao) ? "" : zhiZhao;
    }

    public void setZhiZhao(String zhiZhao) {
        this.zhiZhao = zhiZhao;
    }

    public String getZhiZhaoPics() {
        return TextUtils.isEmpty(zhiZhaoPics) ? "" : zhiZhaoPics;
    }

    public void setZhiZhaoPics(String zhiZhaoPics) {
        this.zhiZhaoPics = zhiZhaoPics;
    }

    public int getFirstId() {
        return firstId;
    }

    public void setFirstId(int firstId) {
        this.firstId = firstId;
    }

    public int getSecondId() {
        return secondId;
    }

    public void setSecondId(int secondId) {
        this.secondId = secondId;
    }

    public int getThirdId() {
        return thridId;
    }

    public void setThirdId(int thirdId) {
        this.thridId = thirdId;
    }
}
