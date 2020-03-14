package com.yyy.wrsf.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.main.MainActivity;
import com.yyy.wrsf.model.LoginModel;
import com.yyy.wrsf.utils.PhoneUtils;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.utils.net.Result;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.utils.net.member.VerifyType;
import com.yyy.wrsf.view.VerificationCode;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.timecount.OnSendListener;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginCodeActivity extends AppCompatActivity {

    @BindView(R.id.ecv_phone)
    EditClearView ecvPhone;
    @BindView(R.id.vc_code)
    VerificationCode vcCode;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_code);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
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
                if (!PhoneUtils.isNotValidChinesePhone(ecvPhone.getText()) && vcCode.getCountDownButton().isEnabled()) {
                    vcCode.getCountDownButton().startCount();
//                    ecvPhone.forbidEdit();
                    getVeridfy();
                }
            }
        });
    }

    private void getVeridfy() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(verifyParams(), NetConfig.address + MemberURL.VERIFY, RequstType.GET, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                LoadingFinish(null);
                Log.e(LoginCodeActivity.this.getClass().getName(), "code:" + string);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        LoadingFinish(getString(R.string.common_code_success));
//                        finish();
                    } else {
                        LoadingFinish(result.getMsg());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoadingFinish(e.getMessage());
                }
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private List<NetParams> verifyParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("memberTel", ecvPhone.getText()));
        params.add(new NetParams("type", VerifyType.LOGIN.getCode()));
        return params;
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
                if (PhoneUtils.isNotValidChinesePhone(ecvPhone.getText())) {
                    Toasts.showLong(LoginCodeActivity.this, getString(R.string.error_phone));
                    return;
                }
                if (vcCode.getText().length() != 6) {
                    Toasts.showLong(LoginCodeActivity.this, getString(R.string.error_verify_code));
                    return;
                }
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(loginParams(), NetConfig.address + MemberURL.fastLogin, RequstType.GET, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                LoadingFinish(null);
//                Log.e(LoginCodeActivity.this.getClass().getName(), "data:" + string);
                Result result = null;
                try {
                    result = new Result(string);
                    if (result.isSuccess()) {
                        LoadingFinish(null);
                        try {
                            JSONObject jsonObject = new JSONObject(result.getData());
                            String data = jsonObject.optString("loginUser", "");
                            if (TextUtils.isEmpty(data)) {
                                Toast(getString(R.string.net_empty_data));
                            } else {
                                setPreference(jsonObject, new Gson().fromJson(data, LoginModel.class));
                                go2Main();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(LoginCodeActivity.this.getClass().getName(), result.getMsg());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private void go2Main() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent().setClass(LoginCodeActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void setPreference(JSONObject data, LoginModel model) {
        preferencesHelper.put("member", data);
        preferencesHelper.put("recNo", model.getRecNo());
        preferencesHelper.put("tel", model.getTel());
        preferencesHelper.put("sex", model.getSex());
        preferencesHelper.put("petname", model.getPetname());
        preferencesHelper.put("main", model.getMail());
        preferencesHelper.put("companyName", model.getCompanyName());
        preferencesHelper.put("roleType", (int) model.getRoleType());
        preferencesHelper.put("token", data.optString("token", ""));
    }

    private List<NetParams> loginParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("memberTel", ecvPhone.getText()));
        params.add(new NetParams("validate", vcCode.getText()));
        return params;
    }

    private void go2Register() {
        startActivity(new Intent().setClass(this, RegisterActivity.class));
    }

    private void go2login() {
        startActivity(new Intent().setClass(this, LoginActivity.class));
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
