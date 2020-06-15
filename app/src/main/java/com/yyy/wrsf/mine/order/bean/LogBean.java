package com.yyy.wrsf.mine.order.bean;

import android.text.TextUtils;

public class LogBean {

    /**
     * id : 6
     * orderNum : 20200327145742000001
     * operationId : 29
     * operationName : 测试-停用2
     * logTitle : 创建订单
     * createDate : 2020-03-27 14:57:42
     */

    private int id;
    private String orderNum;
    private int operationId;
    private String operationName;
    private String logTitle;
    private String createDate;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getLogTitle() {
        return logTitle;
    }

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return TextUtils.isEmpty(content) ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
