package com.yyy.wrsf.mine.order.persenter;

public interface IOrderP {
    void getData(int type);

    void cancel(int pos, String id);


    void pay(String orderNo);

    void getTabs();

    void resetPage();
}
