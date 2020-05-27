package com.yyy.wrsf.company.order.persenter;

public interface IOrderP {
    void getData(int type);

    void cancel(int pos, String id);

    void confirmGet(int pos, String id);

    void confirm(int pos, String id);

    void pay(int pos);

    void getTabs();

    void resetPage();
}
