package com.yyy.wrsf.login.Model;

import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.List;

public interface ILoginM {
    void login(List<NetParams> params, String url, RequstType requstType, OnResultListener onResultListener);
}
