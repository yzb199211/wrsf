package com.yyy.wrsf.base.model;

import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;

import org.json.JSONException;

import java.util.List;

public class BaseM implements IBaseM {
    @Override
    public void Requset(List<NetParams> params, String url, RequstType requstType, OnResultListener onResultListener) {
        new NetUtil(params, url, requstType, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        onResultListener.onSuccess(result.getData());
                    } else {
                        onResultListener.onFail(result.getMsg());
                    }
                } catch (JSONException e) {
                    onResultListener.onFail(BaseApplication.getInstance().getString(R.string.error_json));
                } catch (Exception e) {
                    onResultListener.onFail(e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Exception e) {
                onResultListener.onFail(e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
