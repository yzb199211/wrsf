package com.yyy.wrsf.login.persenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.bean.MemberBean;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.login.model.ILoginM;
import com.yyy.wrsf.login.model.LoginM;
import com.yyy.wrsf.login.view.ILoginCodeV;
import com.yyy.wrsf.utils.PhoneUtils;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class LoginCodeVP implements ILoginVP {
    private ILoginCodeV iLoginCodeV;
    private ILoginM iLoginM;
    private Handler handler = new Handler();
    private boolean destroyFlag;

    public LoginCodeVP(ILoginCodeV iLoginCodeV) {
        this.iLoginCodeV = iLoginCodeV;
        iLoginM = new LoginM();
    }

    @Override
    public void login() {
        if (PhoneUtils.isNotValidChinesePhone(iLoginCodeV.getTel())) {
            iLoginCodeV.toast(BaseApplication.getInstance().getString(R.string.error_phone));
            return;
        }
        if (iLoginCodeV.getCode().length() != 6) {
            iLoginCodeV.toast(BaseApplication.getInstance().getString(R.string.error_verify_code));
            return;
        }
        iLoginM.login(getParams(), NetConfig.address + MemberURL.fastLogin, RequstType.GET, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iLoginCodeV.setPreference(new Gson().fromJson(string, MemberBean.class));
                        iLoginCodeV.go2Main();
                        iLoginCodeV.finishLoading(null);
                    });
                }
            }

            @Override
            public void onFail(String string) {
                if (!destroyFlag) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iLoginCodeV.finishLoading(string);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getVersion() {

    }

    public List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("memberTel", iLoginCodeV.getTel()));
        params.add(new NetParams("validate", iLoginCodeV.getCode()));
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.iLoginCodeV = null;
    }
}
