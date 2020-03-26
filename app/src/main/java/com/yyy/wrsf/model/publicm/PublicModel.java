package com.yyy.wrsf.model.publicm;

import com.yyy.yyylibrary.wheel.interfaces.IPickerViewData;

public class PublicModel implements IPickerViewData {

    /**
     * recNo : 1
     * plantPublicRecno : 2
     * detailCode : 81
     * detailName : 香锅
     * yesno : 1
     * remark : 123
     */

    private int recNo;
    private int plantPublicRecno;
    private int detailCode;
    private String detailName;


    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public int getPlantPublicRecno() {
        return plantPublicRecno;
    }

    public void setPlantPublicRecno(int plantPublicRecno) {
        this.plantPublicRecno = plantPublicRecno;
    }

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


    @Override
    public String getPickerViewText() {
        return detailName;
    }
}
