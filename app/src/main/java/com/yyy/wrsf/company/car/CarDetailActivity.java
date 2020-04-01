package com.yyy.wrsf.company.car;

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
import com.yyy.wrsf.model.CarModel;
import com.yyy.wrsf.model.DriverModel;
import com.yyy.wrsf.model.publicm.PublicArray;
import com.yyy.wrsf.model.publicm.PublicBean;
import com.yyy.wrsf.model.filter.DriverFilterB;
import com.yyy.wrsf.model.filter.PublicFilterB;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.PublicCode;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.car.CarUrl;
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

public class CarDetailActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_name)
    EditClearView ecvName;
    @BindView(R.id.ecv_license_plate)
    EditClearView ecvLicensePlate;
    @BindView(R.id.ecv_license_driving)
    EditClearView ecvLicenseDriving;
    @BindView(R.id.ecv_type)
    EditClearView ecvType;
    @BindView(R.id.ecv_company)
    EditClearView ecvCompany;
    @BindView(R.id.ecv_driver)
    EditClearView ecvDriver;
    @BindView(R.id.ecv_status)
    EditClearView ecvStatus;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private OptionsPickerView pvDriver;
    private OptionsPickerView pvStatus;
    private OptionsPickerView pvCartype;

    private List<DriverModel> drivers = new ArrayList<>();
    private List<PublicBean> status = new ArrayList<>();
    private List<PublicBean> carTypes = new ArrayList<>();

    private PublicFilterB publicFilter;
    private DriverFilterB driverFilter;

    private CarModel car;

    private int code;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        init();
        getData();
    }


    private void init() {
        initPublicFilter();
        initDriverFilter();
        initView();
        initData();
    }


    private void initView() {
        initTop();
        initDriver();
        initcarTypes();
        initStatus();
    }

    private void initData() {
        code = getIntent().getIntExtra("code", 0);
        pos = getIntent().getIntExtra("pos", -1);
        initCar();
    }

    private void initCar() {
        if (code == CodeUtil.MODIFY) {
            car = new Gson().fromJson(getIntent().getStringExtra("data"), CarModel.class);
        } else {
            car = new CarModel();
        }
    }

    private void initDriverFilter() {
        driverFilter = new DriverFilterB();
        driverFilter.setDriverStatus(1);
    }

    private void initPublicFilter() {
        publicFilter = new PublicFilterB();
        List<Integer> list = new ArrayList<>();
        list.add(PublicCode.CarType);
        list.add(PublicCode.CarStatus);
        publicFilter.setPublicCodes(list);
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    private void initDriver() {
        ecvDriver.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (drivers.size() == 0)
                    getDriverData();
                else {
                    pvDriver.show();
                }
            }
        });
    }

    private void initcarTypes() {
        ecvType.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (pvCartype == null) {
                    initPvCartype();
                } else {
                    pvCartype.show();
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

    private void initPvCartype() {
        pvCartype = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ecvType.setText(carTypes.get(options1).getPickerViewText());
                car.setCarType(carTypes.get(options1).getRecNo());
                car.setCarTypeName(carTypes.get(options1).getPickerViewText());
            }
        }).setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setTitleText(ecvType.getTitle())
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .build();
        pvCartype.setPicker(carTypes);//一级选择器
        DialogUtil.setDialog(pvCartype);
        pvCartype.show();
    }

    private void initPvStatus() {

        pvStatus = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ecvStatus.setText(status.get(options1).getPickerViewText());
                car.setCarStatus(status.get(options1).getRecNo());
                car.setCarStatusName(status.get(options1).getPickerViewText());
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
                            if (array.equals(PublicCode.CarType) && array.getPlantPublicDetails() != null) {
                                carTypes.addAll(array.getPlantPublicDetails());
                            } else if (array.equals(PublicCode.CarStatus) && array.getPlantPublicDetails() != null) {
                                status.addAll(array.getPlantPublicDetails());
                            }
                        }
                        LoadingFinish(null);

                    } else {
                        LoadingFinish(result.getMsg());
                    }
                    setCar();
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

    private void getDriverData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(driverParams(), NetConfig.address + DriverUrl.getList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<DriverModel> list = new Gson().fromJson(result.getData(), new TypeToken<List<DriverModel>>() {
                        }.getType());
                        if (list != null && list.size() > 0) {
                            drivers.addAll(list);
                            initPvDriver();
                        }
                        LoadingFinish(null);
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
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });

    }

    private void initPvDriver() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pvDriver = new OptionsPickerBuilder(CarDetailActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        ecvDriver.setText(drivers.get(options1).getPickerViewText());
                        car.setDriverRecNo(drivers.get(options1).getRecNo());
                        car.setDriverName(drivers.get(options1).getDriverName());
//                        car.setCarTypeName(drivers.get(options1).getPickerViewText());
                    }
                }).setContentTextSize(18)//设置滚轮文字大小
                        .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                        .setSelectOptions(0)//默认选中项
                        .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .setLabels("", "", "")
                        .isDialog(true)
                        .setTitleText(ecvType.getTitle())
                        .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                        .build();
                pvDriver.setPicker(drivers);//一级选择器
                DialogUtil.setDialog(pvDriver);
                pvDriver.show();
            }
        });
    }

    private List<NetParams> driverParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(driverFilter)));
        return params;
    }

    private void setCar() {
        setDefaultData();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ecvName.setText(car.getCarName());
                ecvLicensePlate.setText(car.getCarCode());
                ecvLicenseDriving.setText(car.getDriverCer());
                ecvType.setText(car.getCarTypeName());
                ecvDriver.setText(car.getDriverName());
                ecvStatus.setText(car.getCarStatusName());
            }
        });

    }

    private void setDefaultData() {
        if (car.getCarStatus() == 0 && status.size() > 0) {
            for (PublicBean item : status) {
                if (item.getDetailCode() == 1) {
                    car.setCarStatus(item.getRecNo());
                    car.setCarStatusName(item.getDetailName());
                    break;
                }
            }
        }
        if (car.getCarType() == 0 && carTypes.size() > 0) {
            car.setCarType(carTypes.get(0).getRecNo());
            car.setCarTypeName(carTypes.get(0).getDetailName());
        }
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(publicFilter)));
        return params;
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
        if (TextUtils.isEmpty(ecvLicensePlate.getText())) {
            Toast(ecvLicensePlate.getHint());
            return false;
        }
        if (TextUtils.isEmpty(ecvLicenseDriving.getText())) {
            Toast(ecvLicenseDriving.getHint());
            return false;
        }
        if (TextUtils.isEmpty(ecvDriver.getText())) {
            Toast(ecvDriver.getHint());
            return false;
        }
        car.setCarName(ecvName.getText());
        car.setCarCode(ecvLicensePlate.getText());
        car.setDriverCer(ecvLicenseDriving.getText());

        return true;
    }

    private void saveData() {
        new NetUtil(sendParams(), NetConfig.address + (code == CodeUtil.MODIFY ? CarUrl.update : CarUrl.insert), code == CodeUtil.MODIFY ? RequstType.PUT : RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(CarDetailActivity.class.getName(), "data:" + string);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        setResult(code, new Intent().putExtra("data", new Gson().toJson(car)).putExtra("pos", pos));
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
                Log.e("error", e.getMessage());
            }
        });
    }

    private List<NetParams> sendParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(car)));
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
