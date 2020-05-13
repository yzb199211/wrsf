package com.yyy.wrsf.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.login.view.IBackPwdV;
import com.yyy.wrsf.login.persenter.BackPwdVP;
import com.yyy.wrsf.login.persenter.VeridfyVP;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.net.member.VerifyType;
import com.yyy.wrsf.view.VerificationCode;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.timecount.OnSendListener;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PwdBackActivity extends BaseActivity implements IBackPwdV {
    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_phone)
    EditClearView ecvPhone;
    @BindView(R.id.vc_code)
    VerificationCode vcCode;
    @BindView(R.id.ecv_pwd)
    EditClearView ecvPwd;
    @BindView(R.id.ecv_pwd_confirm)
    EditClearView ecvPwdConfirm;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private SharedPreferencesHelper preferencesHelper;
    private VeridfyVP veridfyVP;
    private BackPwdVP backPwdVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_back);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        veridfyVP = new VeridfyVP(this);
        backPwdVP = new BackPwdVP(this);
        initView();
    }

    private void initView() {
        initTop();
        initConfirm();
        initCode();
        initPhone();
    }

    private void initPhone() {
        ecvPhone.setText((String) preferencesHelper.getSharedPreference("tel", ""));
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    private void initConfirm() {
        btnConfirm.setText(getString(R.string.common_submit));
    }

    private void initCode() {
        vcCode.getCountDownButton().setOnSendListener(new OnSendListener() {
            @Override
            public void onSend() {
                veridfyVP.veridfy();
            }
        });
    }


    @OnClick({R.id.btn_confirm})
    public void onViewClicked(View view) {
        backPwdVP.backPwd();
    }


    @Override
    public String getCode() {
        return vcCode.getText();
    }

    @Override
    public void backLogin() {
        startActivity(new Intent().setClass(this, LoginActivity.class));
    }

    @Override
    public String getPwd() {
        return ecvPwd.getText();
    }

    @Override
    public String getPwdComfir() {
        return ecvPwdConfirm.getText();
    }

    @Override
    public String getTel() {
        return ecvPhone.getText();
    }

    @Override
    public String getVeridfyType() {
        return VerifyType.BACK.getCode();
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
    protected void onDestroy() {
        veridfyVP.detachView();
        backPwdVP.detachView();
        super.onDestroy();
    }
}
