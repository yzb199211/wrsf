package com.yyy.wrsf.mine.order.model;

import android.text.TextUtils;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.model.filter.OrderFilterB;

public class OrderM extends BaseM implements IOrderM {
    @Override
    public OrderFilterB getFilter(String orderNo, Integer orderType) {
        OrderFilterB orderFilterB = new OrderFilterB();
        if (TextUtils.isEmpty(orderNo) || orderType != null) {
            orderFilterB.setContractNo(TextUtils.isEmpty(orderNo) ? null : orderNo);
            orderFilterB.setContractStatus(orderType);
        }
        return null;
    }
}
