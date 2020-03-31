package com.yyy.wrsf.main.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.view.cycle.bean.DataBean;

import java.util.List;

public interface ICycleV extends ILoadingV {
    void setViewPager(List<DataBean> imgs);

    void setImage(String string);
}
