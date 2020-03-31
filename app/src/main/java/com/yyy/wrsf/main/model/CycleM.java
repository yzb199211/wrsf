package com.yyy.wrsf.main.model;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.model.ImageBean;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.view.cycle.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

public class CycleM extends BaseM {
    public List<DataBean> initCycleImgs(List<ImageBean> imgs) {
        List<DataBean> list = new ArrayList<>();
        for (ImageBean img : imgs) {
            list.add(new DataBean(NetConfig.address_img + img.getUrl(), ""));
        }
        return list;
    }
}
