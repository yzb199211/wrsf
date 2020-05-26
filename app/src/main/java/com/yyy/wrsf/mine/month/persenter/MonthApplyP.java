package com.yyy.wrsf.mine.month.persenter;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.beans.month.MonthB;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.month.model.MonthApplyM;
import com.yyy.wrsf.mine.month.view.IMonthApplyV;
import com.yyy.wrsf.utils.net.month.MonthUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class MonthApplyP implements IMonthApplyP {
    private MonthApplyM monthApplyM;
    private IMonthApplyV iMonthApplyV;
    private boolean destroyFlag;
    private Handler handler = new Handler();
    private int pageSize = 20;
    private int pageIndex = 0;

    public MonthApplyP(IMonthApplyV iMonthApplyV) {
        this.iMonthApplyV = iMonthApplyV;
        monthApplyM = new MonthApplyM();
    }

    @Override
    public void getData() {
        iMonthApplyV.startLoading();
        monthApplyM.Requset(getParams(), NetConfig.address + MonthUrl.selectMemberPageList, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag)
                handler.post(()->{
                    try {
                        List<MonthB> list  = new Gson().fromJson(data,new TypeToken<List<MonthB>>(){}.getType());
                        if (list!=null&&list.size()>0){
                            if(list.size()<pageIndex) iMonthApplyV.forbidLoad(false);
                            else pageIndex +=1;
                            iMonthApplyV.setList(list);
                        }else {
                           iMonthApplyV.finishLoading(BaseApplication.getInstance().getString(R.string.error_empty));
                        }
                    }catch (JsonSyntaxException e){
                        iMonthApplyV.finishLoading(BaseApplication.getInstance().getString(R.string.error_json));
                    }
                });
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag)
                handler.post(()->{
                   iMonthApplyV.finishLoading(error);
                });
            }
        });
    }

    @Override
    public void resetPageIndex() {
        pageIndex = 0;
    }

    public List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", getPagerFilter()));
        return params;
    }

    public String getPagerFilter() {
        PagerRequestBean pagerRequestBean = new PagerRequestBean();
        pagerRequestBean.setPageSize(pageSize);
        pagerRequestBean.setPageIndex(pageIndex);
        pagerRequestBean.setQueryParam(monthApplyM.getMonthFilter(iMonthApplyV.getStatus()));
        return new Gson().toJson(pagerRequestBean);
    }

    public void detachView() {
        destroyFlag = true;
        this.iMonthApplyV = null;
    }
}
