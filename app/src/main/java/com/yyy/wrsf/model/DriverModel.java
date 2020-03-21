package com.yyy.wrsf.model;

import android.text.TextUtils;

import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

import java.io.Serializable;

public class DriverModel implements Serializable, IPickerViewData {

    /**
     * driverName : string
     * driverStatus : 0
     * driverTel : string
     * driverType : 0
     * recNo : 0
     * sex : 0
     * transCompanyRecno : 0
     * transShopInfoRecno : 0
     */

    private String driverName;
    private Integer driverStatus;
    private String driverTel;
    private String driverTypeName;
    private Integer driverType;
    private Integer recNo;
    private Integer sex;
    private Integer transCompanyRecno;
    private Integer transShopInfoRecno;

    public String getDriverTypeName() {
        return TextUtils.isEmpty(driverTypeName) ? "" : driverTypeName;
    }

    public void setDriverTypeName(String driverTypeName) {
        this.driverTypeName = driverTypeName;
    }

    public String getDriverName() {
        return TextUtils.isEmpty(driverName) ? "" : driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Integer getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(int driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getDriverTel() {
        return TextUtils.isEmpty(driverTel) ? "" : driverTel;
    }

    public void setDriverTel(String driverTel) {
        this.driverTel = driverTel;
    }

    public int getDriverType() {
        return driverType;
    }

    public void setDriverType(int driverType) {
        this.driverType = driverType;
    }

    public int getRecNo() {
        return recNo == null ? 0 : recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public int getSex() {
        return sex == null ? 1 : sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getTransCompanyRecno() {
        return transCompanyRecno == null ? 0 : transCompanyRecno;
    }

    public void setTransCompanyRecno(int transCompanyRecno) {
        this.transCompanyRecno = transCompanyRecno;
    }

    public int getTransShopInfoRecno() {
        return transShopInfoRecno == null ? 0 : transShopInfoRecno;
    }

    public void setTransShopInfoRecno(int transShopInfoRecno) {
        this.transShopInfoRecno = transShopInfoRecno;
    }

    @Override
    public String getPickerViewText() {
        return driverName;
    }
}
