package com.yyy.wrsf.model;

import android.text.TextUtils;

public class BillBean {

    /**
     * invoiceAdd : string
     * invoiceBank : string
     * invoiceBankNumber : string
     * invoiceCompany : string
     * invoiceTax : string
     * invoiceTel : string
     * invoiceType : 0
     * platMemberRecNo : 0
     * recAdd : string
     * recNo : 0
     * recPerson : string
     * recTel : string
     */

    private String invoiceAdd;
    private String invoiceBank;
    private String invoiceBankNumber;
    private String invoiceCompany;
    private String invoiceTax;
    private String invoiceTel;
    private int invoiceType;
    private String invoiceTypeName;
    private String recAdd;
    private Integer recNo;
    private String recPerson;
    private String recTel;

    public String getInvoiceAdd() {
        return invoiceAdd;
    }

    public void setInvoiceAdd(String invoiceAdd) {
        this.invoiceAdd = invoiceAdd;
    }

    public String getInvoiceBank() {
        return TextUtils.isEmpty(invoiceBank) ? "" : invoiceBank;
    }

    public void setInvoiceBank(String invoiceBank) {
        this.invoiceBank = invoiceBank;
    }

    public String getInvoiceBankNumber() {
        return TextUtils.isEmpty(invoiceBankNumber) ? "" : invoiceBankNumber;
    }

    public void setInvoiceBankNumber(String invoiceBankNumber) {
        this.invoiceBankNumber = invoiceBankNumber;
    }

    public String getInvoiceCompany() {
        return TextUtils.isEmpty(invoiceCompany) ? "" : invoiceCompany;
    }

    public void setInvoiceCompany(String invoiceCompany) {
        this.invoiceCompany = invoiceCompany;
    }

    public String getInvoiceTax() {
        return TextUtils.isEmpty(invoiceTax) ? "" : invoiceTax;
    }

    public void setInvoiceTax(String invoiceTax) {
        this.invoiceTax = invoiceTax;
    }

    public String getInvoiceTel() {
        return TextUtils.isEmpty(invoiceTel) ? "" : invoiceTel;
    }

    public void setInvoiceTel(String invoiceTel) {
        this.invoiceTel = invoiceTel;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTypeName() {
        return TextUtils.isEmpty(invoiceTypeName) ? "" : invoiceTypeName;
    }

    public void setInvoiceTypeName(String invoiceTypeName) {
        this.invoiceTypeName = invoiceTypeName;
    }

    public String getRecAdd() {
        return TextUtils.isEmpty(recAdd) ? "" : recAdd;
    }

    public void setRecAdd(String recAdd) {
        this.recAdd = recAdd;
    }

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public String getRecPerson() {
        return TextUtils.isEmpty(recPerson) ? "" : recPerson;
    }

    public void setRecPerson(String recPerson) {
        this.recPerson = recPerson;
    }

    public String getRecTel() {
        return TextUtils.isEmpty(recTel) ? "" : recTel;
    }

    public void setRecTel(String recTel) {
        this.recTel = recTel;
    }
}
