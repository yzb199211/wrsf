package com.yyy.wrsf.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.model.MemberModel;
import com.yyy.wrsf.utils.PhoneUtils;
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
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTop();
        initConfirm();
        initCode();
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    private void initCode() {
        vcCode.getCountDownButton().setOnSendListener(new OnSendListener() {
            @Override
            public void onSend() {
                if (!PhoneUtils.isNotValidChinesePhone(ecvPhone.getText()) && vcCode.getCountDownButton().isEnabled()) {
                    vcCode.getCountDownButton().startCount();
                    ecvPhone.forbidEdit();
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
                Log.e(RegisterActivity.this.getClass().getName(), "code:" + string);
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
        params.add(new NetParams("type", VerifyType.REGISTER.getCode()));
        return params;
    }


    private void initConfirm() {
        btnConfirm.setText(getString(R.string.login_register));
    }

    @OnClick({R.id.btn_confirm})
    public void onViewClicked(View view) {
        if (vcCode.getText().length() != 6) {
            Toasts.showLong(RegisterActivity.this, getString(R.string.error_verify_code));
            return;
        }
        if (ecvPwd.getText().length() < 6) {
            Toasts.showLong(RegisterActivity.this, getString(R.string.error_pwd_length));
            return;
        }
        if (!ecvPwd.getText().equals(ecvPwdConfirm.getText())) {
            Toasts.showLong(RegisterActivity.this, getString(R.string.error_pwd_confirm));
            return;
        }
        register();
    }

    private void register() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(registerParams(), NetConfig.address + MemberURL.REGISTER, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                LoadingFinish(null);
                Log.e(RegisterActivity.this.getClass().getName(), "data:" + string);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        LoadingFinish(getString(R.string.login_register_success));
                        finish();
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

    private List<NetParams> registerParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("platMemberBo", getMember()));
        return params;
    }

    private String getMember() {
        MemberModel model = new MemberModel();
        model.setMemberTel(ecvPhone.getText());
        model.setPassWord(ecvPwd.getText());
        model.setValidate(vcCode.getText());
        return new Gson().toJson(model);
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
