package com.yyy.wrsf.mine.pay.persenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.base.model.IBaseM;
import com.yyy.wrsf.beans.PaymentB;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.pay.view.IPayV;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.pay.PayUrl;

import java.util.ArrayList;
import java.util.List;

public class PayP implements IPayP {
    private IPayV payV;
    private IBaseM baseM;
    private boolean destroyFlag;
    private Handler handler = new Handler();

    public PayP(IPayV payV) {
        this.payV = payV;
        baseM = new BaseM();
    }

    @Override
    public void getOrder() {

    }

    @Override
    public void pay() {
        payV.startLoading();
        baseM.Requset(payParams(), NetConfig.address + PayUrl.pay, RequstType.GET, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                payV.finishLoading(null);
                PaymentB paymentB = new Gson().fromJson(data, PaymentB.class);
                payV.pay(paymentB.getContractNo());
            }

            @Override
            public void onFail(String error) {
                payV.finishLoading(error);
            }
        });

    }

    private List<NetParams> payParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("payType", payV.getPayType()));
        params.add(new NetParams("contractNo", payV.getOrderNo()));
        return params;
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        return params;
    }
}
