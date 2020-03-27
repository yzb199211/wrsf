package com.yyy.wrsf.mine.shipping;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BasePickActivity;
import com.yyy.wrsf.common.company.CompanySelect;
import com.yyy.wrsf.mine.address.AddressActivity;
import com.yyy.wrsf.mine.address.AddressSendActivity;
import com.yyy.wrsf.model.address.AddressModel;
import com.yyy.wrsf.model.company.CompanyModel;
import com.yyy.wrsf.model.price.PriceBackM;
import com.yyy.wrsf.model.ship.ShipGoodsModel;
import com.yyy.wrsf.model.ship.ShippingAddValueModel;
import com.yyy.wrsf.model.filter.ShipCompany;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.DateUtil;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.TimeUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.view.textselect.TextMenuItem;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.TimePickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnTimeSelectListener;
import com.yyy.yyylibrary.pick.view.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingActivity extends BasePickActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tv_name_send)
    TextView tvNameSend;
    @BindView(R.id.tv_tel_send)
    TextView tvTelSend;
    @BindView(R.id.tv_address_detail_send)
    TextView tvAddressDetailSend;
    @BindView(R.id.tv_address_send)
    TextView tvAddressSend;
    @BindView(R.id.tv_name_receive)
    TextView tvNameReceive;
    @BindView(R.id.tv_tel_receive)
    TextView tvTelReceive;
    @BindView(R.id.tv_address_detail_receive)
    TextView tvAddressDetailReceive;
    @BindView(R.id.tv_address_receive)
    TextView tvAddressReceive;
    @BindView(R.id.tmi_company)
    TextMenuItem tmiCompany;
    @BindView(R.id.tmi_goods)
    TextMenuItem tmiGoods;
    @BindView(R.id.tmi_value_add)
    TextMenuItem tmiValueAdd;
    @BindView(R.id.tmi_pick_date)
    TextMenuItem tmiPickDate;
    @BindView(R.id.tmi_remark)
    TextMenuItem tmiRemark;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    @BindView(R.id.tv_pay_now)
    RadioButton tvPayNow;
    @BindView(R.id.tv_pay_receive)
    RadioButton tvPayReceive;
    @BindView(R.id.tv_pay_month)
    RadioButton tvPayMonth;

    private RadioButton currentPay;
    private CompanySelect companySelect;
    private TimePickerView pvDate;

    private AddressModel addressSend;
    private AddressModel addressReceive;
    private ShipGoodsModel goods;
    private CompanyModel company;
    private ShipCompany companyFilter;
    private ShippingAddValueModel addValue;
    private PriceBackM priceBackM;

    private int payType;
    private boolean refreshCompany = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initView();
        initModel();
    }

    private void initModel() {
        companyFilter = new ShipCompany();
    }

    private void initView() {
        initTop();
        initPay();
        initDate();
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initPay() {
        tvPayMonth.setVisibility(View.GONE);
        currentPay = tvPayNow;
        payType = 1;
    }

    private void initDate() {
        tmiPickDate.setText(DateUtil.getCurrentDate());
    }

    @OnClick({R.id.tv_address_send, R.id.ll_send, R.id.tv_address_receive, R.id.ll_receive, R.id.tmi_company, R.id.tmi_goods, R.id.tmi_value_add,
            R.id.tmi_pick_date, R.id.tmi_remark, R.id.tv_total, R.id.tv_protocol, R.id.tv_submit, R.id.tv_pay_now, R.id.tv_pay_receive, R.id.tv_pay_month})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address_send:
                go2addressSend();
                break;
            case R.id.ll_send:
                go2AddressDetail(addressSend, CodeUtil.AddressSend);
                break;
            case R.id.tv_address_receive:
                go2addressReceive();
                break;
            case R.id.ll_receive:
                go2AddressDetail(addressReceive, CodeUtil.AddressReceive);
                break;
            case R.id.tmi_company:
                if (addressSend == null || addressReceive == null) {
                    Toast(getString(R.string.send_empty_company));
                    return;
                }
                selectCompany(view);
                break;
            case R.id.tmi_goods:
                if (company == null) {
                    Toast(getString(R.string.send_empty_company));
                    return;
                }
                go2Goods();
                break;
            case R.id.tmi_value_add:
                go2ValueAdd();
                break;
            case R.id.tmi_pick_date:
                try {
                    if (pvDate == null)
                        initPvDate();
                    else {
                        pvDate.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tmi_remark:
                break;
            case R.id.tv_total:
                break;
            case R.id.tv_protocol:
                break;
            case R.id.tv_submit:
                break;
            case R.id.tv_pay_now:
                switchPay(view, 1);
                break;
            case R.id.tv_pay_receive:
                switchPay(view, 3);
                break;
            case R.id.tv_pay_month:
                switchPay(view, 2);
                break;
            default:
                break;
        }
    }

    private void selectCompany(View view) {
        if (companySelect == null) {
            companySelect = new CompanySelect(this);
            companySelect.setOnLoadingListener((boolean success) -> {
                refreshCompany = false;
            });
            companySelect.setOnCompanySelectListener((CompanyModel item) -> {
                company = item;
                tmiCompany.setText(company.getCompanyName());
            });
        }
        companySelect.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        EventBus.getDefault().register(companySelect);
        if (refreshCompany)
            EventBus.getDefault().post(companyFilter);
    }


    private void go2AddressDetail(AddressModel address, int code) {
        Intent intent = new Intent();
        intent.setClass(this, ShippingPersonActivity.class);
        intent.putExtra("code", code);
        if (address != null) {
            intent.putExtra("data", new Gson().toJson(address));
        }
        startActivityForResult(intent, code);
    }

    private void switchPay(View view, int i) {
        if (view.getId() != currentPay.getId()) {
            currentPay.setChecked(false);
            currentPay.setTextColor(getColor(R.color.text_gray));
            currentPay = (RadioButton) view;
            currentPay.setTextColor(getColor(R.color.white));
            currentPay.setChecked(true);
            payType = i;
        } else {
            currentPay.setChecked(true);
        }
    }

    private void go2addressSend() {
        startActivityForResult(new Intent().setClass(this, AddressSendActivity.class).putExtra("isSelect", true), CodeUtil.AddressSend);
    }

    private void go2addressReceive() {
        startActivityForResult(new Intent().setClass(this, AddressActivity.class).putExtra("isSelect", true), CodeUtil.AddressReceive);
    }

    private void go2Goods() {
        startActivityForResult(
                new Intent()
                        .setClass(this, ShippingGoodsActivity.class)
                        .putExtra("data", goods == null ? "" : new Gson().toJson(goods))
                        .putExtra("sendRec", addressSend.getThirdId())
                        .putExtra("receiveRec", addressReceive.getThirdId())
                        .putExtra("company", company.getRecNo())
                , CodeUtil.ShipGoods);
    }

    private void go2ValueAdd() {
        startActivityForResult(
                new Intent()
                        .setClass(this, ShippingValueAddActivity.class)
                        .putExtra("data", addValue == null ? "" : new Gson().toJson(addValue))
                , CodeUtil.ShipAddValue);
    }

    private void initPvDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarLast = Calendar.getInstance();
        calendarLast.add(Calendar.DATE, 2);
        pvDate = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tmiPickDate.setText(DateUtil.getDate(date));
            }
        }).setRangDate(calendar, calendarLast)
                .setDate(calendar)
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setContentTextSize(18)
                .setBgColor(0xFFFFFFFF)
                .build();
        setDialog(pvDate);
        initDialogWindow(pvDate.getDialog().getWindow());
        pvDate.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            setData(requestCode, data);
        }
    }

    private void setData(int requestCode, Intent data) {
        Log.e("data", data.getStringExtra("data"));
        switch (requestCode) {
            case CodeUtil.AddressSend:
                addressSend = new Gson().fromJson(data.getStringExtra("data"), AddressModel.class);
                if (addressSend.getThirdId() != companyFilter.getSendRegion()) {
                    refreshCompany = true;
                    companyFilter.setSendRegion(addressSend.getThirdId());
                }
                setSend();
                break;
            case CodeUtil.AddressReceive:
                addressReceive = new Gson().fromJson(data.getStringExtra("data"), AddressModel.class);
                if (addressReceive.getThirdId() != companyFilter.getRecRegion()) {
                    refreshCompany = true;
                    companyFilter.setRecRegion(addressReceive.getThirdId());
                }
                setReceive();
                break;
            case CodeUtil.ShipGoods:
                goods = new Gson().fromJson(data.getStringExtra("data"), ShipGoodsModel.class);
                priceBackM = new Gson().fromJson(data.getStringExtra("price"), PriceBackM.class);
                Log.e("price", data.getStringExtra("price"));
                setGoods();
                setTotal();
                break;
            case CodeUtil.ShipAddValue:
                addValue = new Gson().fromJson(data.getStringExtra("data"), ShippingAddValueModel.class);
                setTotal();
                break;
            default:
                break;
        }
    }

    private void setTotal() {
        double total = 0;
        if (priceBackM != null) {
            total = ShipUtil.getTotal(total, priceBackM.getContractTotal());
        }
        if (addValue != null) {
            total = ShipUtil.getTotal(total, addValue.getTotal());
        }
        String sTotal = getString(R.string.send_total) + getString(R.string.common_rmb) + total;
        tvTotal.setText(StringUtil.getSpanStr(sTotal, 5, getColor(R.color.order_yellow)));
    }

    private void setGoods() {
        tmiGoods.setText(goods.getData());
    }

    private void setSend() {
        tvNameSend.setText(addressSend.getContractPerson());
        tvTelSend.setText(addressSend.getContractTel());
        tvAddressDetailSend.setText(addressSend.getFirstAdd() + addressSend.getSecondAdd() + addressSend.getThirdAdd() + addressSend.getDetailAdd());
    }

    private void setReceive() {
        tvNameReceive.setText(addressReceive.getContractPerson());
        tvTelReceive.setText(addressReceive.getContractTel());
        tvAddressDetailReceive.setText(addressReceive.getFirstAdd() + addressReceive.getSecondAdd() + addressReceive.getThirdAdd() + addressReceive.getDetailAdd());
    }


    private void Toast(String msg) {
        Toasts.showShort(this, msg);
    }

    @Override
    public void onBackPressed() {
        if (companySelect != null && companySelect.isShowing()) {
            companySelect.dismiss();
        } else
            super.onBackPressed();
    }


}
