package com.yyy.wrsf.model;

public class CarModel {

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

    private int recNo;
    private int driverRecNo;
    private String carCode;
    private int carStatus;
    private int carType;
    private String carName;
    private String carTypeName;
    private String carStatusName;

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
        return carCode;
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
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getCarStatusName() {
        return carStatusName;
    }

    public void setCarStatusName(String carStatusName) {
        this.carStatusName = carStatusName;
    }
}
