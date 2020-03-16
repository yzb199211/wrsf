package com.yyy.wrsf.utils.net;


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

    private int success;
    private String msg;
    private String rows;

    public Result(String string) throws JSONException {
        JSONObject jsonObject = new JSONObject(string);
        setSuccess(jsonObject.optInt("success", -1));
        setData(jsonObject.optString("rows", ""));
        setMsg(jsonObject.optString("msg"));
    }

    public String getData() {
        return rows;
    }

    public void setData(String data) {
        this.rows = data;
    }

    public boolean isSuccess() {
        return success == 200 ? true : false;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMsg() {
        return success + ":" + msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
