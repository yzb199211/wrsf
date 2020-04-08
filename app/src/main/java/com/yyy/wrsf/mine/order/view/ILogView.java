package com.yyy.wrsf.mine.order.view;

import com.yyy.wrsf.base.view.ILoadingV;

import java.util.List;

public interface ILogView<T> extends ILoadingV {
    void addLog(List<T> data);
    void showAll(List<T> data);
    String getContractNo();
    void refreshList();
}
