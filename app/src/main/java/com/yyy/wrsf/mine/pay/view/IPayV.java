package com.yyy.wrsf.mine.pay.view;

import com.yyy.wrsf.base.view.ILoadingV;

public interface IPayV extends ILoadingV {
    void getOrder();

    String getOrderNo();

    void setOrder();


}
