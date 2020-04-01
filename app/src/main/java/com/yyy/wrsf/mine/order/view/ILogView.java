package com.yyy.wrsf.mine.order.view;

import java.util.List;

public interface ILogView<T> {
    void startLoading();
    void addLog(List<T> data);
    void showAll(List<T> data);
    void logFail(String string);
    void finishiLoading();
}
