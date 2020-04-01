package com.yyy.wrsf.mine.address;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.common.address.AreaSelect;
import com.yyy.wrsf.common.address.OnBackAreaListener;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.beans.address.AddressB;
import com.yyy.wrsf.beans.address.AreaB;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.PhoneUtils;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.address.AddressUrl;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressDetailActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_company)
    EditClearView ecvCompany;
    @BindView(R.id.ecv_contract)
    EditClearView ecvContract;
    @BindView(R.id.ecv_phone)
    EditClearView ecvPhone;
    @BindView(R.id.ecv_area)
    EditClearView ecvArea;
    @BindView(R.id.ecv_address_detail)
    EditClearView ecvAddressDetail;

    private AreaSelect areaSelect;
    private AreaB province;
    private AreaB city;
    private AreaB district;

    private AddressB addressModel;
    private int memberId;
    private SharedPreferencesHelper preferencesHelper;

    private int code;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
        memberId = (Integer) preferencesHelper.getSharedPreference("recNo", 0);
        intiIntentData();
    }

    private void intiIntentData() {
        code = getIntent().getIntExtra("code", -1);
        pos = getIntent().getIntExtra("pos", -1);
        if (code == CodeUtil.MODIFY) {
            addressModel = new Gson().fromJson(getIntent().getStringExtra("data"), AddressB.class);
            initAreaData();
            setArea();
            setDetail();
        }
    }

    private void initAreaData() {
        if (TextUtils.isEmpty(addressModel.getFirstAdd()))
            return;
        province = new AreaB();
        province.setId(addressModel.getFirstId());
        province.setAreaName(addressModel.getFirstAdd());
        if (TextUtils.isEmpty(addressModel.getSecondAdd()))
            return;
        city = new AreaB();
        city.setId(addressModel.getSecondId());
        city.setAreaName(addressModel.getSecondAdd());
        if (TextUtils.isEmpty(addressModel.getThirdAdd()))
            return;
        district = new AreaB();
        district.setId(addressModel.getThirdId());
        district.setAreaName(addressModel.getThirdAdd());
    }

    private void setDetail() {
        ecvAddressDetail.setText(addressModel.getDetailAdd());
        ecvCompany.setText(addressModel.getCompanyName());
        ecvPhone.setText(addressModel.getContractTel());
        ecvContract.setText(addressModel.getContractPerson());
    }

    private void intDefaultData() {
    }

    private void initView() {
        initTop();
        initArea();
    }

    private void initArea() {
        ecvArea.getTvText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectArea(v);
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

    @OnClick({R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                saveAddress();
                break;
        }
    }

    private void saveAddress() {
        if (canSave()) {
            LoadingDialog.showDialogForLoading(this);
            new NetUtil(getAddressParams(),
                    NetConfig.address + (addressModel.getRecNo() == 0 ? AddressUrl.AddAddress : AddressUrl.updateAddress),
                    addressModel.getRecNo() == 0 ? RequstType.POST : RequstType.PUT,
                    new ResponseListener() {
                        @Override
                        public void onSuccess(String string) {
                            LoadingFinish(null);
                            Log.e(AddressDetailActivity.class.getName(), "data:" + string);
                            try {
                                Result result = new Result(string);
                                if (result.isSuccess()) {
                                    setResult(code, new Intent().putExtra("data", new Gson().toJson(addressModel)).putExtra("pos", pos));
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
    }

    private List<NetParams> getAddressParams() {
        getAddress();
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("platMemberRecaddBo", new Gson().toJson(addressModel)));
        return params;
    }

    private void getAddress() {
        if (addressModel == null) {
            addressModel = new AddressB();
        }
        addressModel.setCompanyName(ecvCompany.getText());
        addressModel.setContractPerson(ecvContract.getText());
        addressModel.setContractTel(ecvPhone.getText());
        addressModel.setFirstAdd(province.getAreaName());
        addressModel.setFirstId(province.getId());
        addressModel.setSecondAdd(city.getAreaName());
        addressModel.setSecondId(city.getId());
        addressModel.setThirdAdd(district.getAreaName());
        addressModel.setThirdId(district.getId());
        addressModel.setDetailAdd(ecvAddressDetail.getText());
        addressModel.setPlatMemberRecNo(memberId);
    }

    private boolean canSave() {
        if (TextUtils.isEmpty(ecvCompany.getText())) {
            Toast(ecvCompany.getHint());
            return false;
        }
        if (TextUtils.isEmpty(ecvContract.getText())) {
            Toast(ecvContract.getHint());
            return false;
        }
        if (TextUtils.isEmpty(ecvPhone.getText())) {
            Toast(ecvPhone.getHint());
            return false;
        }
        if (PhoneUtils.isNotValidChinesePhone(ecvPhone.getText())) {
            Toast(getString(R.string.error_phone));
            return false;
        }
        if (TextUtils.isEmpty(ecvArea.getText())) {
            Toast(ecvArea.getHint());
            return false;
        }
        if (TextUtils.isEmpty(ecvAddressDetail.getText())) {
            Toast(ecvAddressDetail.getHint());
            return false;
        }
        return true;
    }

    private void selectArea(View view) {
        if (areaSelect == null && province == null)
            areaSelect = new AreaSelect(AddressDetailActivity.this);
        else if (areaSelect == null) {
            areaSelect = new AreaSelect(this, province, city, district);
        }
        areaSelect.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        areaSelect.setOnBackAreaListener(new OnBackAreaListener() {
            @Override
            public void backArea(AreaB province, AreaB city, AreaB district) {
                AddressDetailActivity.this.province = province;
                AddressDetailActivity.this.city = city;
                AddressDetailActivity.this.district = district;
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

    @Override
    public void onBackPressed() {
        if (areaSelect != null && areaSelect.isShowing()) {
            areaSelect.dismiss();
        } else
            super.onBackPressed();
    }
}
