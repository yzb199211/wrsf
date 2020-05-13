package com.yyy.wrsf.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.yyy.wrsf.BuildConfig;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.bean.MemberBean;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.PermissionListener;
import com.yyy.wrsf.login.persenter.LoginVP;
import com.yyy.wrsf.login.view.ILoginV;
import com.yyy.wrsf.main.MainActivity;
import com.yyy.wrsf.utils.AESUtil;
import com.yyy.wrsf.utils.FileUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.VersionUtil;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.view.editclear.EditClearView;

import java.io.File;
import java.util.List;

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
    @BindView(R.id.cb_pwd)
    CheckBox cbPwd;
    private LoginVP loginVP;
    private boolean remember;
    int versionSystem;
    String dowmloadUrl;

    ProgressDialog progressDialog;
    //    Dialog loading;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        loginVP = new LoginVP(this);
        init();

//        try {
//            AESUtil.getCode("huanxin.wanruisf", "123456");
////            Log.e("pwd", AESUtil.getCode("huanxin.wanruisf", "123456"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void init() {
        initVersion();
        initView();
        loginVP.getVersion();
    }

    private void initVersion() {
        versionSystem = VersionUtil.getAppVersionCode(this);
    }

    private void initView() {
        btnConfirm.setText(getString(R.string.common_login));
        ecvUser.setText((String) preferencesHelper.getSharedPreference("tel", ""));
        if (StringUtil.isNotEmpty((String) preferencesHelper.getSharedPreference("tel", "")))
            initPwd();
    }

    private void initPwd() {
        Log.e("pwd", (String) preferencesHelper.getSharedPreference("pwd", ""));
        remember = (boolean) preferencesHelper.getSharedPreference("remember", false);
        cbPwd.setChecked(remember);
        if (remember) {
            ecvPwd.setText((String) preferencesHelper.getSharedPreference("pwd", ""));
        }
        initRemember();
    }

    private void initRemember() {
        cbPwd.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                preferencesHelper.put("remember", true);
            } else {
                preferencesHelper.put("pwd", "");
                preferencesHelper.put("remember", false);
            }
        });
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
        if (remember)
            preferencesHelper.put("pwd", getPwd());
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

    /*下载进度更新*/
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 11) {
                progressDialog.setProgress((Integer) msg.obj);
            }
            if (msg.what == 0) {
                installApk(file);
            }
        }

        ;
    };

    /*显示下载进度*/
    private void showPrograss() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("正在下载");
        progressDialog.setMessage("请稍后...");
        progressDialog.setMax(100);
        progressDialog.show();
    }

    /*安装apk*/
    public void installApk(File apkPath) {
        //安装跳转
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            /* Android N 写法*/
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(LoginActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", apkPath);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkPath),
                    "application/vnd.android.package-archive");
        }
        try {
            startActivity(intent);

        } catch (ActivityNotFoundException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void judgeDownloadPermission() {
        /*获取文件下载权限*/
        requestRunPermisssion(new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                downFile(dowmloadUrl);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                toast("");
            }
        });

    }


    /*apk下载*/
    private void downFile(String url) {
        showPrograss();
        File dirfile = FileUtil.creatDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wrsf");
        file = FileUtil.creatFile(dirfile, "wrsf.apk");


        new NetUtil(url, Environment.getExternalStorageDirectory().getAbsolutePath(), "/wrsf/wrsf.apk", new NetUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                //下载完成进行相关逻辑操作
                Message msg = mHandler.obtainMessage();
                msg.what = 0;
                mHandler.sendMessage(msg);

            }

            @Override
            public void onDownloading(int progress) {
//                        progressDialog.setProgress(progress);

                Message msg = mHandler.obtainMessage();
                msg.what = 11;
                msg.obj = progress;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onDownloadFailed(Exception e) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                //下载异常进行相关提示操作
                Message msg = mHandler.obtainMessage();
                msg.what = 1;
                msg.obj = e;
                mHandler.sendMessage(msg);
            }
        });


    }

}
