package com.yyy.wrsf.bean;

public class MemberAuthorityBean {

    /**
     * id : 2
     * name : COMPANY
     * description : 物流公司负责人
     * createTime : 1584171511000
     * updateTime : 1584171511000
     */

    private int id;
    private String name;
    private String description;
    private long createTime;
    private long updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
