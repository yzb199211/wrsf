package com.yyy.wrsf.mine.order.persenter;

public interface IOrderP {
    void getData();

    void cancel(int pos, String id);

    void pay(int pos);

    void getTabs();
}
