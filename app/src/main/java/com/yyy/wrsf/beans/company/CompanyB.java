package com.yyy.wrsf.beans.company;

import android.text.TextUtils;

import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

import java.io.Serializable;

public class CompanyB implements Serializable, IPickerViewData {

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
    private String checkDate;

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public String getCompanyName() {
        return TextUtils.isEmpty(companyName)?"":companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFristAdd() {
        return TextUtils.isEmpty(fristAdd)?"":fristAdd;
    }

    public void setFristAdd(String fristAdd) {
        this.fristAdd = fristAdd;
    }

    public String getSecondAdd() {
        return TextUtils.isEmpty(secondAdd)?"":secondAdd;
    }

    public void setSecondAdd(String secondAdd) {
        this.secondAdd = secondAdd;
    }

    public String getThirdAdd() {
        return TextUtils.isEmpty(thirdAdd)?"":thirdAdd;
    }

    public void setThirdAdd(String thirdAdd) {
        this.thirdAdd = thirdAdd;
    }

    public String getDetailAdd() {
        return TextUtils.isEmpty(detailAdd)?"":detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public String getPerson() {
        return TextUtils.isEmpty(person)?"":person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getContractPerson() {
        return TextUtils.isEmpty(contractPerson)?"":contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson;
    }

    public String getContractTel() {
        return TextUtils.isEmpty(contractTel)?"":contractTel;
    }

    public void setContractTel(String contractTel) {
        this.contractTel = contractTel;
    }

    public String getZhiZhao() {
        return TextUtils.isEmpty(zhiZhao)?"":zhiZhao;
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
        return TextUtils.isEmpty(plantSystemName)?"":plantSystemName;
    }

    public void setPlantSystemName(String plantSystemName) {
        this.plantSystemName = plantSystemName;
    }

    public String getCheckDate() {
        return TextUtils.isEmpty(checkDate)?"":checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    @Override
    public String getPickerViewText() {
        return companyName;
    }
}
