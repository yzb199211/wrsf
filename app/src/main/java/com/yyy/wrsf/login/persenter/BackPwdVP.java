package com.yyy.wrsf.login.persenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.base.model.IBaseM;
import com.yyy.wrsf.bean.MemberBean;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.login.view.IBackPwdV;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class BackPwdVP implements IBackPwdVP {
    IBackPwdV iBackPwdV;
    IBaseM iBaseM;
    private Handler handler = new Handler();
    private boolean destroyFlag;

    public BackPwdVP(IBackPwdV iBackPwdV) {
        this.iBackPwdV = iBackPwdV;
        iBaseM = new BaseM();
    }

    @Override
    public void backPwd() {
        if (iBackPwdV.getCode().length() != 6) {
            iBackPwdV.toast(BaseApplication.getInstance().getString(R.string.error_verify_code));
            return;
        }
        if (iBackPwdV.getPwd().length() < 6) {
            iBackPwdV.toast(BaseApplication.getInstance().getString(R.string.error_pwd_length));
            return;
        }
        if (!iBackPwdV.getPwd().equals(iBackPwdV.getPwdComfir())) {
            iBackPwdV.toast(BaseApplication.getInstance().getString(R.string.error_pwd_confirm));
            return;
        }
        iBackPwdV.startLoading();
        iBaseM.Requset(getParams(), NetConfig.address + MemberURL.updateMember, RequstType.PUT, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iBackPwdV.backLogin();
                        iBackPwdV.finishLoading(null);
                    });
                }
            }

            @Override
            public void onFail(String string) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iBackPwdV.finishLoading(string);
                    });
                }
            }
        });
    }

    public List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(getMember())));
        return params;
    }

    public MemberBean getMember() {
        MemberBean memberBean = new MemberBean();
        memberBean.setPassWord(iBackPwdV.getPwd());
        memberBean.setValidate(iBackPwdV.getCode());
        return memberBean;
    }

    public void detachView() {
        destroyFlag = true;
        this.iBackPwdV = null;
    }
}
