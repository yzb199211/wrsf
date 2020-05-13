package com.yyy.wrsf.company.bill.persenter;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.beans.company.bill.CompanyBillDetailB;
import com.yyy.wrsf.company.bill.model.CompamgBillDetailM;
import com.yyy.wrsf.company.bill.view.ICompanyBillDetailV;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.net.bill.BillUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class CompanyBillDetailP implements ICompanyBillDetailP {
    private ICompanyBillDetailV companyBillDetailV;
    private CompamgBillDetailM compamgBillDetailM;
    private boolean destroyFlag;
    private Handler handler = new Handler();
    private int pageIndex = 0;
    private int pageSize = 20;

    public CompanyBillDetailP(ICompanyBillDetailV companyBillDetailV) {
        this.companyBillDetailV = companyBillDetailV;
        compamgBillDetailM = new CompamgBillDetailM();
    }

    @Override
    public void getData() {
        companyBillDetailV.startLoading();
        compamgBillDetailM.Requset(getParams(), NetConfig.address + BillUrl.getCompanyFinanceReportDetail, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    companyBillDetailV.finishLoading(null);
                    try {
                        List<CompanyBillDetailB> list = new Gson().fromJson(data, new TypeToken<List<CompanyBillDetailB>>() {
                        }.getType());
                        handler.post(() -> {
                            if (list != null && list.size() > 0) {
                                companyBillDetailV.addList(list);
                                companyBillDetailV.refreshList();
                            }
                            if (list.size() < pageSize) {
                                companyBillDetailV.setLoad(false);
                            } else pageIndex += pageIndex;
                        });
                    }catch (JsonSyntaxException e){}

                }
            }

            @Override
            public void onFail(String error) {
                Log.e("error", error);
                companyBillDetailV.finishLoading(error);
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param",
                new Gson().toJson(
                        compamgBillDetailM.PageParams(
                                pageIndex,
                                pageSize,
                                companyBillDetailV.getMonth(),
                                companyBillDetailV.getCustomerId(),
                                companyBillDetailV.getCustomerTypeId()))));
        return params;
    }
    public void detachView() {
        destroyFlag = true;
        this.companyBillDetailV = null;
    }
}
