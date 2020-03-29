package com.yyy.wrsf.mine.order.model;

import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;

import java.util.List;

public interface ILogM {
  public void getLog(List<NetParams> params, String url, RequstType requstType, OnResultListener onResultListener);
}
