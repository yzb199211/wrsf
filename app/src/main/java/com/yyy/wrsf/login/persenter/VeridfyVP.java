package com.yyy.wrsf.login.persenter;

import android.os.Handler;

import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.base.model.IBaseM;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.login.view.IVeridfyV;
import com.yyy.wrsf.utils.PhoneUtils;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class VeridfyVP implements IVeridfyVP {
    private IVeridfyV iVeridfyV;
    private IBaseM iBaseM;
    private Handler handler = new Handler();
    private boolean destroyFlag;

    public VeridfyVP(IVeridfyV iVeridfyV) {
        this.iVeridfyV = iVeridfyV;
        iBaseM = new BaseM();
    }

    @Override
    public void veridfy() {
        if (PhoneUtils.isNotValidChinesePhone(iVeridfyV.getTel())) {
            iVeridfyV.toast(BaseApplication.getInstance().getString(R.string.error_phone));
            return;
        }
        if (!iVeridfyV.isEnabled()) {
            return;
        }

        iBaseM.Requset(getParams(), NetConfig.address + MemberURL.VERIFY, RequstType.GET, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iVeridfyV.toast(string);
                        iVeridfyV.startCount();
                    });
                }
            }

            @Override
            public void onFail(String string) {
                if (!destroyFlag) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iVeridfyV.finishLoading(string);
                        }
                    });
                }
            }
        });
    }

    public List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("memberTel", iVeridfyV.getTel()));
        params.add(new NetParams("type", iVeridfyV.getVeridfyType()));
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.iVeridfyV = null;
    }
}
