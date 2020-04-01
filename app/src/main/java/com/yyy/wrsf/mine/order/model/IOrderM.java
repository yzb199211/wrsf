package com.yyy.wrsf.mine.order.model;

import com.yyy.wrsf.base.model.IBaseM;
import com.yyy.wrsf.model.filter.OrderFilterB;

public interface IOrderM extends IBaseM {
    OrderFilterB getFilter(String orderNo, Integer orderType);
}
