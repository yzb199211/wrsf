package com.yyy.wrsf.company.bill.persenter;


import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.company.bill.model.CompamgBillM;
import com.yyy.wrsf.company.bill.view.ICompanyBillV;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.net.bill.BillUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class CompanyBillP implements ICompanyBillP {
    private CompamgBillM compamgBillM;
    private ICompanyBillV companyBillV;
    private Handler handler = new Handler();
    private boolean destroyFlag;
    private int pageIndex = 0;
    private int pageSize = 20;

    public CompanyBillP(ICompanyBillV companyBillV) {
        this.companyBillV = companyBillV;
        compamgBillM = new CompamgBillM();
    }

    @Override
    public void getBill() {
        companyBillV.startLoading();
        compamgBillM.Requset(getParams(), NetConfig.address + BillUrl.getCompanyFinanceReport, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    finish(null);
                    try {
                        List<CompanyBillB> list = new Gson().fromJson(data, new TypeToken<List<CompanyBillB>>() {
                        }.getType());
                        refreshList(list);
                    } catch (Exception e) {
                        finish(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    finish(error);
                }
            }
        });
    }

    private void finish(String error) {
        handler.post(() -> {
            companyBillV.finishLoading(error);
            System.out.println(error);
        });
    }

    private void refreshList(List<CompanyBillB> list) {
        if (list != null && list.size() > 0) {
            companyBillV.addList(list);
            handler.post(() -> {
                companyBillV.refreshList();
            });
        }
    }

    @Override
    public void resetPage(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    private List<NetParams> getParams() {
        PagerRequestBean pagerRequestBean = compamgBillM.PageParams(pageIndex, pageSize, companyBillV.getField(), companyBillV.getOrder(), companyBillV.getMonth());
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(pagerRequestBean)));
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.companyBillV = null;
    }
}
