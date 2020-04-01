package com.yyy.wrsf.beans.address;

import android.text.TextUtils;

import java.io.Serializable;

public class AddressB implements Serializable {

    /**
     * companyName : string
     * contractPerson : string
     * contractTel : string
     * detailAdd : string
     * firstAdd : string
     * platMemberRecNo : 0
     * recNo : 0
     * secondAdd : string
     * thirdAdd : string
     */

    private String companyName;
    private String contractPerson;
    private String contractTel;
    private String detailAdd;
    private String firstAdd;
    private int platMemberRecNo;
    private int recNo;
    private String secondAdd;
    private String thirdAdd;
    private int firstId;
    private int secondId;
    private int thirdId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContractPerson() {
        return contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson;
    }

    public String getContractTel() {
        return contractTel;
    }

    public void setContractTel(String contractTel) {
        this.contractTel = contractTel;
    }

    public String getDetailAdd() {
        return detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public String getFirstAdd() {
        return firstAdd;
    }

    public void setFirstAdd(String firstAdd) {
        this.firstAdd = firstAdd;
    }

    public int getPlatMemberRecNo() {
        return platMemberRecNo;
    }

    public void setPlatMemberRecNo(int platMemberRecNo) {
        this.platMemberRecNo = platMemberRecNo;
    }

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public String getSecondAdd() {
        return secondAdd;
    }

    public void setSecondAdd(String secondAdd) {
        this.secondAdd = secondAdd;
    }

    public String getThirdAdd() {
        return thirdAdd;
    }

    public void setThirdAdd(String thirdAdd) {
        this.thirdAdd = thirdAdd;
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
        return thirdId;
    }

    public void setThirdId(int thirdId) {
        this.thirdId = thirdId;
    }

    public String getWholeAddress() {
        return (TextUtils.isEmpty(getFirstAdd()) ? "" : getFirstAdd() + "\u3000")
                + (TextUtils.isEmpty(getSecondAdd()) ? "" : getSecondAdd() + "\u3000")
                + (TextUtils.isEmpty(getThirdAdd()) ? "" : getThirdAdd() + "\u3000")
                + (TextUtils.isEmpty(getDetailAdd()) ? "" : getDetailAdd());
    }
}
