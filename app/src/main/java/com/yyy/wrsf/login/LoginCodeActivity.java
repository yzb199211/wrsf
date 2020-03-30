package com.yyy.wrsf.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.bean.MemberBean;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.login.View.ILoginCodeV;
import com.yyy.wrsf.login.View.IVeridfyV;
import com.yyy.wrsf.login.persenter.LoginCodeVP;
import com.yyy.wrsf.login.persenter.VeridfyVP;
import com.yyy.wrsf.main.MainActivity;
import com.yyy.wrsf.model.LoginModel;
import com.yyy.wrsf.utils.PhoneUtils;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetLogin;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.utils.net.member.VerifyType;
import com.yyy.wrsf.view.VerificationCode;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.timecount.OnSendListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginCodeActivity extends BaseActivity implements ILoginCodeV {

    @BindView(R.id.ecv_phone)
    EditClearView ecvPhone;
    @BindView(R.id.vc_code)
    VerificationCode vcCode;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private SharedPreferencesHelper preferencesHelper;
    private VeridfyVP veridfyVP;
    private LoginCodeVP loginCodeVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_code);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        veridfyVP = new VeridfyVP(this);
        loginCodeVP = new LoginCodeVP(this);
        initView();
    }

    private void initView() {
        initConfirm();
        initCode();
    }

    private void initConfirm() {
        btnConfirm.setText(getString(R.string.common_login));
    }

    private void initCode() {
        vcCode.getCountDownButton().setOnSendListener(new OnSendListener() {
            @Override
            public void onSend() {
                closeKeybord();
                veridfyVP.veridfy();
            }
        });
    }

    @OnClick({R.id.tv_pwd_switch, R.id.tv_register, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pwd_switch:
                go2login();
                break;
            case R.id.tv_register:
                go2Register();
                break;
            case R.id.btn_confirm:
                loginCodeVP.login();
                break;
            default:
                break;
        }
    }


    @Override
    public void go2Main() {
        btnConfirm.setClickable(false);
        startActivity(new Intent().setClass(this, MainActivity.class));
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


    private void go2Register() {
        startActivity(new Intent().setClass(this, RegisterActivity.class));
    }

    private void go2login() {
        startActivity(new Intent().setClass(this, LoginActivity.class));
        finish();
    }

    @Override
    public String getTel() {
        return ecvPhone.getText();
    }

    @Override
    public String getVeridfyType() {
        return VerifyType.LOGIN.getCode();
    }

    @Override
    public boolean isEnabled() {
        return vcCode.getCountDownButton().isEnabled();
    }

    @Override
    public void startCount() {
        vcCode.getCountDownButton().startCount();
    }

    @Override
    public void startLoading() {
        LoadingDialog.showDialogForLoading(this);
    }

    @Override
    public void finishLoading(@Nullable String s) {
        LoadingFinish(s);
    }

    @Override
    public void toast(String s) {
        Toast(s);
    }

    @Override
    public String getCode() {
        return vcCode.getText();
    }

    @Override
    protected void onDestroy() {
        veridfyVP.detachView();
        loginCodeVP.detachView();
        super.onDestroy();
    }
}
