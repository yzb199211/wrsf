package com.yyy.wrsf.utils.net.member;

import com.yyy.wrsf.utils.net.Result;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultUtil {
    public static Result getResult(String string) throws JSONException {
        Result result = new Result(string);
        JSONObject jsonObject = new JSONObject(string);
        result.setSuccess(jsonObject.optBoolean("success", false));
        result.setData(jsonObject.optString("rows", ""));
        result.setToken(jsonObject.optString("token", ""));
        result.setCode(jsonObject.optInt("code", -1));
        result.setMsg(jsonObject.optString("msg"));
        return result;
    }
}
