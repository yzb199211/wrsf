package com.yyy.wrsf.mine.order.persenter;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.order.bean.LogBean;
import com.yyy.wrsf.mine.order.model.ILogM;
import com.yyy.wrsf.mine.order.model.LogM;
import com.yyy.wrsf.mine.order.view.ILogView;
import com.yyy.wrsf.utils.net.log.LogUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;


public class LogPersenter implements ILogPersenter {
    private ILogM iLogM;
    private ILogView iLogView;
    private Handler handler = new Handler();
    private boolean destroyFlag;
    private List<LogBean> list;

    public LogPersenter(ILogView iLogView) {
        this.iLogM = new LogM();
        this.iLogView = iLogView;
    }


    @Override
    public void getLog() {
        iLogView.startLoading();
        iLogM.getLog(getParams(), NetConfig.address + LogUrl.orderLog, RequstType.GET, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
//                Log.e("log", string);
                if (!destroyFlag)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iLogView.finishLoading(null);
                            try {
                                list = new Gson().fromJson(string, new TypeToken<List<LogBean>>() {
                                }.getType());
                                if (list != null) {
                                    iLogView.addLog(list.size() > 3 ? list.subList(0, 3) : list);
                                    iLogView.refreshList();
                                }
                                if (list != null && list.size() < 4) {
                                    iLogView.hideLoad();
                                }
                            }catch (Exception e){}
                        }
                    });

            }

            @Override
            public void onFail(String string) {
                if (!destroyFlag)
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iLogView.finishLoading(string);
                        }
                    });
            }
        });
    }

    @Override
    public void showAll() {
        if (list != null && list.size() > 3) {
            iLogView.addLog(list.subList(3, list.size()));
            iLogView.refreshList();
        }
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("contractNo", iLogView.getContractNo()));
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.iLogView = null;
    }
}
