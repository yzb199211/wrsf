package com.yyy.wrsf.main.persenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.main.model.CycleM;
import com.yyy.wrsf.main.view.ICycleV;
import com.yyy.wrsf.beans.ImageBean;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.publics.PublicUrl;
import com.yyy.wrsf.view.cycle.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

public class CycleP implements ICycleP {
    private ICycleV iCycleV;
    private CycleM cycleM;
    private Handler handler = new Handler();
    private boolean destroyFlag;


    public CycleP(ICycleV iCycleV) {
        this.iCycleV = iCycleV;
        cycleM = new CycleM();
    }

    @Override
    public void getImgs() {
        cycleM.Requset(getParams(), NetConfig.address + PublicUrl.getFiles, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
                if (!destroyFlag) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            List<DataBean> imgs = getImgs(string);
                            if (imgs.size() > 0 && imgs.size() == 1) {
                                iCycleV.setImage(imgs.get(0).getUrl());
                            } else if (imgs.size() > 0) {
                                iCycleV.setViewPager(imgs);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFail(String string) {
                if (!destroyFlag) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iCycleV.finishLoading(string);
                        }
                    });
                }
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(getPager())));
        return params;
    }

    private PagerRequestBean getPager() {
        PagerRequestBean pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(6);
        return pager;
    }

    private List<DataBean> getImgs(String string) {
        List<DataBean> imgs = cycleM.initCycleImgs(
                new Gson().fromJson(string, new TypeToken<List<ImageBean>>() {
                }.getType()));
        return imgs;
    }

    public void detachView() {
        destroyFlag = true;
        this.iCycleV = null;
    }
}
