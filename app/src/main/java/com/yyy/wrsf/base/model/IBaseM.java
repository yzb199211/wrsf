package com.yyy.wrsf.base.model;

import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.List;

public interface IBaseM {
    void Requset(List<NetParams> params, String url, RequstType requstType, OnResultListener onResultListener);
}
