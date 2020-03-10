package com.yyy.wrsf.utils.net;

public class Result {

    /**
     * success : false
     * code : 400
     * msg : 验证码不正确
     * data
     * token
     */

    private boolean success;
    private int code;
    private String msg;
    private String data;
    private String token;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
