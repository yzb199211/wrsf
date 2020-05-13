package com.yyy.wrsf.mine.order.persenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.enums.ContractStatusEnum;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.order.model.IOrderM;
import com.yyy.wrsf.mine.order.model.OrderM;
import com.yyy.wrsf.mine.order.view.IOrderV;
import com.yyy.wrsf.beans.OrderBean;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.order.OrderUrl;

import java.util.ArrayList;
import java.util.List;

public class OrderP implements IOrderP {
    private IOrderV iOrderV;
    private IOrderM iOrderM;
    private Handler handler = new Handler();
    private boolean destroyFlag;
    private PagerRequestBean pager;
    private int pageSize = 30;
    private int pageIndex = 0;

    public OrderP(IOrderV iOrderV) {
        this.iOrderV = iOrderV;
        iOrderM = new OrderM();
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
        iOrderV.startLoading();
        iOrderM.Requset(getParams(), NetConfig.address + OrderUrl.getPageList, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iOrderV.finishLoading(null);
                        try {
                            List<OrderBean> list = new Gson().fromJson(string, new TypeToken<List<OrderBean>>() {
                            }.getType());
                            if (list.size() > 0) {
                                iOrderV.addDatas(list);
                                iOrderV.refreshList();
                            }
                            if (list.size() < pageSize) {
                                iOrderV.cancelLoadMore();
                            } else {
                                pageIndex += 1;
                            }
                        }catch (JsonSyntaxException e){

                        }
                    });
                }

            }

            @Override
            public void onFail(String string) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iOrderV.finishLoading(string);
                    });
                }
            }
        });
    }

    private void initParams() {
        pager.setPageIndex(pageIndex);
        pager.setQueryParam(iOrderM.getFilter(iOrderV.getOrderName(), iOrderV.getType()));
    }

    @Override
    public void cancel(int pos, String id) {
        iOrderV.startLoading();
        iOrderM.Requset(cancelParams(id), NetConfig.address + OrderUrl.cancelOrder, RequstType.DELETE, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iOrderV.finishLoading(BaseApplication.getInstance().getString(R.string.common_cancel_success));
                        iOrderV.setItemType(pos, ContractStatusEnum.CANCEL.getStatus());
                        iOrderV.refreshList();
                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iOrderV.finishLoading(error);
                    });
                }
            }
        });
    }

    @Override
    public void pay(int pos) {
    }

    @Override
    public void getTabs() {
        iOrderV.setTabs(iOrderM.getTabs());
    }

    @Override
    public void resetPage() {
        pageIndex = 0;
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(pager)));
        return params;
    }

    private List<NetParams> cancelParams(String id) {
        List<NetParams> list = new ArrayList<>();
        list.add(new NetParams("contractNo", id));
        return list;
    }

    public void detachView() {
        destroyFlag = true;
        this.iOrderV = null;
    }
}
