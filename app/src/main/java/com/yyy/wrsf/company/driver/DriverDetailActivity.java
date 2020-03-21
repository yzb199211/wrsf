package com.yyy.wrsf.company.driver;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.DialogUtil;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.DriverModel;
import com.yyy.wrsf.model.PublicArray;
import com.yyy.wrsf.model.PublicModel;
import com.yyy.wrsf.model.Sex;
import com.yyy.wrsf.model.filter.PublicFilterModel;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.PhoneUtils;
import com.yyy.wrsf.utils.PublicCode;
import com.yyy.wrsf.utils.SexUtil;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.utils.net.Result;
import com.yyy.wrsf.utils.net.driver.DriverUrl;
import com.yyy.wrsf.utils.net.publics.PublicUrl;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.OptionsPickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnOptionsSelectListener;
import com.yyy.yyylibrary.pick.view.OptionsPickerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverDetailActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_name)
    EditClearView ecvName;
    @BindView(R.id.ecv_tel)
    EditClearView ecvTel;
    @BindView(R.id.ecv_license)
    EditClearView ecvLicense;
    @BindView(R.id.ecv_sex)
    EditClearView ecvSex;
    @BindView(R.id.ecv_status)
    EditClearView ecvStatus;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private OptionsPickerView pvSex;
    private OptionsPickerView pvStatus;
    private OptionsPickerView pvLicense;
    private List<Sex> sexes;
    private List<PublicModel> status;
    private List<PublicModel> licenses;
    private PublicFilterModel publicFilter;
    private DriverModel driver;

    private int code;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail);
        ButterKnife.bind(this);
        init();
        getData();
    }

    private void init() {
        initList();
        initPublicFilter();
        initView();
        initData();
    }

    private void initPublicFilter() {
        publicFilter = new PublicFilterModel();
        List<Integer> list = new ArrayList<>();
        list.add(PublicCode.LicenseDriver);
        list.add(PublicCode.StatusDriver);
        publicFilter.setPublicCodes(list);
    }

    private void initList() {
        sexes = SexUtil.getSexs();
        status = new ArrayList<>();
        licenses = new ArrayList<>();
    }

    private void initView() {
        initTop();
        initSex();
        initLicense();
        initStatus();
    }

    private void initData() {
        code = getIntent().getIntExtra("code", 0);
        pos = getIntent().getIntExtra("pos", -1);
        initDriver();
    }

    private void initDriver() {
        if (code == CodeUtil.MODIFY) {
            driver = new Gson().fromJson(getIntent().getStringExtra("data"), DriverModel.class);

        } else {
            driver = new DriverModel();
        }
    }

    private void setDriver() {
        setDefaultData();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ecvName.setText(TextUtils.isEmpty(driver.getDriverName()) ? "" : driver.getDriverName());
                ecvLicense.setText(TextUtils.isEmpty(driver.getDriverTypeName()) ? "" : driver.getDriverTypeName());
                ecvSex.setText(driver.getSex() == 1 ? "男" : "女");
                ecvTel.setText(TextUtils.isEmpty(driver.getDriverTel()) ? "" : driver.getDriverTel());
                ecvStatus.setText(getStatusName());
            }
        });
    }

    private void setDefaultData() {
        if (TextUtils.isEmpty(driver.getDriverTypeName()) && licenses.size() > 0) {
            driver.setDriverType(licenses.get(0).getRecNo());
            driver.setDriverTypeName(licenses.get(0).getDetailName());
        }
        if (driver.getDriverStatus() == null && status.size() > 0) {
            for (PublicModel item : status) {
                if (item.getDetailCode() == 1) {
                    driver.setDriverStatus(item.getRecNo());
                    break;
                }
            }
        }
    }

    private String getStatusName() {
        for (PublicModel item : status) {
            if (item.getRecNo() == driver.getDriverStatus()) ;
            return item.getDetailName();
        }
        return "";
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }


    private void initSex() {
        ecvSex.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (pvSex == null) {
                    initPvSex();
                } else {
                    pvSex.show();
                }
            }
        });
    }


    private void initLicense() {
        ecvLicense.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (pvLicense == null) {
                    initPvLicense();
                } else {
                    pvLicense.show();
                }
            }
        });

    }

    private void initStatus() {
        ecvStatus.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (pvStatus == null) {
                    initPvStatus();
                } else {
                    pvStatus.show();
                }
            }
        });
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.address + PublicUrl.getPublic, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<PublicArray> list = new Gson().fromJson(result.getData(), new TypeToken<List<PublicArray>>() {
                        }.getType());
                        for (PublicArray array : list) {
                            if (array.equals(PublicCode.LicenseDriver) && array.getPlantPublicDetails() != null) {
                                licenses.addAll(array.getPlantPublicDetails());
                            } else if (array.equals(PublicCode.StatusDriver) && array.getPlantPublicDetails() != null) {
                                status.addAll(array.getPlantPublicDetails());
                            }
                        }
                        LoadingFinish(null);

                    } else {
                        LoadingFinish(result.getMsg());
                    }
                    setDriver();
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoadingFinish(e.getMessage());
                }
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();

            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(publicFilter)));
        return params;
    }

    private void initPvSex() {
        pvSex = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ecvSex.setText(sexes.get(options1).getPickerViewText());
                driver.setSex(sexes.get(options1).getId());
            }
        }).setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setTitleText(ecvSex.getTitle())
                .isDialog(true)
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .build();
        pvSex.setPicker(sexes);//一级选择器
        DialogUtil.setDialog(pvSex);
        pvSex.show();
    }

    private void initPvStatus() {
        Log.e("status", new Gson().toJson(status));
        pvStatus = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ecvStatus.setText(status.get(options1).getPickerViewText());
                driver.setDriverStatus(status.get(options1).getRecNo());
            }
        }).setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setTitleText(ecvStatus.getTitle())
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .build();
        pvStatus.setPicker(status);//一级选择器
        DialogUtil.setDialog(pvStatus);
        pvStatus.show();
    }

    private void initPvLicense() {
        pvLicense = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ecvLicense.setText(licenses.get(options1).getPickerViewText());
                driver.setDriverType(licenses.get(options1).getRecNo());
                driver.setDriverTypeName(licenses.get(options1).getPickerViewText());
            }
        }).setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setTitleText(ecvLicense.getTitle())
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .build();
        pvLicense.setPicker(licenses);//一级选择器
        DialogUtil.setDialog(pvLicense);
        pvLicense.show();
    }


    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        if (canSave())
            saveData();
    }

    private boolean canSave() {
        if (TextUtils.isEmpty(ecvName.getText())) {
            Toast(ecvName.getHint());
            return false;
        }

        if (TextUtils.isEmpty(ecvTel.getText())) {
            Toast(ecvTel.getHint());
            return false;
        }
        if (PhoneUtils.isNotValidChinesePhone(ecvTel.getText())) {
            Toast(getString(R.string.error_phone));
            return false;
        }
        if (TextUtils.isEmpty(ecvLicense.getText())) {
            Toast(ecvLicense.getHint());
            return false;
        }
        driver.setDriverName(ecvName.getText());
        driver.setDriverTel(ecvTel.getText());
        return true;
    }

    private void saveData() {
        new NetUtil(sendParams(), NetConfig.address + (code == CodeUtil.MODIFY ? DriverUrl.update : DriverUrl.insert), code == CodeUtil.MODIFY ? RequstType.PUT : RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(DriverDetailActivity.class.getName(), "data:" + string);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        setResult(code, new Intent().putExtra("data", new Gson().toJson(driver)).putExtra("pos", pos));
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
                Log.e("error", e.getMessage());
            }
        });
    }

    private List<NetParams> sendParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(driver)));
        return params;
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
