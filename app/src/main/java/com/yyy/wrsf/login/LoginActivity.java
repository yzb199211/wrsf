package com.yyy.wrsf.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.R;
import com.yyy.wrsf.bean.MemberBean;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.login.view.ILoginV;
import com.yyy.wrsf.login.persenter.LoginVP;
import com.yyy.wrsf.main.MainActivity;
import com.yyy.wrsf.utils.AESUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.view.editclear.EditClearView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginV {

    @BindView(R.id.ecv_user)
    EditClearView ecvUser;
    @BindView(R.id.ecv_pwd)
    EditClearView ecvPwd;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    SharedPreferencesHelper preferencesHelper;
    private LoginVP loginVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        loginVP = new LoginVP(this);
        initView();
        try {
            AESUtil.getCode("huanxin.wanruisf", "123456");
            Log.e("pwd", AESUtil.getCode("huanxin.wanruisf", "123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                loginVP.login();
                break;
        }
    }

    @Override
    public String getUser() {
        return ecvUser.getText();
    }

    @Override
    public String getPwd() {
        return ecvPwd.getText();
    }

    @Override
    public void startLoading() {
        LoadingDialog.showDialogForLoading(this);
    }

    @Override
    public void finishLoading(@NonNull String s) {
        LoadingFinish(s);
    }

    @Override
    public void go2Main() {
        btnConfirm.setClickable(false);
        startActivity(new Intent().setClass(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void setPreference(MemberBean model) {
        preferencesHelper.put("member", new Gson().toJson(model));
        preferencesHelper.put("recNo", model.getRecNo());
        preferencesHelper.put("tel", model.getMemberTel());
        preferencesHelper.put("sex", model.getMemberSex());
        preferencesHelper.put("petname", model.getMemberPetname());
        preferencesHelper.put("main", model.getMail());
        preferencesHelper.put("companyName", model.getCompanyName());
        preferencesHelper.put("roleType", (int) model.getRoleType());
        preferencesHelper.put("token", model.getToken());
        preferencesHelper.put("authority", model.getRoles().get(0).getName());
    }

    @Override
    public void toast(String s) {
        Toast(s);
    }


    private void go2Register() {
        startActivity(new Intent().setClass(this, RegisterActivity.class));
    }

    private void go2LoginCode() {
        startActivity(new Intent().setClass(this, LoginCodeActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        loginVP.detachView();
        super.onDestroy();
    }
}
