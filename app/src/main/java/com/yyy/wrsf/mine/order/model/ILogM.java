package com.yyy.wrsf.mine.order.model;

import com.yyy.wrsf.beans.TabB;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.List;

public interface ILogM {
    void getLog(List<NetParams> params, String url, RequstType requstType, OnResultListener onResultListener);
}
