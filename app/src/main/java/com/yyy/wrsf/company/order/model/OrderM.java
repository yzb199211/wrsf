package com.yyy.wrsf.company.order.model;

import android.text.TextUtils;

import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.beans.TabB;
import com.yyy.wrsf.beans.filter.OrderFilterB;
import com.yyy.wrsf.enums.ContractStatusEnum;
import com.yyy.wrsf.enums.util.EnumEntity;
import com.yyy.wrsf.enums.util.EnumUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderM extends BaseM implements IOrderM {
    @Override
    public OrderFilterB getFilter(String orderNo, Integer orderType) {
        OrderFilterB orderFilterB = new OrderFilterB();
        if (TextUtils.isEmpty(orderNo) || orderType != null) {
            orderFilterB.setContractNo(TextUtils.isEmpty(orderNo) ? null : orderNo);
            orderFilterB.setContractStatus(orderType);
            return orderFilterB;
        }
        return null;
    }

    @Override
    public List<TabB> getTabs() {
        List<TabB> tabs = new ArrayList<>();
        tabs.add(new TabB(null, BaseApplication.getInstance().getString(R.string.common_all)));
        List<EnumEntity> list = EnumUtils.getEnumList(ContractStatusEnum.values(), ContractStatusEnum::getStatus, ContractStatusEnum::getDesc);
        for (EnumEntity enumEntity : list) {
            tabs.add(new TabB(enumEntity.getId(), enumEntity.getName()));
        }
        return tabs;
    }
}
