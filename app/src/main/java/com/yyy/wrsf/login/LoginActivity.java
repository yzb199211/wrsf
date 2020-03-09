package com.yyy.wrsf.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.view.editclear.EditClearView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ecv_user)
    EditClearView ecvUser;
    @BindView(R.id.ecv_pwd)
    EditClearView ecvPwd;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        btnConfirm.setText(getString(R.string.common_login));
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
        new NetUtil(loginParam(), NetConfig.address + MemberURL.Login, RequstType.GET, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                LoadingFinish(null);
                Log.e(LoginActivity.this.getClass().getName(), "data:" + string);
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private List<NetParams> loginParam() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("memberTel", ecvUser.getText()));
        params.add(new NetParams("password", ecvPwd.getText()));
        params.add(new NetParams("roleType", "1"));
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
