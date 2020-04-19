package com.yyy.wrsf.beans.ship;

public class ShipAddValueFeeB {
    /**
     * baoAskLimit : 0
     * baoPriceLimit : 0
     * baoRate : 0
     * companyId : 0
     * daiPriceLimit : 0
     * daiRate : 0
     * id : 0
     * qianEle : 0
     * qianPaper : 0
     */

    private Integer baoAskLimit;
    private Integer baoPriceLimit;
    private Double baoRate;
    private Integer companyId;
    private Integer daiPriceLimit;
    private Double daiRate;
    private Integer id;
    private Integer qianEle;
    private Integer qianPaper;

    public int getBaoAskLimit() {
        return baoAskLimit == null ? 0 : baoAskLimit;
    }

    public void setBaoAskLimit(int baoAskLimit) {
        this.baoAskLimit = baoAskLimit;
    }

    public int getBaoPriceLimit() {
        return baoPriceLimit == null ? 0 : baoPriceLimit;
    }

    public void setBaoPriceLimit(int baoPriceLimit) {
        this.baoPriceLimit = baoPriceLimit;
    }

    public double getBaoRate() {
        return baoRate == null ? 0 : baoRate;
    }

    public void setBaoRate(double baoRate) {
        this.baoRate = baoRate;
    }

    public int getCompanyId() {
        return companyId == null ? 0 : companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getDaiPriceLimit() {
        return daiPriceLimit == null ? 0 : daiPriceLimit;
    }

    public void setDaiPriceLimit(int daiPriceLimit) {
        this.daiPriceLimit = daiPriceLimit;
    }

    public double getDaiRate() {
        return daiRate == null ? 0 : daiRate;
    }

    public void setDaiRate(double daiRate) {
        this.daiRate = daiRate;
    }

    public int getId() {
        return id == null ? 0 : id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQianEle() {
        return qianEle == null ? 0 : qianEle;
    }

    public void setQianEle(int qianEle) {
        this.qianEle = qianEle;
    }

    public int getQianPaper() {
        return qianPaper == null ? 0 : qianPaper;
    }

    public void setQianPaper(int qianPaper) {
        this.qianPaper = qianPaper;
    }
}
