package com.yyy.wrsf.view.cycle.bean;

public class DataBean {
    private String url;
    private String describe;

    public DataBean(String url, String describe) {
        this.url = url;
        this.describe = describe;
    }

    public String getUrl() {
        return url;
    }

    public DataBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDescribe() {
        return describe;
    }

    public DataBean setDescribe(String describe) {
        this.describe = describe;
        return this;
    }
}