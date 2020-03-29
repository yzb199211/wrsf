package com.yyy.wrsf.mine.order.persenter;

import com.yyy.wrsf.utils.net.net.PagerRequestBean;

import java.util.List;

public interface ILogPersenter{
    void loadingLog(PagerRequestBean pager);
    void showAll();
}
