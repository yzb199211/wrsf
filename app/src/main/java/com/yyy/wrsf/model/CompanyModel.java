package com.yyy.wrsf.model;

import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

import java.io.Serializable;

public class CompanyModel implements Serializable, IPickerViewData {

    /**
     * recNo : 1
     * companyName : 测试公司
     * fristAdd : 浙江
     * secondAdd : 嘉兴
     * thirdAdd : 海宁
     * detailAdd : 丁桥园区
     * person : 测试甲
     * contractPerson : 测试甲
     * contractTel : 17858956195
     * zhiZhao : 9123012330113087XD
     * companyStatus : 4
     * plantSystemAccountRecno : 30
     * plantSystemName : 测试
     * checkDate : 1582785414000
     */

    private int recNo;
    private String companyName;
    private String fristAdd;
    private String secondAdd;
    private String thirdAdd;
    private String detailAdd;
    private String person;
    private String contractPerson;
    private String contractTel;
    private String zhiZhao;
    private int companyStatus;
    private int plantSystemAccountRecno;
    private String plantSystemName;
    private long checkDate;

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFristAdd() {
        return fristAdd;
    }

    public void setFristAdd(String fristAdd) {
        this.fristAdd = fristAdd;
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

    public String getDetailAdd() {
        return detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
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

    public String getZhiZhao() {
        return zhiZhao;
    }

    public void setZhiZhao(String zhiZhao) {
        this.zhiZhao = zhiZhao;
    }

    public int getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(int companyStatus) {
        this.companyStatus = companyStatus;
    }

    public int getPlantSystemAccountRecno() {
        return plantSystemAccountRecno;
    }

    public void setPlantSystemAccountRecno(int plantSystemAccountRecno) {
        this.plantSystemAccountRecno = plantSystemAccountRecno;
    }

    public String getPlantSystemName() {
        return plantSystemName;
    }

    public void setPlantSystemName(String plantSystemName) {
        this.plantSystemName = plantSystemName;
    }

    public long getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(long checkDate) {
        this.checkDate = checkDate;
    }

    @Override
    public String getPickerViewText() {
        return companyName;
    }
}
