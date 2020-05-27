package com.yyy.wrsf.company.order.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.OrderBean;
import com.yyy.wrsf.beans.TabB;

import java.util.List;

public interface IOrderV extends ILoadingV {
    void refreshList();

    void addDatas(List<OrderBean> list);

    void cancelLoadMore();

    void setItemType(int pos, int type);

    Integer getType();

    String getOrderName();

    void setTabs(List<TabB> tabs);
    void stopLoad();
}
