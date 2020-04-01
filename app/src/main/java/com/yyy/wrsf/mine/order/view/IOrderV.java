package com.yyy.wrsf.mine.order.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.model.OrderBean;

import java.util.List;

public interface IOrderV extends ILoadingV {
    void refreshList();

    void addDatas(List<OrderBean> list);

    void cancelLoadMore();

    void setItemType(int type);

    Integer getType();

    String getOrderName();

}
