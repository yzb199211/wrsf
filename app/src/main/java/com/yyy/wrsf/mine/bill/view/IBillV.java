package com.yyy.wrsf.mine.bill.view;

import android.widget.LinearLayout;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.model.BillBean;
import com.yyy.wrsf.utils.net.net.RequstType;

public interface IBillV extends ILoadingV {
    void setEditAble(boolean b);

    void setBill(BillBean bill);

    BillBean getBill();

    void setUrl(String string);

    String getUrl();

    void setRequstType(RequstType requstType);

    RequstType getRequstType();

    LinearLayout getContent();
}
