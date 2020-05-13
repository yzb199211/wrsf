package com.yyy.wrsf.mine.shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.common.address.AreaSelect;
import com.yyy.wrsf.common.address.OnBackAreaListener;
import com.yyy.wrsf.beans.address.AddressB;
import com.yyy.wrsf.beans.address.AreaB;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.PhoneUtils;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingPersonActivity extends BaseActivity {
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
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_person);
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
        title = code == CodeUtil.AddressSend ? getString(R.string.address_area_send) : getString(R.string.address_area);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("data"))) {
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
        topView.setTitle(title);
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
                if (canSave()) {
                    setResult(code, new Intent().putExtra("data", new Gson().toJson(addressModel)).putExtra("pos", pos));
                    finish();
                }
                break;
        }
    }


    private void getAddress() {
        if (addressModel == null) {
            addressModel = new AddressB();
        }
        addressModel.setCompanyName(TextUtils.isEmpty(ecvCompany.getText())?ecvContract.getText():ecvCompany.getText());
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
        if (TextUtils.isEmpty(ecvCompany.getText())&&TextUtils.isEmpty(ecvContract.getText())) {
            Toast(ecvContract.getHint());
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
        getAddress();
        return true;
    }

    private void selectArea(View view) {
        if (areaSelect == null && province == null)
            areaSelect = new AreaSelect(ShippingPersonActivity.this);
        else if (areaSelect == null) {
            areaSelect = new AreaSelect(this, province, city, district);
        }
        areaSelect.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        areaSelect.setOnBackAreaListener(new OnBackAreaListener() {
            @Override
            public void backArea(AreaB province, AreaB city, AreaB district) {
                ShippingPersonActivity.this.province = province;
                ShippingPersonActivity.this.city = city;
                ShippingPersonActivity.this.district = district;
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



    @Override
    public void onBackPressed() {
        if (areaSelect != null && areaSelect.isShowing()) {
            areaSelect.dismiss();
        } else
            super.onBackPressed();
    }
}
