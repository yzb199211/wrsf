package com.yyy.wrsf.mine.order.persenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.order.View.ILogView;
import com.yyy.wrsf.mine.order.bean.LogBean;
import com.yyy.wrsf.mine.order.model.ILogM;
import com.yyy.wrsf.mine.order.model.LogM;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;


public class LogPersenter<T> implements ILogPersenter {
    private ILogM iLogM;
    private ILogView iLogView;
    private Handler handler = new Handler();
    private boolean destroyFlag;
    private List<T> list;

    public LogPersenter(ILogView iLogView) {
        this.iLogM = new LogM();
        this.iLogView = iLogView;
    }

    @Override
    public void loadingLog(PagerRequestBean pager) {
        iLogView.startLoading();
        iLogM.getLog(getParams(pager), NetConfig.address, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
                if (!destroyFlag)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iLogView.finishiLoading();
                            list = new Gson().fromJson(string, new TypeToken<List<T>>() {
                            }.getType());
                            if (list != null)
                                iLogView.addLog(list.size() > 3 ? list.subList(0, 3) : list);
                        }
                    });

            }

            @Override
            public void onFail(String string) {
                if (destroyFlag)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iLogView.logFail(string);
                            iLogView.finishiLoading();
                        }
                    });
            }
        });
    }

    @Override
    public void showAll() {
        if (list != null && list.size() > 3) {
            iLogView.addLog(list.subList(3, list.size()));
        }
    }

    private List<NetParams> getParams(PagerRequestBean pager) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(pager)));
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.iLogView = null;
    }
}
