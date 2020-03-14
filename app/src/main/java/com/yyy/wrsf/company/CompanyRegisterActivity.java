package com.yyy.wrsf.company;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lxj.matisse.Matisse;
import com.lxj.matisse.MimeType;
import com.yyy.wrsf.BaseActivity;
import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.PermissionListener;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.view.VerificationCode;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.TopView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CompanyRegisterActivity extends BaseActivity {

    @BindView(R.id.ecv_company)
    EditClearView ecvCompany;
    @BindView(R.id.ecv_area)
    EditClearView ecvArea;
    @BindView(R.id.ecv_address_detail)
    EditClearView ecvAddressDetail;
    @BindView(R.id.ecv_legal_person)
    EditClearView ecvLegalPerson;
    @BindView(R.id.ecv_contract)
    EditClearView ecvContract;
    @BindView(R.id.ecv_tel)
    EditClearView ecvTel;
    @BindView(R.id.vc_code)
    VerificationCode vcCode;
    @BindView(R.id.ecv_business_license)
    EditClearView ecvBusinessLicense;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.iv_obverse)
    ImageView ivObverse;
    @BindView(R.id.iv_reverse)
    ImageView ivReverse;
    @BindView(R.id.top_view)
    TopView topView;

    SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
    }

    private void init() {
    }

    private void setPhoto(int type, int size) {
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                Matisse.from(CompanyRegisterActivity.this)
                        .choose(MimeType.of(MimeType.JPEG), false)
                        .countable(true)
                        .maxSelectable(size)
                        .capture(true)
//                        .captureStrategy(new CaptureStrategy(true, "com.yyy.wrsf.fileProvider"))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
//                        .imageEngine(new GlideEngine())
                        .forResult(type);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                Toast(getString(R.string.error_permission));
            }
        });
    }

    private void Toast(String msg) {
        Toasts.showShort(this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CodeUtil.Address) {

        } else if (resultCode == RESULT_OK) {
//            Log.e("s", Matisse.obtainOriginalState(data) + "");
            initPhoto(requestCode, data);
        }
    }

    File fileObverse;
    File fileReverse;

    private void initPhoto(int requestCode, Intent data) {
        switch (requestCode) {
            case CodeUtil.Photo_License:
                break;
            case CodeUtil.Photo_Obverse:
                String path = Matisse.obtainCaptureImageResult(data);
                if (TextUtils.isEmpty(path))
                    path = Matisse.obtainSelectPathResult(data).get(0);
                fileObverse = new File(path);
                if (fileObverse.exists()) {
                    Glide.with(this).load(fileObverse).into(ivObverse);
                }
                break;
            case CodeUtil.Photo_Reverse:
                String path1 = Matisse.obtainCaptureImageResult(data);
                if (TextUtils.isEmpty(path1))
                    path1 = Matisse.obtainSelectPathResult(data).get(0);
                fileReverse = new File(path1);
                if (fileReverse.exists()) {
                    Glide.with(this).load(fileReverse).into(ivReverse);
                }
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.iv_obverse, R.id.iv_reverse, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_obverse:
                setPhoto(CodeUtil.Photo_Obverse, 1);
                break;
            case R.id.iv_reverse:
                setPhoto(CodeUtil.Photo_Reverse, 1);
                break;
            case R.id.ecv_area:

                break;
            case R.id.btn_confirm:
                List<File> files = new ArrayList<>();
                files.add(fileObverse);
                files.add(fileReverse);
                uploadMultiFiles(NetConfig.address + "/files/uploadFiles", files);
                break;
        }
    }

    private void uploadMultiFiles(String url, List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("fileList", file.getName(), fileBody);
        }
        builder.addFormDataPart("userType", "3");
        MultipartBody multipartBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(multipartBody)
                .addHeader("token", (String) preferencesHelper.getSharedPreference("token", ""))
                .build();

        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                try {
                    String jsonStr = response.body().string();
                    Log.i("EvaluateActivity", "uploadMultiFile() response=" + jsonStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }

}
