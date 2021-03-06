package com.yyy.wrsf.beans.publicm;

import androidx.annotation.Nullable;

import java.util.List;

public class PublicArray {
    private int recNo;
    private Integer publicCode;
    private String publicName;
    private List<PublicBean> plantPublicDetails;

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public int getPublicCode() {
        return publicCode;
    }

    public void setPublicCode(int publicCode) {
        this.publicCode = publicCode;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public List<PublicBean> getPlantPublicDetails() {
        return plantPublicDetails;
    }

    public void setPlantPublicDetails(List<PublicBean> plantPublicDetails) {
        this.plantPublicDetails = plantPublicDetails;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return publicCode == (Integer) obj;
    }
}
