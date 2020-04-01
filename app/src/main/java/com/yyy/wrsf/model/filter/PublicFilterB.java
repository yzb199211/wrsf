package com.yyy.wrsf.model.filter;

import java.io.Serializable;
import java.util.List;

public class PublicFilterB implements Serializable {

    /**
     * detailCode : 0
     * detailName : string
     * plantPublicRecno : 0
     * publicCode : 0
     * publicCodes : [0]
     * recNo : 0
     * remark : string
     * yesno : 0
     */

    private Integer detailCode;
    private String detailName;
    private Integer plantPublicRecno;
    private Integer publicCode;
    private Integer recNo;
    private String remark;
    private Integer yesno;
    private List<Integer> publicCodes;

    public int getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(int detailCode) {
        this.detailCode = detailCode;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public int getPlantPublicRecno() {
        return plantPublicRecno;
    }

    public void setPlantPublicRecno(int plantPublicRecno) {
        this.plantPublicRecno = plantPublicRecno;
    }

    public int getPublicCode() {
        return publicCode;
    }

    public void setPublicCode(int publicCode) {
        this.publicCode = publicCode;
    }

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getYesno() {
        return yesno;
    }

    public void setYesno(int yesno) {
        this.yesno = yesno;
    }

    public List<Integer> getPublicCodes() {
        return publicCodes;
    }

    public void setPublicCodes(List<Integer> publicCodes) {
        this.publicCodes = publicCodes;
    }
}
