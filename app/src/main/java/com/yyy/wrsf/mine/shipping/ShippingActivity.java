package com.yyy.wrsf.mine.shipping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BasePickActivity;
import com.yyy.wrsf.beans.address.AddressB;
import com.yyy.wrsf.beans.filter.AddressFilterB;
import com.yyy.wrsf.beans.filter.ShipCompanyFilterB;
import com.yyy.wrsf.beans.price.PriceBackB;
import com.yyy.wrsf.beans.ship.ShipAddValueFeeB;
import com.yyy.wrsf.beans.ship.ShipCompanyB;
import com.yyy.wrsf.beans.ship.ShipGoodsB;
import com.yyy.wrsf.beans.ship.ShippingAddValueB;
import com.yyy.wrsf.beans.ship.ShippingB;
import com.yyy.wrsf.common.company.CompanySelect;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.mine.address.AddressReceiveActivity;
import com.yyy.wrsf.mine.address.AddressSendActivity;
import com.yyy.wrsf.mine.pay.PayActivity;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.DateUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.net.address.AddressUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.order.OrderUrl;
import com.yyy.wrsf.utils.net.ship.ShipUrl;
import com.yyy.wrsf.view.textselect.TextMenuItem;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.TimePickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnTimeSelectListener;
import com.yyy.yyylibrary.pick.view.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingActivity extends BasePickActivity implements CompoundButton.OnCheckedChangeListener {

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
    @BindView(R.id.tmi_base_fee)
    TextMenuItem tmiBaseFee;
    @BindView(R.id.tmi_value_add_fee)
    TextMenuItem tmiValueAddFee;
    @BindView(R.id.cb_protocol)
    CheckBox cbProtocol;

    private RadioButton currentPay;
    private CompanySelect companySelect;
    private TimePickerView pvDate;

    private AddressB addressSend;
    private AddressB addressReceive;
    private ShipGoodsB goods;
    private ShipCompanyB company;
    private ShipCompanyFilterB companyFilter;
    private ShippingB shipping;
    private ShippingAddValueB addValue;
    private ShipAddValueFeeB addValueFeeB;
    private PriceBackB priceBackM;

    private boolean refreshCompany = true;
    private SharedPreferencesHelper preferencesHelper;
    private int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
    }

    private void init() {
        memberId = (Integer) preferencesHelper.getSharedPreference("recNo", 0);
        initModel();
        initView();
        initPagerData();
        getDefaultData();
    }


    private void initModel() {
        companyFilter = new ShipCompanyFilterB();
        initShip();
    }

    private void initShip() {
        shipping = new ShippingB();
        shipping.setPickDate(DateUtil.getCurrentDate());
        shipping.setPayType(1);
    }

    private void initView() {
        initTop();
        initPay();
        initDate();
        initRadio();
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initPay() {
        tvPayMonth.setVisibility(View.GONE);
        currentPay = tvPayNow;
    }

    private void initDate() {
        tmiPickDate.setText(DateUtil.getDateAndHour(new Date(System.currentTimeMillis())));
    }

    private void initRadio() {
        tvPayNow.setOnCheckedChangeListener(this);
        tvPayReceive.setOnCheckedChangeListener(this);
        tvPayMonth.setOnCheckedChangeListener(this);
    }

    /**
     * 获取默认数据
     */
    private void getDefaultData() {
        getSendPerson();
    }

    private void getSendPerson() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.address + AddressUrl.getAddressSendList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
//                    LoadingFinish(null);
                    if (result.isSuccess()) {
                        List<AddressB> list = new Gson().fromJson(result.getData(), new TypeToken<List<AddressB>>() {
                        }.getType());
                        if (list != null && list.size() > 0) {
                            addressSend = list.get(0);
                            if (addressSend.getThirdId() != companyFilter.getSendRegion()) {
                                refreshCompany = true;
                                companyFilter.setSendRegion(addressSend.getThirdId());
                            }
                            runOnUiThread(() -> {
                                clearCompany();
                                setSend();
                            });
                        }
                    } else {
//                        LoadingFinish(result.getMsg());
                        Log.e(AddressSendActivity.class.getName(), result.getMsg());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    LoadingFinish(e.getMessage());
                }finally {
                    getRecPerson();
                }
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
                getRecPerson();
//                LoadingFinish(e.getMessage());
            }
        });
    }

    private void getRecPerson() {

        new NetUtil(getParams(), NetConfig.address + AddressUrl.getAddressList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
                    LoadingFinish(null);
                    if (result.isSuccess()) {
                        List<AddressB> list = new Gson().fromJson(result.getData(), new TypeToken<List<AddressB>>() {
                        }.getType());
                        if (list != null && list.size() > 0) {
                            addressReceive = list.get(0);
                            if (addressReceive.getThirdId() != companyFilter.getRecRegion()) {
                                refreshCompany = true;
                                companyFilter.setRecRegion(addressReceive.getThirdId());
                            }
                            runOnUiThread(() -> {
                                clearCompany();
                                setReceive();
                            });
                        }
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(AddressSendActivity.class.getName(), result.getMsg());
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

    private List<NetParams> getParams() {

        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("platMemberRecaddBo", new Gson().toJson(pager)));
        return params;
    }

    private PagerRequestBean<AddressFilterB> pager;

    private void initPagerData() {
        pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(1);
        AddressFilterB filterModel = new AddressFilterB();
        filterModel.setRecNo(memberId);
        filterModel.setIsDefault(1);
        pager.setQueryParam(filterModel);
//        String s = new Gson().toJson(filterModel);
    }

    @OnClick({R.id.tv_address_send, R.id.ll_send, R.id.tv_address_receive, R.id.ll_receive, R.id.tmi_company, R.id.tmi_goods, R.id.tmi_value_add,
            R.id.tmi_pick_date, R.id.tmi_remark, R.id.tv_total, R.id.ll_protocol, R.id.tv_submit, R.id.tv_pay_now, R.id.tv_pay_receive, R.id.tv_pay_month})
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
                    Toast(getString(R.string.send_empty_address));
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
                go2Remark();
                break;
            case R.id.tv_total:
                break;
            case R.id.ll_protocol:
                if (!cbProtocol.isChecked()) go2Protocol();
                else cbProtocol.setChecked(false);
                break;
            case R.id.tv_submit:
                if (canSave()) {
                    Log.e("data", new Gson().toJson(shipping));
                    save();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton view, boolean b) {
        if (b) {
            switch (view.getId()) {
                case R.id.tv_pay_now:
                    switchPay(view, 1);
                    break;
                case R.id.tv_pay_receive:
                    switchPay(view, 3);
                    break;
                case R.id.tv_pay_month:
                    switchPay(view, 2);
                    break;
            }
        }
    }

    private void selectCompany(View view) {
        if (companySelect == null) {
            companySelect = new CompanySelect(this);
            companySelect.setOnLoadingListener((boolean success) -> {
                refreshCompany = false;
            });
            companySelect.setOnCompanySelectListener((ShipCompanyB item) -> {
                company = item;
                tmiCompany.setText(company.getTransCompanyName());
                shipping.setTransCompanyRecNo(company.getTransCompanyRecno());
                shipping.setTransRecShopRecNo(company.getRecRegion().getRecNo());
                shipping.setTransShopRecNo(company.getSendRegion().getRecNo());
                clearGoods();
                initAddValue();

            });
        }
        companySelect.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        setEventBus();

    }

    private void initAddValue() {
        if (company.getCompanyFee() == null) {
            tmiValueAdd.setVisibility(View.GONE);
            tmiValueAddFee.setVisibility(View.GONE);
        } else {
            addValueFeeB = company.getCompanyFee();
            tmiValueAddFee.setVisibility(View.VISIBLE);
            tmiValueAdd.setVisibility(View.VISIBLE);
        }
    }

    private void setEventBus() {
        try {
            if (!EventBus.getDefault().isRegistered(companySelect))
                EventBus.getDefault().register(companySelect);
            if (refreshCompany)
                EventBus.getDefault().post(companyFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void go2AddressDetail(AddressB address, int code) {
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
            shipping.setPayType(i);
        } else {
            currentPay.setChecked(true);
        }
    }

    private void go2addressSend() {
        startActivityForResult(new Intent().setClass(this, AddressSendActivity.class).putExtra("isSelect", true), CodeUtil.AddressSend);
    }

    private void go2addressReceive() {
        startActivityForResult(new Intent().setClass(this, AddressReceiveActivity.class).putExtra("isSelect", true), CodeUtil.AddressReceive);
    }

    private void go2Goods() {
        startActivityForResult(
                new Intent()
                        .setClass(this, ShippingGoodsActivity.class)
                        .putExtra("data", goods == null ? "" : new Gson().toJson(goods))
                        .putExtra("sendRec", addressSend.getThirdId())
                        .putExtra("receiveRec", addressReceive.getThirdId())
                        .putExtra("company", company.getTransCompanyRecno())
                        .putExtra("sendShop", company.getSendRegion().getRecNo())
                        .putExtra("recShop", company.getRecRegion().getRecNo())
                        .putExtra("type", company.getType())
                , CodeUtil.ShipGoods);
    }

    private void go2ValueAdd() {
        startActivityForResult(
                new Intent()
                        .setClass(this, ShippingValueAddActivity.class)
                        .putExtra("data", addValue == null ? "" : new Gson().toJson(addValue))
                        .putExtra("fee", new Gson().toJson(addValueFeeB))
                , CodeUtil.ShipAddValue);
    }

    private void go2Remark() {
        startActivityForResult(
                new Intent()
                        .setClass(this, ShippingRemarkActivity.class)
                        .putExtra("data", tmiRemark.getText())
                , CodeUtil.ADD);
    }

    private void initPvDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarLast = Calendar.getInstance();
        calendarLast.add(Calendar.DATE, 2);
        pvDate = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tmiPickDate.setText(DateUtil.getDateAndHour(date));
                shipping.setPickDate(DateUtil.getTime(date));
            }
        }).setRangDate(calendar, calendarLast)
                .setDate(calendar)
                .setType(new boolean[]{true, true, true, true, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setContentTextSize(18)
                .setBgColor(0xFFFFFFFF)
                .build();
        setDialog(pvDate);
        initDialogWindow(pvDate.getDialog().getWindow());
        pvDate.show();
    }

    private void go2Protocol() {
        startActivityForResult(new Intent().setClass(this, ShipProtocolActivity.class), CodeUtil.CONFIRM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            setData(requestCode, data);
        }
    }

    private void setData(int requestCode, Intent data) {
        switch (requestCode) {
            case CodeUtil.AddressSend:
                addressSend = new Gson().fromJson(data.getStringExtra("data"), AddressB.class);
                if (addressSend.getThirdId() != companyFilter.getSendRegion()) {
                    refreshCompany = true;
                    companyFilter.setSendRegion(addressSend.getThirdId());
                }
                clearCompany();
                setSend();
                break;
            case CodeUtil.AddressReceive:
                addressReceive = new Gson().fromJson(data.getStringExtra("data"), AddressB.class);
                if (addressReceive.getThirdId() != companyFilter.getRecRegion()) {
                    refreshCompany = true;
                    companyFilter.setRecRegion(addressReceive.getThirdId());
                }
                clearCompany();
                setReceive();
                break;
            case CodeUtil.ShipGoods:
                goods = new Gson().fromJson(data.getStringExtra("data"), ShipGoodsB.class);
                priceBackM = new Gson().fromJson(data.getStringExtra("price"), PriceBackB.class);
                Log.e("price", data.getStringExtra("price"));
                shipping.setGoodsPrice(priceBackM);
                setGoods();
                setTotal();
                setMonth();
                break;
            case CodeUtil.ShipAddValue:
                addValue = new Gson().fromJson(data.getStringExtra("data"), ShippingAddValueB.class);
                setAddValue();
                setTotal();
                break;
            case CodeUtil.ADD:
                tmiRemark.setText(data.getStringExtra("data"));
//                shipping.set
                break;
            case CodeUtil.CONFIRM:
                cbProtocol.setChecked(true);
                break;
            default:
                break;
        }
    }

    private void setMonth() {
        if (priceBackM != null && priceBackM.getTransCustomerMonthRecNo() != 0) {
            tvPayMonth.setVisibility(View.VISIBLE);
        } else {
            tvPayMonth.setVisibility(View.GONE);
            if (currentPay == tvPayMonth) {
                switchPay(tvPayNow, 1);
            }
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
        String sTotal = getString(R.string.send_total) + getString(R.string.common_rmb) + StringUtil.formatDouble(total);
        tvTotal.setText(StringUtil.getSpanStr(sTotal, 5, getColor(R.color.order_yellow)));
    }

    private void setGoods() {
        shipping.setGoods(goods);
        tmiGoods.setText(goods.getData());
        tmiBaseFee.setText(getString(R.string.common_rmb) + StringUtil.formatDouble(priceBackM.getContractTotal()));
    }


    private void setAddValue() {
        shipping.setValueAdd(addValue);
        tmiValueAdd.setText(addValue.getData());
        tmiValueAddFee.setText(getString(R.string.common_rmb) + StringUtil.formatDouble(addValue.getTotal()));
    }

    private void clearCompany() {
        clearGoods();
        company = null;
        shipping.setTransShopRecNo(0);
        shipping.setTransRecShopRecNo(0);
        shipping.setTransCompanyRecNo(0);
        tmiCompany.setText("");
    }

    private void clearGoods() {
        goods = null;
        priceBackM = null;
        shipping.clear();
        tmiGoods.setText("");
        tmiBaseFee.setText("");
        String sTotal = getString(R.string.send_total) + getString(R.string.common_rmb) + 0;
        tvTotal.setText(StringUtil.getSpanStr(sTotal, 5, getColor(R.color.order_yellow)));
        clearAddValue();
        setMonth();
    }

    private void clearAddValue() {
        addValue = null;
        addValueFeeB = null;
        shipping.clearValueAdd();
        tmiValueAdd.setText("");
        tmiValueAddFee.setText("");
    }


    private void setSend() {
        shipping.setSend(addressSend);
        tvNameSend.setText(addressSend.getContractPerson());
        tvTelSend.setText(addressSend.getContractTel());
        tvAddressDetailSend.setText(addressSend.getFirstAdd() + addressSend.getSecondAdd() + addressSend.getThirdAdd() + addressSend.getDetailAdd());
    }

    private void setReceive() {
        shipping.setReceive(addressReceive);
        tvNameReceive.setText(addressReceive.getContractPerson());
        tvTelReceive.setText(addressReceive.getContractTel());
        tvAddressDetailReceive.setText(addressReceive.getFirstAdd() + addressReceive.getSecondAdd() + addressReceive.getThirdAdd() + addressReceive.getDetailAdd());
    }


    private boolean canSave() {
        if (goods == null || goods.isEmpty()) {
            Toast(getString(R.string.send_goods_empty));
            return false;
        }
        if (!cbProtocol.isChecked()) {
            Toast(getString(R.string.send_protocol_check));
            return false;
        }
        return true;
    }

    private void save() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(saveParams(), NetConfig.address + OrderUrl.insertContractInfo, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                LoadingFinish(null);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        LoadingFinish(getString(R.string.common_order_success));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (shipping.getPayType() == 1) go2Pay(result.getData());
                                finish();
                            }
                        });
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(ShippingActivity.this.getClass().getName(), result.getMsg());
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

    private void go2Pay(String order) {
        startActivity(
                new Intent()
                        .setClass(this, PayActivity.class)
                        .putExtra("data", order)
        );
    }

    private List<NetParams> saveParams() {
        List<NetParams> params = new ArrayList<>();
        if (shipping.getQianType() == 0) {
            shipping.setQianType(1);
        }
        params.add(new NetParams("param", new Gson().toJson(shipping)));
        return params;
    }

    @Override
    public void onBackPressed() {
        if (companySelect != null && companySelect.isShowing()) {
            companySelect.dismiss();
        } else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
