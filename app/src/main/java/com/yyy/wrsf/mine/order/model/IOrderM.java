package com.yyy.wrsf.mine.order.model;

import com.yyy.wrsf.base.model.IBaseM;
import com.yyy.wrsf.beans.TabB;
import com.yyy.wrsf.beans.filter.OrderFilterB;

import java.util.List;

public interface IOrderM extends IBaseM {
    OrderFilterB getFilter(String orderNo, Integer orderType);
    OrderFilterB getFilterNotice(String orderNo, Integer orderType);
    List<TabB> getTabs();

}
