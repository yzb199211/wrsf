package com.yyy.wrsf.mine.pay.persenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.base.model.IBaseM;
import com.yyy.wrsf.beans.PaymentB;
import com.yyy.wrsf.beans.WeiXinPay;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.pay.view.IPayV;
import com.yyy.wrsf.utils.Toasts;
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
                if (!destroyFlag)
                    handler.post(() -> {
                        payV.finishLoading(null);
                        PaymentB paymentB = new Gson().fromJson(data, PaymentB.class);
                        payV.pay(paymentB.getQrCode());
                    });

            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) payV.finishLoading(error);
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

    public PayReq getWexinReq(String order) {
        PayReq request = new PayReq();
        try {
            WeiXinPay pay = new Gson().fromJson(order, WeiXinPay.class);
            request.appId = pay.getAppid();
            request.prepayId = pay.getPrepayid();
            request.partnerId = pay.getPartnerid();
            request.packageValue = "Sign=WXPay";
            request.nonceStr = pay.getNoncestr();
            request.timeStamp = pay.getTimestamp();
            request.sign = pay.getSign();
            return request;
        } catch (JsonSyntaxException e) {
            payV.toast(BaseApplication.getInstance().getString(R.string.error_json));
            return request;
        }
    }

    public void detachView() {
        destroyFlag = true;
        this.payV = null;
    }
}
