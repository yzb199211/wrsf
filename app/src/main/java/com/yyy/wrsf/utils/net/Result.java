package com.yyy.wrsf.utils.net;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Result implements Serializable {

    /**
     * success : false
     * code : 400
     * msg : 验证码不正确
     * data
     * token
     */

    private boolean success;
    private Integer code;
    private String msg;
    private String rows;
    private String token;
    private long loginTime;

    public Result(String string) throws JSONException {
        JSONObject jsonObject = new JSONObject(string);
        setSuccess(jsonObject.optBoolean("success", false));
        setData(jsonObject.optString("rows", ""));
        setToken(jsonObject.optString("token", ""));
        setCode(jsonObject.optInt("code", -1));
        setMsg(jsonObject.optString("msg"));
    }

    public String getData() {
        return rows;
    }

    public void setData(String data) {
        this.rows = data;
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
