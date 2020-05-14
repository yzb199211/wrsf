package com.yyy.wrsf.mine.order.persenter;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.enums.ContractStatusEnum;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.order.bean.LogBean;
import com.yyy.wrsf.mine.order.model.ILogM;
import com.yyy.wrsf.mine.order.model.LogM;
import com.yyy.wrsf.mine.order.view.ILogView;
import com.yyy.wrsf.utils.net.log.LogUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.order.OrderUrl;

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
                            }catch (JsonSyntaxException e){}
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

    public void confirm() {
        iLogView.startLoading();
        iLogM.getLog(comfirmParams(), NetConfig.address + OrderUrl.shopConfirm, RequstType.GET, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iLogView.finishLoading(BaseApplication.getInstance().getString(R.string.common_cancel_success));

                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    handler.post(() -> {
                       iLogView.finishLoading(error);
                    });
                }
            }
        });
    }
    public void cancel() {
        iLogView.startLoading();
        iLogM.getLog(cancelParams(), NetConfig.address + OrderUrl.cancelOrder, RequstType.DELETE, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iLogView.finishLoading(BaseApplication.getInstance().getString(R.string.common_cancel_success));

                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iLogView.finishLoading(error);
                    });
                }
            }
        });
    }
    private List<NetParams> comfirmParams() {
        List<NetParams> list = new ArrayList<>();
        list.add(new NetParams("contractNo", iLogView.getContractNo()));
        list.add(new NetParams("confirmStatus", "1"));
        return list;
    }
    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("contractNo", iLogView.getContractNo()));
        return params;
    }
    private List<NetParams> cancelParams() {
        List<NetParams> list = new ArrayList<>();
        list.add(new NetParams("contractNo", iLogView.getContractNo()));
        return list;
    }
    public void detachView() {
        destroyFlag = true;
        this.iLogView = null;
    }
}
