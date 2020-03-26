package com.yyy.wrsf.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.yyy.wrsf.BaseActivity;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.main.MainActivity;
import com.yyy.wrsf.model.LoginModel;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetLogin;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.view.editclear.EditClearView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.ecv_user)
    EditClearView ecvUser;
    @BindView(R.id.ecv_pwd)
    EditClearView ecvPwd;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        initView();
    }

    private void initView() {
        btnConfirm.setText(getString(R.string.common_login));
        ecvUser.setText((String) preferencesHelper.getSharedPreference("tel", ""));
    }

    @OnClick({R.id.tv_pwd_switch, R.id.tv_register, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pwd_switch:
                go2LoginCode();
                break;
            case R.id.tv_register:
                go2Register();
                break;
            case R.id.btn_confirm:
                if (TextUtils.isEmpty(ecvUser.getText())) {
                    Toasts.showLong(LoginActivity.this, getString(R.string.error_user));
                    return;
                }
                if (ecvPwd.getText().length() < 6) {
                    Toasts.showLong(LoginActivity.this, getString(R.string.error_pwd_length));
                    return;
                }
                login();
                break;
        }
    }

    private void login() {
        LoadingDialog.showDialogForLoading(this);
        new NetLogin(loginParam(), NetConfig.address + MemberURL.Login, RequstType.GET, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                LoadingFinish(null);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        LoadingFinish(null);
                        setPreference(result.getData(), new Gson().fromJson(result.getData(), LoginModel.class));
                        go2Main();
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(LoginActivity.this.getClass().getName(), result.getMsg());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoadingFinish(e.getMessage());
                }

            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private void go2Main() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent().setClass(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void setPreference(String data, LoginModel model) {
        preferencesHelper.put("member", data);
        preferencesHelper.put("recNo", model.getRecNo());
        preferencesHelper.put("tel", model.getMemberTel());
        preferencesHelper.put("sex", model.getMemberSex());
        preferencesHelper.put("petname", model.getMemberPetname());
        preferencesHelper.put("main", model.getMail());
        preferencesHelper.put("companyName", model.getCompanyName());
        preferencesHelper.put("roleType", (int) model.getRoleType());
        preferencesHelper.put("token", model.getToken());
    }

    private List<NetParams> loginParam() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("memberTel", ecvUser.getText()));
        params.add(new NetParams("password", ecvPwd.getText()));
        return params;
    }

    private void go2Register() {
        startActivity(new Intent().setClass(this, RegisterActivity.class));
    }

    private void go2LoginCode() {
        startActivity(new Intent().setClass(this, LoginCodeActivity.class));
        finish();
    }

    private void LoadingFinish(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (StringUtil.isNotEmpty(msg)) {
                    Toast(msg);
                }
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private void Toast(String msg) {
        Toasts.showShort(this, msg);
    }

}
