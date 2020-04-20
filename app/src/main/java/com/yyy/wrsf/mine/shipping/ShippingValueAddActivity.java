package com.yyy.wrsf.mine.shipping;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.ship.ShipAddValueFeeB;
import com.yyy.wrsf.beans.ship.ShippingAddValueB;
import com.yyy.wrsf.beans.ship.SignB;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.EditInputFilter;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingValueAddActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tv_price_insured)
    TextView tvPriceInsured;
    @BindView(R.id.tv_price_collection)
    TextView tvPriceCollection;
    @BindView(R.id.tv_sign_back)
    TextView tvSignBack;
    @BindView(R.id.tv_price_signback)
    TextView tvPriceSignback;
    @BindView(R.id.rb_none)
    RadioButton rbNone;
    @BindView(R.id.rb_paper)
    RadioButton rbPaper;
    @BindView(R.id.rb_electronic)
    RadioButton rbElectronic;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.rb_yes)
    RadioButton rbYes;
    @BindView(R.id.rb_no)
    RadioButton rbNo;
    @BindView(R.id.btn_add)
    TextView btnAdd;
    @BindView(R.id.et_insured)
    EditText etInsured;
    @BindView(R.id.et_collection)
    EditText etCollection;

    private ShippingAddValueB shippingAddValue;
    private ShipAddValueFeeB shipAddValueFeeB;
    private String rmb;

    private RadioButton currentSign;
    private RadioButton currentNotice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_value_add);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initTop();
        initData();
        initInsure();
        initCollection();
        initRadio();
    }

    private void initRadio() {
        rbNone.setOnCheckedChangeListener(this);
        rbPaper.setOnCheckedChangeListener(this);
        rbElectronic.setOnCheckedChangeListener(this);
        rbYes.setOnCheckedChangeListener(this);
        rbNo.setOnCheckedChangeListener(this);
    }

    private void initCollection() {
        InputFilter[] filters = {new EditInputFilter(100000000)};
        etCollection.setFilters(filters);
        etCollection.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    int value = Integer.parseInt(s.toString());
                    int fee = collectionFeeLimit(ShipUtil.getFee(value, shipAddValueFeeB.getDaiRate()).intValue());
                    tvPriceCollection.setText(rmb + fee);
                    shippingAddValue.setColletionValue(value);
                    shippingAddValue.setColletionFee(fee);
                } else {
                    tvPriceCollection.setText(rmb + 0);
                    shippingAddValue.setColletionValue(0);
                    shippingAddValue.setColletionFee(0);
                }
            }
        });
    }

    private void initInsure() {
        InputFilter[] filters = {new EditInputFilter(shipAddValueFeeB.getBaoAskLimit())};
        etInsured.setFilters(filters);
        etInsured.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    int value = Integer.parseInt(s.toString());
                    int fee = insureFeeLimit(ShipUtil.getFee(value, shipAddValueFeeB.getBaoRate()).intValue());
                    tvPriceInsured.setText(rmb + fee);
                    shippingAddValue.setInsureFee(fee);
                    shippingAddValue.setInsureValue(value);
                } else {
                    tvPriceInsured.setText(rmb + 0);
                    shippingAddValue.setInsureFee(0);
                    shippingAddValue.setInsureValue(0);
                }
            }
        });
    }

    private int insureFeeLimit(int fee) {
        if (shipAddValueFeeB.getBaoPriceLimit() != 0) {
            fee = fee < shipAddValueFeeB.getBaoPriceLimit() ? shipAddValueFeeB.getBaoPriceLimit() : fee;
        }
        return fee;
    }

    private int collectionFeeLimit(int fee) {
        if (shipAddValueFeeB.getDaiPriceLimit() != 0) {
            fee = fee < shipAddValueFeeB.getDaiPriceLimit() ? shipAddValueFeeB.getDaiPriceLimit() : fee;
        }
        return fee;
    }

    private void initData() {
        rmb = getString(R.string.common_rmb);
        String data = getIntent().getStringExtra("data");
        String fee = getIntent().getStringExtra("fee");
//        isEmpty = TextUtils.isEmpty(data);
        shippingAddValue = TextUtils.isEmpty(data) ? new ShippingAddValueB() : new Gson().fromJson(data, ShippingAddValueB.class);
        shipAddValueFeeB = new Gson().fromJson(fee, ShipAddValueFeeB.class);
        setAddValueView();
    }

    private void setAddValueView() {
        tvPriceInsured.setText(rmb + shippingAddValue.getInsureFee());
        etInsured.setText(shippingAddValue.getInsureValue() == 0 ? "" : shippingAddValue.getInsureValue() + "");
        tvPriceCollection.setText(rmb + shippingAddValue.getColletionFee());
        etCollection.setText(shippingAddValue.getColletionValue() == 0 ? "" : shippingAddValue.getColletionValue() + "");
        tvPriceSignback.setText(rmb + shippingAddValue.getSignFee());
        initSign();
        initNotice();
    }

    private void initSign() {
        switch (shippingAddValue.getSignType()) {
            case 1:
                currentSign = rbNone;
                break;
            case 2:
                currentSign = rbPaper;
                break;
            case 3:
                currentSign = rbElectronic;
                break;
            default:
                currentSign = rbNone;
                shippingAddValue.setSignType(1);
                break;
        }
        currentSign.setChecked(true);
        currentSign.setTextColor(getColor(R.color.white));
    }

    private void initNotice() {
        if (shippingAddValue.getReceiveType() == 1) {
            currentNotice = rbYes;
        } else {
            currentNotice = rbNo;
        }
        currentNotice.setChecked(true);
        currentNotice.setTextColor(getColor(R.color.white));
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    //    R.id.rb_none, R.id.rb_paper, R.id.rb_electronic, R.id.rb_yes, R.id.rb_no,
    @OnClick({R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.rb_none:
//                switchSign(view, SignB.NONE, 0);
//                break;
//            case R.id.rb_paper:
//                switchSign(view, SignB.PAPER, shipAddValueFeeB.getQianPaper());
//                break;
//            case R.id.rb_electronic:
//                switchSign(view, SignB.ELECTRONIC, shipAddValueFeeB.getQianEle());
//                break;
//            case R.id.rb_yes:
//                switchNotice(view, 1);
//                break;
//            case R.id.rb_no:
//                switchNotice(view, 0);
//                break;
            case R.id.btn_add:
                save();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton view, boolean b) {
        if (b) {
            switch (view.getId()) {
                case R.id.rb_none:
                    switchSign(view, SignB.NONE, 0);
                    break;
                case R.id.rb_paper:
                    switchSign(view, SignB.PAPER, shipAddValueFeeB.getQianPaper());
                    break;
                case R.id.rb_electronic:
                    switchSign(view, SignB.ELECTRONIC, shipAddValueFeeB.getQianEle());
                    break;
                case R.id.rb_yes:
                    switchNotice(view, 1);
                    break;
                case R.id.rb_no:
                    switchNotice(view, 0);
                    break;
                default:
                    break;
            }
        }
    }

    private void switchSign(View view, SignB sign, int price) {
        if (view.getId() != currentSign.getId()) {
            currentSign.setChecked(false);
            currentSign.setTextColor(getColor(R.color.text_gray));
            currentSign = (RadioButton) view;
            currentSign.setTextColor(getColor(R.color.white));
            currentSign.setChecked(true);
            shippingAddValue.setSignType(sign.getType());
            shippingAddValue.setSignFee(price);
            tvPriceSignback.setText(rmb + price);
        } else {
            currentSign.setChecked(true);
        }
    }

    private void switchNotice(View view, int i) {
        if (view.getId() != currentNotice.getId()) {
            currentNotice.setChecked(false);
            currentNotice.setTextColor(getColor(R.color.text_gray));
            currentNotice = (RadioButton) view;
            currentNotice.setTextColor(getColor(R.color.white));
            currentNotice.setChecked(true);
            shippingAddValue.setReceiveType(i);
        } else {
            currentNotice.setChecked(true);
        }
    }

    private void save() {
        setResult(CodeUtil.ShipAddValue, new Intent().putExtra("data", new Gson().toJson(shippingAddValue)));
        finish();
    }


}
