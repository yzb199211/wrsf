package com.yyy.wrsf.login.persenter;

import android.os.Handler;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.bean.MemberBean;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.login.Model.ILoginM;
import com.yyy.wrsf.login.Model.LoginM;
import com.yyy.wrsf.login.View.ILoginV;
import com.yyy.wrsf.utils.net.log.LogUrl;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class LoginVP implements ILoginVP {
    private ILoginV iLoginV;
    private ILoginM iLoginM;
    private Handler handler = new Handler();
    private boolean destroyFlag;

    public LoginVP(ILoginV iLoginV) {
        this.iLoginV = iLoginV;
        this.iLoginM = new LoginM();
    }

    @Override
    public void login() {
        if (TextUtils.isEmpty(iLoginV.getUser())) {
            iLoginV.toast(BaseApplication.getInstance().getString(R.string.error_user));
            return;
        }
        if (iLoginV.getPwd().length() < 6) {
            iLoginV.toast(BaseApplication.getInstance().getString(R.string.error_pwd_length));
            return;
        }
        iLoginV.stratLoding();
        iLoginM.login(getParams(), NetConfig.address + MemberURL.Login, RequstType.GET, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
                if (!destroyFlag) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iLoginV.setPreference(new Gson().fromJson(string, MemberBean.class));
                            iLoginV.go2Main();
                            iLoginV.finishLoding(null);
                        }
                    });
                }
            }

            @Override
            public void onFail(String string) {
                if (!destroyFlag) {
                    iLoginV.finishLoding(string);
                }
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("memberTel", iLoginV.getUser()));
        params.add(new NetParams("password", iLoginV.getPwd()));
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.iLoginV = null;
    }
}
