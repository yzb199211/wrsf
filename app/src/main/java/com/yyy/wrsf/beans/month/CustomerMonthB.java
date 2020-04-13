package com.yyy.wrsf.beans.month;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * [description]
 *
 * @version v1.0
 * @Author: cyq
 * @Date: 2020/3/12
 */
public class CustomerMonthB {

    private Integer recNo;

    private Integer transCompanyRecNo;

    private String companyName;

    private String zhiZhao;

    private String firstAdd;

    private String secondAdd;

    private String thirdAdd;

    private String detailAdd;

    private String person;

    private String contractPerson;

    private String contractTel;

    private Integer customerStatus;

    private BigDecimal yskTotal;

    private Integer firstId;

    private Integer secondId;

    private Integer thirdId;

    public Integer getRecNo() {
        return recNo;
    }

    public void setRecNo(Integer recNo) {
        this.recNo = recNo;
    }

    public Integer getTransCompanyRecNo() {
        return transCompanyRecNo == null ? -1 : transCompanyRecNo;
    }

    public void setTransCompanyRecNo(Integer transCompanyRecNo) {
        this.transCompanyRecNo = transCompanyRecNo;
    }

    public String getCompanyName() {
        return TextUtils.isEmpty(companyName) ? "" : companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getZhiZhao() {
        return TextUtils.isEmpty(zhiZhao) ? "" : zhiZhao;
    }

    public void setZhiZhao(String zhiZhao) {
        this.zhiZhao = zhiZhao;
    }

    public String getFirstAdd() {
        return TextUtils.isEmpty(firstAdd) ? "" : firstAdd;
    }

    public void setFirstAdd(String firstAdd) {
        this.firstAdd = firstAdd;
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

    public String getDetailAdd() {
        return TextUtils.isEmpty(detailAdd) ? "" : detailAdd;
    }

    public void setDetailAdd(String detailAdd) {
        this.detailAdd = detailAdd;
    }

    public String getPerson() {
        return TextUtils.isEmpty(person) ? "" : person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getContractPerson() {
        return TextUtils.isEmpty(contractPerson) ? "" : contractPerson;
    }

    public void setContractPerson(String contractPerson) {
        this.contractPerson = contractPerson;
    }

    public String getContractTel() {
        return TextUtils.isEmpty(contractTel) ? "" : contractTel;
    }

    public void setContractTel(String contractTel) {
        this.contractTel = contractTel;
    }

    public Integer getCustomerStatus() {
        return customerStatus==null?0:customerStatus;
    }

    public void setCustomerStatus(Integer customerStatus) {
        this.customerStatus = customerStatus;
    }

    public BigDecimal getYskTotal() {
        return yskTotal;
    }

    public void setYskTotal(BigDecimal yskTotal) {
        this.yskTotal = yskTotal;
    }

    public Integer getFirstId() {
        return firstId;
    }

    public void setFirstId(Integer firstId) {
        this.firstId = firstId;
    }

    public Integer getSecondId() {
        return secondId;
    }

    public void setSecondId(Integer secondId) {
        this.secondId = secondId;
    }

    public Integer getThirdId() {
        return thirdId;
    }

    public void setThirdId(Integer thirdId) {
        this.thirdId = thirdId;
    }
}
