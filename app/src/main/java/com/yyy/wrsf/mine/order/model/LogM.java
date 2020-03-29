package com.yyy.wrsf.mine.order.model;

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

public class LogM implements ILogM {

    @Override
    public void getLog(List<NetParams> params, String url, RequstType requstType, OnResultListener onResultListener) {
        new NetUtil(params, url, requstType, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()){
                        onResultListener.onSuccess(result.getData());
                    }else{
                        onResultListener.onFail(result.getMsg());
                    }
                }catch (JSONException e){
                    onResultListener.onFail(BaseApplication.getInstance().getString(R.string.error_net));
                }catch (Exception e){
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
