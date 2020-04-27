package com.yyy.wrsf.company.collection.persenter;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.beans.OrderBean;
import com.yyy.wrsf.beans.company.bill.CompanyBillCollectionTotalB;
import com.yyy.wrsf.company.collection.model.CollectionM;
import com.yyy.wrsf.company.collection.view.ICollectionV;
import com.yyy.wrsf.enums.ContractStatusEnum;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.net.bill.BillUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.order.OrderUrl;

import java.util.ArrayList;
import java.util.List;

public class CollectionP implements ICollectionP {
    private ICollectionV collectionV;
    private CollectionM collectionM;
    private boolean destroyFlag;
    private Handler handler = new Handler();
    private int pageIndex = 0;
    private int pageSize = 20;

    public CollectionP(ICollectionV collectionV) {
        this.collectionV = collectionV;
        collectionM = new CollectionM();
    }

    @Override
    public void getData() {
        collectionM.Requset(getParams(), NetConfig.address + BillUrl.getDaiContractReport, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("data", data);
                CompanyBillCollectionTotalB companyBillCollectionTotalB = new Gson().fromJson(data, CompanyBillCollectionTotalB.class);
                collectionV.setTotal(companyBillCollectionTotalB);
            }

            @Override
            public void onFail(String error) {
                Log.e("error", error);
            }
        });
    }

    @Override
    public void getOrder() {
        collectionV.startLoading();
        collectionM.Requset(getCollettionParams(), NetConfig.address + OrderUrl.getCompanyPageList, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        collectionV.finishLoading(null);
                        List<OrderBean> list = new Gson().fromJson(string, new TypeToken<List<OrderBean>>() {
                        }.getType());
                        if (list.size() > 0) {
                            collectionV.addList(list);
                            collectionV.refreshList();
                        }
                        if (list.size() < pageSize) {
                            collectionV.setLoad(false);
                        } else {
                            pageIndex += 1;
                        }
                    });
                }

            }

            @Override
            public void onFail(String string) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        collectionV.finishLoading(string);
                    });
                }
            }
        });
    }


    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(collectionM.getParams(collectionV.getMonth(), collectionV.getCompanyId()))));
        return params;
    }

    private List<NetParams> getCollettionParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(collectionM.getParams(pageIndex, pageSize, collectionV.getMonth(), collectionV.getPayType()))));
        return params;
    }

    @Override
    public void resetPager() {
        pageIndex = 0;
        collectionV.setLoad(true);
    }


}
