package com.yyy.wrsf.company.bill.persenter;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
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
        compamgBillDetailM.Requset(getParams(), NetConfig.address + BillUrl.getCompanyFinanceReportDetail, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                Log.e("data", data);
            }

            @Override
            public void onFail(String error) {
                Log.e("error", error);
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
}
