package com.yyy.wrsf.login.persenter;

import android.os.Handler;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.bean.MemberBean;
import com.yyy.wrsf.beans.MemberB;
import com.yyy.wrsf.beans.VersionB;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.login.model.ILoginM;
import com.yyy.wrsf.login.model.LoginM;
import com.yyy.wrsf.login.view.ILoginV;
import com.yyy.wrsf.utils.VersionUtil;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.Result;

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
        iLoginV.startLoading();
        iLoginM.login(getParams(), NetConfig.address + MemberURL.Login, RequstType.GET, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
                if (!destroyFlag) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                iLoginV.setPreference(new Gson().fromJson(string, MemberBean.class));
                                iLoginV.go2Main();
                                iLoginV.finishLoading(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                                iLoginV.finishLoading(BaseApplication.getInstance().getString(R.string.error_data));
                            }
                        }
                    });
                }
            }

            @Override
            public void onFail(String string) {
                if (!destroyFlag) {
                    iLoginV.finishLoading(string);
                }
            }
        });
    }

    @Override
    public void getVersion() {
        iLoginV.startLoading();
        iLoginM.login(versionParams(), NetConfig.address + MemberURL.getVersionById, RequstType.GET, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iLoginV.finishLoading(null);
                        VersionB versionB = new Gson().fromJson(data, VersionB.class);
                        if (versionB.getVersion() != null && versionB.getVersionId() > VersionUtil.getAppVersionCode(BaseApplication.getInstance())) {
                            iLoginV.judgeDownloadPermission();
                        }
                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag)
                    handler.post(() -> {
                        iLoginV.finishLoading(error);
                    });

            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("memberTel", iLoginV.getUser()));
        params.add(new NetParams("password", iLoginV.getPwd()));
        return params;
    }

    private List<NetParams> versionParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("id", "1"));
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.iLoginV = null;
    }
}
