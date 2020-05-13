package com.yyy.wrsf.mine.bill.persenter;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.beans.company.bill.CompanyBillDetailB;
import com.yyy.wrsf.company.bill.model.CompamgBillDetailM;
import com.yyy.wrsf.company.bill.persenter.ICompanyBillDetailP;
import com.yyy.wrsf.company.bill.view.ICompanyBillDetailV;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.bill.model.BillMonthDetailM;
import com.yyy.wrsf.mine.bill.view.IBillMonthDetailV;
import com.yyy.wrsf.utils.net.bill.BillUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class BillMonthDetailP implements IBillMonthDetailP {
    private IBillMonthDetailV billMonthDetailV;
    private BillMonthDetailM billMonthDetailM;
    private boolean destroyFlag;
    private Handler handler = new Handler();
    private int pageIndex = 0;
    private int pageSize = 20;

    public BillMonthDetailP(IBillMonthDetailV billMonthDetailV) {
        this.billMonthDetailV = billMonthDetailV;
        billMonthDetailM = new BillMonthDetailM();
    }

    @Override
    public void getData() {
        billMonthDetailV.startLoading();
        billMonthDetailM.Requset(getParams(), NetConfig.address + BillUrl.getCustomerFinanceReportDetailPageList, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    billMonthDetailV.finishLoading(null);
                    try {
                        List<CompanyBillDetailB> list = new Gson().fromJson(data, new TypeToken<List<CompanyBillDetailB>>() {
                        }.getType());
                        handler.post(() -> {
                            if (list != null && list.size() > 0) {
                                billMonthDetailV.addList(list);
                                billMonthDetailV.refreshList();
                            }
                            if (list.size() < pageSize) {
                                billMonthDetailV.setLoad(false);
                            } else pageIndex += pageIndex;
                        });
                    }catch (NumberFormatException  e){}
                }
            }

            @Override
            public void onFail(String error) {
                Log.e("error", error);
                billMonthDetailV.finishLoading(error);
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param",
                new Gson().toJson(
                        billMonthDetailM.PageParams(
                                pageIndex,
                                pageSize,
                                billMonthDetailV.getMonth(),
                                billMonthDetailV.getCompanyId(),
                                billMonthDetailV.getCustomerTypeId()
                        ))));
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.billMonthDetailV = null;
    }
}
