package com.yyy.wrsf.beans;

import android.text.TextUtils;

import java.io.Serializable;

public class CarB implements Serializable {

    /**
     * recNo : 1
     * driverRecNo : 1
     * carCode : WL01
     * carStatus : 1
     * carType : 28
     * carName : 物流车辆1
     * carTypeName : 挂车
     * carStatusName : 香锅
     */

    private Integer recNo;
    private int driverRecNo;
    private String carCode;
    private int carStatus;
    private int carType;
    private String carName;
    private String carTypeName;
    private String carStatusName;
    private String driverName;
    private String driverCer;

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public int getDriverRecNo() {
        return driverRecNo;
    }

    public void setDriverRecNo(int driverRecNo) {
        this.driverRecNo = driverRecNo;
    }

    public String getCarCode() {
        return TextUtils.isEmpty(carCode) ? "" : carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public int getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(int carStatus) {
        this.carStatus = carStatus;
    }

    public int getCarType() {
        return carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }

    public String getCarName() {
        return TextUtils.isEmpty(carName) ? "" : carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarTypeName() {
        return TextUtils.isEmpty(carTypeName) ? "" : carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getCarStatusName() {
        return TextUtils.isEmpty("carStatusName") ? "" : carStatusName;
    }

    public void setCarStatusName(String carStatusName) {
        this.carStatusName = carStatusName;
    }

    public String getDriverName() {
        return TextUtils.isEmpty(driverName) ? "" : driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverCer() {
        return driverCer;
    }

    public void setDriverCer(String driverCer) {
        this.driverCer = driverCer;
    }
}
