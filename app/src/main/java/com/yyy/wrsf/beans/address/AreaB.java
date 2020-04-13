package com.yyy.wrsf.beans.address;

import android.text.TextUtils;

import java.io.Serializable;

public class AreaB implements Serializable {

    /**
     * id : 1
     * areaName : 北京市
     * displayName : 北京市
     * parentId : 0
     */

    private int id;
    private String areaName;
    private String displayName;
    private int parentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaName() {
        return TextUtils.isEmpty(areaName) ? "" : areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDisplayName() {
        return TextUtils.isEmpty(displayName) ? "" : displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
