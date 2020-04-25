package com.yyy.wrsf.mine.pay.persenter;

import android.os.Handler;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.base.model.IBaseM;
import com.yyy.wrsf.mine.pay.view.IPayV;
import com.yyy.wrsf.utils.net.net.NetParams;

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

    }

    private List<NetParams> payParams() {
        List<NetParams> params = new ArrayList<>();
        return params;
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        return params;
    }
}
