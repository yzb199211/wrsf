package com.yyy.wrsf.model.filter;

import java.io.Serializable;

public class NoticeFilterB implements Serializable {

    /**
     * companyId : 0
     * id : string
     * memberId : 0
     * noticeId : string
     * readDate : 2020-03-18T04:49:24.015Z
     * readStatus : 0
     * reader : string
     * readerName : string
     * shopId : 0
     */

    private String id;
    private Integer memberId;
    private String noticeId;
    private String readDate;
    private Integer readStatus;
    private String reader;
    private String readerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }
}
