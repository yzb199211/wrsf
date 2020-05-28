package com.yyy.wrsf.mine.month;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.matisse.Matisse;
import com.lxj.matisse.MimeType;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.base.BasePickActivity;
import com.yyy.wrsf.beans.ImageBean;
import com.yyy.wrsf.beans.address.AreaB;
import com.yyy.wrsf.beans.company.CompanyB;
import com.yyy.wrsf.beans.filter.CompanyFilterB;
import com.yyy.wrsf.beans.month.MonthB;
import com.yyy.wrsf.common.address.AreaSelect;
import com.yyy.wrsf.common.address.OnBackAreaListener;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.PermissionListener;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.net.company.CompanyUrl;
import com.yyy.wrsf.utils.net.month.MonthUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.net.UploadFile;
import com.yyy.wrsf.view.VerificationCode;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.OptionsPickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnOptionsSelectListener;
import com.yyy.yyylibrary.pick.view.OptionsPickerView;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MonthApplyModifyActivity extends BasePickActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_trans)
    EditClearView ecvTrans;
    @BindView(R.id.ecv_company)
    EditClearView ecvCompany;
    @BindView(R.id.ecv_area)
    EditClearView ecvArea;
    @BindView(R.id.ecv_address_detail)
    EditClearView ecvAddressDetail;
    @BindView(R.id.ecv_legal_person)
    EditClearView ecvLegalPerson;
    @BindView(R.id.ecv_business_license)
    EditClearView ecvBusinessLicense;
    @BindView(R.id.iv_one)
    ImageView ivOne;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.iv_obverse)
    ImageView ivObverse;
    @BindView(R.id.iv_reverse)
    ImageView ivReverse;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
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

    private MonthB monthModel;
    private List<CompanyB> companys = new ArrayList<>();
    private PagerRequestBean<CompanyFilterB> pager;

    private OptionsPickerView pvCompany;

    private int pos;
//    private MonthB item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_apply);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        monthModel = new MonthB();
        initData();
        initPagerData();
        initArea();
//        initCompany();
        initSubmit();
        initTop();
        setView();
    }

    private void initData() {
        pos = getIntent().getIntExtra("pos", 0);
        monthModel = new Gson().fromJson(getIntent().getStringExtra("data"), MonthB.class);
        initAreaData();
        clearPics();
    }

    private void initAreaData() {

        if (monthModel.getFirstId()!=0&& StringUtil.isNotEmpty(monthModel.getFirstAdd())){
            province = new AreaB();
            province.setAreaName(monthModel.getFirstAdd());
            province.setDisplayName(monthModel.getFirstAdd());
            province.setId(monthModel.getFirstId());
        }else {
            return;
        }
        if (monthModel.getSecondId()!=0&& StringUtil.isNotEmpty(monthModel.getSecondAdd())){
            city = new AreaB();
            city.setAreaName(monthModel.getSecondAdd());
            city.setDisplayName(monthModel.getSecondAdd());
            city.setId(monthModel.getSecondId());
            city.setParentId(monthModel.getFirstId());
        }else {
            return;
        }
        if (monthModel.getThirdId()!=0&& StringUtil.isNotEmpty(monthModel.getThirdAdd())){
            district = new AreaB();
            district.setAreaName(monthModel.getThirdAdd());
            district.setDisplayName(monthModel.getThirdAdd());
            district.setId(monthModel.getThirdId());
            district.setParentId(monthModel.getSecondId());
        }else {
            return;
        }
    }

    private void clearPics() {
        monthModel.setContractPersonPics(null);
        monthModel.setZhiZhaoPics(null);
        monthModel.setCheckFinish(null);
    }

    private void setView() {
        ecvTrans.setText(monthModel.getTransCompanyName());
        ecvCompany.setText(monthModel.getCompanyName());
        ecvArea.setText(monthModel.getFirstAdd()+"\u3000"+monthModel.getSecondAdd()+"\u3000"+monthModel.getThirdAdd());
        ecvAddressDetail.setText(monthModel.getDetailAdd());
        ecvBusinessLicense.setText(monthModel.getZhiZhao());
        ecvLegalPerson.setText(monthModel.getPerson());
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


    private void initPagerData() {
        pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(500);
        CompanyFilterB filterModel = new CompanyFilterB();
        filterModel.setCheckFinish(4);
        pager.setQueryParam(filterModel);

        String s = new Gson().toJson(filterModel);
    }

    private void initArea() {
        ecvArea.getTvText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectArea(v);
            }
        });
    }

    private void initCompany() {
        ecvTrans.getTvText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (companys.size() == 0) {
                    getCompany();
                } else {
                    pvCompany.show();
                }
            }
        });
    }

    private void selectArea(View view) {
        if (areaSelect == null && province == null)
            areaSelect = new AreaSelect(MonthApplyModifyActivity.this);
        else if (areaSelect == null) {
            areaSelect = new AreaSelect(this, province, city, district);
        }
        areaSelect.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        areaSelect.setOnBackAreaListener(new OnBackAreaListener() {
            @Override
            public void backArea(AreaB province, AreaB city, AreaB district) {
                MonthApplyModifyActivity.this.province = province;
                MonthApplyModifyActivity.this.city = city;
                MonthApplyModifyActivity.this.district = district;
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

    private void getCompany() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.showDialogForLoading(MonthApplyModifyActivity.this);
            }
        });
        new NetUtil(getCompanyParams(), NetConfig.address + CompanyUrl.getCompany, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(MonthApplyModifyActivity.this.getClass().getName(), "data:" + string);
                try {
                    LoadingFinish(null);
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<CompanyB> list = new Gson().fromJson(result.getData(), new TypeToken<List<CompanyB>>() {
                        }.getType());
                        if (list != null) {
                            companys.addAll(list);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initCompanyPick();
                                }
                            });
                        }
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(MonthApplyModifyActivity.class.getName(), result.getMsg());
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

    private void initCompanyPick() {
        pvCompany = new OptionsPickerBuilder(MonthApplyModifyActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ecvTrans.setText(companys.get(options1).getPickerViewText());
                monthModel.setTransCompanyRecNo(companys.get(options1).getRecNo());
            }
        })
                .setTitleText(getString(R.string.company_titlte))
                .setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .build();
        pvCompany.setPicker(companys);//一级选择器
        setDialog(pvCompany);
        pvCompany.show();
    }

    private List<NetParams> getCompanyParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(pager)));
        return params;
    }

    private void setPhoto(int type, int size) {
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                Matisse.from(MonthApplyModifyActivity.this)
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

    @OnClick({R.id.iv_one, R.id.iv_two, R.id.iv_three, R.id.iv_obverse, R.id.iv_reverse, R.id.btn_confirm})
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
        if (district==null){
            Toast(getString(R.string.error_area));
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
        monthModel.setCompanyName(ecvCompany.getText());
        monthModel.setPerson(ecvLegalPerson.getText());
        if (province != null) {
            monthModel.setFirstAdd(province.getAreaName());
            monthModel.setFirstId(province.getId());
        }
        if (city != null) {
            monthModel.setSecondAdd(city.getAreaName());
            monthModel.setSecondId(city.getId());
        }
        if (district != null) {
            monthModel.setThirdAdd(district.getAreaName());
            monthModel.setThirdId(district.getId());
        }
        monthModel.setDetailAdd(ecvAddressDetail.getText());
        monthModel.setZhiZhao(ecvBusinessLicense.getText());
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
                LoadingDialog.showDialogForLoading(MonthApplyModifyActivity.this);
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
                            monthModel.setZhiZhaoPics(setPics(result.getData()));
                            uploadCard();
                        } else if (type == 3) {
                            monthModel.setContractPersonPics(setPics(result.getData()));
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
                LoadingDialog.showDialogForLoading(MonthApplyModifyActivity.this);
            }
        });

        new NetUtil(uploadParams(),
                NetConfig.address + MonthUrl.modify,
                RequstType.PUT,
                new ResponseListener() {
                    @Override
                    public void onSuccess(String string) {
                        LoadingFinish(null);
                        Log.e(MonthApplyModifyActivity.class.getName(), "data:" + string);
                        try {
                            Result result = new Result(string);
                            if (result.isSuccess()) {
                                LoadingFinish(getString(R.string.common_save_success));
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
        params.add(new NetParams("param", new Gson().toJson(monthModel)));
        return params;
    }

    private void setDialog(OptionsPickerView pickview) {
        getDialogLayoutParams();
        pickview.getDialogContainerLayout().setLayoutParams(getDialogLayoutParams());
        initDialogWindow(pickview.getDialog().getWindow());
    }

    @Override
    public void onBackPressed() {
        if (areaSelect != null && areaSelect.isShowing()) {
            areaSelect.dismiss();
        } else
            super.onBackPressed();
    }
}
