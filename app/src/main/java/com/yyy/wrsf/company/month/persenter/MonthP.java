package com.yyy.wrsf.company.month.persenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.beans.month.CustomerMonthB;
import com.yyy.wrsf.company.month.model.MonthM;
import com.yyy.wrsf.company.month.view.IMonthV;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.net.month.MonthUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class MonthP implements IMonthP {
    private IMonthV monthV;
    private MonthM monthM;
    private Handler handler = new Handler();
    private boolean destroyFlag;
    private PagerRequestBean pager;
    private int pageSize = 30;
    private int pageIndex = 0;

    public MonthP(IMonthV monthV) {
        this.monthV = monthV;
        monthM = new MonthM();
        initPager();
    }

    private void initPager() {
        pager = new PagerRequestBean();
        pager.setPageIndex(pageIndex);
        pager.setPageSize(pageSize);
    }

    @Override
    public void getData() {
        initParams();
        monthV.startLoading();
        monthM.Requset(getParams(), NetConfig.address + MonthUrl.pageList, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        monthV.finishLoading(null);
                        List<CustomerMonthB> list = new Gson().fromJson(data, new TypeToken<List<CustomerMonthB>>() {
                        }.getType());
                        if (list != null && list.size() > 0) {
                            monthV.addList(list);
                            monthV.refreshList();
                        }
                        if (list.size() < pageSize) {
                            monthV.stopLoadMore();
                        } else {
                            pageIndex += 1;
                        }
                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        monthV.finishLoading(error);
                    });
                }
            }
        });
    }

    private void initParams() {
        pager.setPageIndex(pageIndex);
        pager.setQueryParam(monthM.getMonthFilter(monthV.getFilter()));
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(pager)));
        return params;
    }
    public void detachView() {
        destroyFlag = true;
        this.monthV = null;
    }
}
