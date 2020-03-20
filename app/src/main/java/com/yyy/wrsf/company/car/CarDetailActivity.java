package com.yyy.wrsf.company.car;

import android.graphics.Color;
import android.os.Bundle;
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
import com.yyy.wrsf.model.PublicArray;
import com.yyy.wrsf.model.PublicModel;
import com.yyy.wrsf.model.filter.DriverFilterModel;
import com.yyy.wrsf.model.filter.PublicFilterModel;
import com.yyy.wrsf.utils.PublicCode;
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
    private List<PublicModel> status = new ArrayList<>();
    private List<PublicModel> carTypes = new ArrayList<>();

    private PublicFilterModel publicFilter;
    private DriverFilterModel driverFilter;
    private CarModel car;
    private CarAdapter carAdapter;

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
    }


    private void initView() {
        initTop();
        initDriver();
        initcarTypes();
        initStatus();
    }

    private void initDriverFilter() {
        driverFilter = new DriverFilterModel();
        driverFilter.setDriverStatus(1);
    }

    private void initPublicFilter() {
        publicFilter = new PublicFilterModel();
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
        new NetUtil(carParams(), NetConfig.address + DriverUrl.getList, RequstType.POST, new ResponseListener() {
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

    private List<NetParams> carParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(driverFilter)));
        return params;
    }

    private void setCar() {


    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(publicFilter)));
        return params;
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
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
