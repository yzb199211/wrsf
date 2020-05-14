package com.yyy.wrsf.mine.order.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.mine.order.bean.LogBean;

import java.util.List;

public interface ILogView extends ILoadingV {
    void addLog(List<LogBean> data);

    String getContractNo();

    void refreshList();

    void hideLoad();
    void finishs();
}
