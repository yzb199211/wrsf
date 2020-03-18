package com.yyy.wrsf.model;

public class NoticeModel {

    /**
     * createDate : 2020-03-18T00:53:10.406Z
     * createName : string
     * creator : 0
     * id : string
     * modifier : 0
     * modifyDate : 2020-03-18T00:53:10.406Z
     * noticeContent : string
     * noticeRole : 0
     * sendStatus : 0
     * title : string
     */

    private String createDate;
    private String createName;
    private int creator;
    private String id;
    private int modifier;
    private String modifyDate;
    private String noticeContent;
    private int noticeRole;
    private int sendStatus;
    private String title;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public int getNoticeRole() {
        return noticeRole;
    }

    public void setNoticeRole(int noticeRole) {
        this.noticeRole = noticeRole;
    }

    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
