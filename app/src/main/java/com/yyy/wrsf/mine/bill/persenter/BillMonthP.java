package com.yyy.wrsf.mine.bill.persenter;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.beans.company.bill.CompanyBillTotalB;
import com.yyy.wrsf.beans.filter.CompanyBillTotalF;
import com.yyy.wrsf.beans.person.PersonBillB;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.bill.model.BillMonthM;
import com.yyy.wrsf.mine.bill.view.IBillMonthV;
import com.yyy.wrsf.utils.net.bill.BillUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class BillMonthP implements IBillMonthP
{
    private IBillMonthV billMonthV;
    private BillMonthM billMonthM;
    private Handler handler = new Handler();
    private boolean destroyFlag;
    private PagerRequestBean pager;
    private int pageSize = 30;
    private int pageIndex = 0;

    public BillMonthP(IBillMonthV billMonthV) {
        this.billMonthV = billMonthV;
        billMonthM = new BillMonthM();
    }

    @Override
    public void getData() {
        billMonthM.Requset(getParams(), NetConfig.address + BillUrl.getCustomerFinanceReportTotal, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    try {
                        Log.e("data", data);
                        CompanyBillTotalB totalB = new Gson().fromJson(data, CompanyBillTotalB.class);
                        handler.post(() -> {
                            billMonthV.setTotal(totalB);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    billMonthV.finishLoading(error);
                }
            }
        });
    }
    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(new CompanyBillTotalF().setMonth(billMonthV.getMonth()))));
        return params;
    }
    @Override
    public void getBill() {
        billMonthV.startLoading();
        billMonthM.Requset(getBillParams(), NetConfig.address + BillUrl.getCustomerFinanceReportPageList, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    billMonthV.finishLoading(null);
                    try {
                        List<PersonBillB> list = new Gson().fromJson(data, new TypeToken<List<PersonBillB>>() {
                        }.getType());
                        refreshList(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                        billMonthV.finishLoading(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    billMonthV.finishLoading(error);
                }
            }
        });
    }
    private List<NetParams> getBillParams() {
        PagerRequestBean pagerRequestBean = billMonthM.PageParams(pageIndex, pageSize, billMonthV.getField(), billMonthV.getOrder(), billMonthV.getMonth());
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(pagerRequestBean)));
        return params;
    }
    private void refreshList(List<PersonBillB> list) {
        if (list != null && list.size() > 0) {
            billMonthV.addList(list);
            handler.post(() -> {
                billMonthV.refreshList();
                if (list.size() < pageIndex) billMonthV.loadMore(false);
                else pageIndex += pageIndex;
            });
        }
    }
    @Override
    public void resetPage(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    public void detachView() {
        destroyFlag = true;
        this.billMonthV = null;
    }
}
