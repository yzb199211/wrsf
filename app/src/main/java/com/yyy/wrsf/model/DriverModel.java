package com.yyy.wrsf.model;

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
    private Integer driverType;
    private Integer recNo;
    private Integer sex;
    private Integer transCompanyRecno;
    private Integer transShopInfoRecno;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(int driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getDriverTel() {
        return driverTel;
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
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getTransCompanyRecno() {
        return transCompanyRecno;
    }

    public void setTransCompanyRecno(int transCompanyRecno) {
        this.transCompanyRecno = transCompanyRecno;
    }

    public int getTransShopInfoRecno() {
        return transShopInfoRecno;
    }

    public void setTransShopInfoRecno(int transShopInfoRecno) {
        this.transShopInfoRecno = transShopInfoRecno;
    }

    @Override
    public String getPickerViewText() {
        return driverName;
    }
}
