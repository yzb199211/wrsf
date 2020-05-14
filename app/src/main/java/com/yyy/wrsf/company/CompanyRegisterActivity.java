package com.yyy.wrsf.company;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lxj.matisse.Matisse;
import com.lxj.matisse.MimeType;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.R;
import com.yyy.wrsf.beans.company.CompanyB;
import com.yyy.wrsf.common.address.AreaSelect;
import com.yyy.wrsf.common.address.OnBackAreaListener;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.PermissionListener;
import com.yyy.wrsf.beans.address.AreaB;
import com.yyy.wrsf.beans.company.CompanyRegisterB;
import com.yyy.wrsf.beans.ImageBean;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.net.UploadFile;
import com.yyy.wrsf.utils.net.company.CompanyUrl;
import com.yyy.wrsf.view.VerificationCode;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.iv_obverse)
    ImageView ivObverse;
    @BindView(R.id.iv_reverse)
    ImageView ivReverse;
    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    SharedPreferencesHelper preferencesHelper;

    private AreaSelect areaSelect;
    private AreaB province;
    private AreaB city;
    private AreaB district;
    private File fileObverse;
    private File fileReverse;
    private File fileOne;
    private File fileTwo;
    private File fileThree;
    private CompanyRegisterB companyRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        initData();
        init();
    }

    private void initData() {
        getData();
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.address + CompanyUrl.getApplyCompany, RequstType.GET, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        LoadingFinish(null);
                        CompanyB companyModel = new Gson().fromJson(result.getData(), CompanyB.class);
                        if (companyModel != null && companyModel.getCheckFinish() != null && companyModel.getCheckFinish().intValue() == 4) {
                            showEmpty();
                        } else {
                            shoDetail();
                        }
                    } else {
                        LoadingFinish(result.getMsg());
                    }
                } catch (JsonSyntaxException e) {
                    LoadingFinish(e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    LoadingFinish(e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        return params;
    }

    private void init() {
        companyRegister = new CompanyRegisterB();
        initArea();
        initSubmit();
        initTop();
    }

    private void showEmpty() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                llEmpty.setVisibility(View.VISIBLE);
            }
        });

    }

    private void shoDetail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scrollView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    private void initSubmit() {
        btnConfirm.setText(getString(R.string.common_submit));
    }

    private void initArea() {
        ecvArea.getTvText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectArea(v);
            }
        });
    }

    private void selectArea(View view) {
        if (areaSelect == null && province == null)
            areaSelect = new AreaSelect(CompanyRegisterActivity.this);
        else if (areaSelect == null) {
            areaSelect = new AreaSelect(this, province, city, district);
        }
        areaSelect.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        areaSelect.setOnBackAreaListener(new OnBackAreaListener() {
            @Override
            public void backArea(AreaB province, AreaB city, AreaB district) {
                CompanyRegisterActivity.this.province = province;
                CompanyRegisterActivity.this.city = city;
                CompanyRegisterActivity.this.district = district;
                setArea();
            }
        });
    }

    private void setArea() {
        if (province != null & city != null & district != null) {
            ecvArea.setText(province.getAreaName() + "\u3000" + city.getAreaName() + "\u3000" + district.getAreaName());
        } else if (province != null & city != null & district == null) {
            ecvArea.setText(province.getAreaName() + "\u3000" + city.getAreaName());
        } else if (province != null & city == null & district == null) {
            ecvArea.setText(province.getAreaName());
        }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            initPhoto(requestCode, getPath(data));
        }
    }

    private String getPath(Intent data) {
        String path = Matisse.obtainCaptureImageResult(data);
        if (TextUtils.isEmpty(path))
            path = Matisse.obtainSelectPathResult(data).get(0);
        return path;
    }

    private void initPhoto(int requestCode, String path) {
        switch (requestCode) {
            case CodeUtil.Photo_Obverse:

                fileObverse = new File(path);
                if (fileObverse.exists()) {
                    Glide.with(this).load(fileObverse).into(ivObverse);
                }
                break;
            case CodeUtil.Photo_Reverse:
                fileReverse = new File(path);
                if (fileReverse.exists()) {
                    Glide.with(this).load(fileReverse).into(ivReverse);
                }
                break;
            case CodeUtil.Photo_One:
                fileOne = new File(path);
                if (fileOne.exists()) {
                    Glide.with(this).load(fileOne).into(ivOne);
                }
                break;
            case CodeUtil.Photo_Two:
                fileTwo = new File(path);
                if (fileTwo.exists()) {
                    Glide.with(this).load(fileTwo).into(ivTwo);
                }
                break;
            case CodeUtil.Photo_Three:
                fileThree = new File(path);
                if (fileThree.exists()) {
                    Glide.with(this).load(fileThree).into(ivThree);
                }
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.iv_obverse, R.id.iv_reverse, R.id.btn_confirm, R.id.iv_one, R.id.iv_two, R.id.iv_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_obverse:
                setPhoto(CodeUtil.Photo_Obverse, 1);
                break;
            case R.id.iv_reverse:
                setPhoto(CodeUtil.Photo_Reverse, 1);
                break;
            case R.id.btn_confirm:
                if (canSave()) {
                    uploadFile(getLicenses(), 2);
                }
                break;
            case R.id.iv_one:
                setPhoto(CodeUtil.Photo_One, 1);
                break;
            case R.id.iv_two:
                setPhoto(CodeUtil.Photo_Two, 1);
                break;
            case R.id.iv_three:
                setPhoto(CodeUtil.Photo_Three, 1);
                break;
            default:
                break;
        }
    }

    private List<File> getLicenses() {
        List<File> files = new ArrayList<>();
        if (fileOne != null) {
            files.add(fileOne);
        }
        if (fileTwo != null) {
            files.add(fileTwo);
        }
        if (fileThree != null) {
            files.add(fileThree);
        }
        return files;
    }

    private boolean canSave() {
        if (TextUtils.isEmpty(ecvCompany.getText())) {
            Toast(ecvCompany.getHint());
            return false;
        }
        if (TextUtils.isEmpty(ecvArea.getText())) {
            Toast(ecvArea.getText());
            return false;
        }
        if (TextUtils.isEmpty(ecvAddressDetail.getText())) {
            Toast(ecvAddressDetail.getHint());
            return false;
        }
        if (TextUtils.isEmpty(ecvLegalPerson.getText())) {
            Toast(ecvLegalPerson.getHint());
            return false;
        }
        if (TextUtils.isEmpty(ecvBusinessLicense.getText())) {
            Toast(ecvBusinessLicense.getHint());
            return false;
        }
//        if (!StringUtil.isLicense15(ecvBusinessLicense.getText()) && !StringUtil.isLicense18(ecvBusinessLicense.getText())) {
//            Toast(getString(R.string.error_license));
//            return false;
//        }
        if (fileOne == null && fileTwo == null && fileThree == null) {
            Toast(getString(R.string.company_upload_license));
        }
        if (fileObverse == null && fileReverse == null) {
            Toast(getString(R.string.company_upload_IDCard));
        }
        setUploadData();
        return true;
    }

    private void setUploadData() {
        companyRegister.setCompanyName(ecvCompany.getText());
        companyRegister.setPerson(ecvLegalPerson.getText());
        if (province != null) companyRegister.setFristAdd(province.getAreaName());
        if (city != null) companyRegister.setSecondAdd(city.getAreaName());
        if (district != null) companyRegister.setThirdAdd(district.getAreaName());
        companyRegister.setDetailAdd(ecvAddressDetail.getText());
        companyRegister.setZhiZhao(ecvBusinessLicense.getText());
    }

    private void uploadCard() {
        List<File> files = new ArrayList<>();
        files.add(fileObverse);
        files.add(fileReverse);
        uploadFile(files, 3);
    }

    private void uploadFile(List<File> files, int type) {
        LoadingFinish(null);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.showDialogForLoading(CompanyRegisterActivity.this);
            }
        });
        UploadFile uploadFile = new UploadFile();
        uploadFile.uploadMultiFiles(NetConfig.address + "/files/uploadFiles", files, type);
        uploadFile.setResponseListener(new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        LoadingFinish(null);
                        if (type == 2) {
                            companyRegister.setZhiZhaoPics(setPics(result.getData()));
                            uploadCard();
                        } else if (type == 3) {
                            companyRegister.setContractPersonPics(setPics(result.getData()));
                            uploadData();
                        }
                    } else {
                        LoadingFinish(result.getMsg());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoadingFinish(e.getMessage());
                }
            }

            @Override
            public void onFail(Exception e) {
                LoadingFinish(e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private String setPics(String data) {
        List<ImageBean> imageModels = new Gson().fromJson(data, new TypeToken<List<ImageBean>>() {
        }.getType());
        String string = "";
        for (ImageBean item : imageModels) {
            string = string + item.getId() + ",";
        }
        return string;
    }

    private void uploadData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingFinish(null);
                LoadingDialog.showDialogForLoading(CompanyRegisterActivity.this);
            }
        });
        new NetUtil(uploadParams(),
                NetConfig.address + CompanyUrl.insert,
                RequstType.POST,
                new ResponseListener() {
                    @Override
                    public void onSuccess(String string) {
                        LoadingFinish(null);
                        Log.e(CompanyRegisterActivity.class.getName(), "data:" + string);
                        try {
                            Result result = new Result(string);
                            if (result.isSuccess()) {
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
                    public void onFail(Exception e) {
                        LoadingFinish(e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    private List<NetParams> uploadParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(companyRegister)));
        return params;
    }

    @Override
    public void onBackPressed() {
        if (areaSelect != null && areaSelect.isShowing()) {
            areaSelect.dismiss();
        } else
            super.onBackPressed();
    }
}
