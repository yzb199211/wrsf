package com.yyy.wrsf.model;

import java.io.Serializable;

public class CompanyRegister implements Serializable {

    /**
     * companyName : string
     * contractPerson : string
     * contractPersonPics : string
     * contractTel : string
     * detailAdd : string
     * fristAdd : string
     * person : string
     * secondAdd : string
     * thirdAdd : string
     * zhiZhao : string
     * zhiZhaoPics : string
     */

    private String companyName;
    private String contractPerson;
    private String contractPersonPics;
    private String contractTel;
    private String detailAdd;
    private String fristAdd;
    private String person;
    private String secondAdd;
    private String thirdAdd;
    private String zhiZhao;
    private String zhiZhaoPics;

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

    public String getContractPersonPics() {
        return contractPersonPics;
    }

    public void setContractPersonPics(String contractPersonPics) {
        this.contractPersonPics = contractPersonPics;
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

    public String getFristAdd() {
        return fristAdd;
    }

    public void setFristAdd(String fristAdd) {
        this.fristAdd = fristAdd;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
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

    public String getZhiZhao() {
        return zhiZhao;
    }

    public void setZhiZhao(String zhiZhao) {
        this.zhiZhao = zhiZhao;
    }

    public String getZhiZhaoPics() {
        return zhiZhaoPics;
    }

    public void setZhiZhaoPics(String zhiZhaoPics) {
        this.zhiZhaoPics = zhiZhaoPics;
    }
}
