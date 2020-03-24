package com.yyy.wrsf.mine.shipping;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.model.ShippingAddValueModel;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingValueAddActivity extends AppCompatActivity {

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

    private ShippingAddValueModel shippingAddValue;
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
    }

    private void initData() {
        rmb = getString(R.string.common_rmb);
        String data = getIntent().getStringExtra("data");
//        isEmpty = TextUtils.isEmpty(data);
        shippingAddValue = TextUtils.isEmpty(data) ? new ShippingAddValueModel() : new Gson().fromJson(data, ShippingAddValueModel.class);
        setAddValueView();
    }

    private void setAddValueView() {
        tvPriceInsured.setText(rmb + shippingAddValue.getInsureFee());
        etInsured.setText(shippingAddValue.getInsureValue());
        tvPriceCollection.setText(rmb + shippingAddValue.getColletionFee());
        etCollection.setText(shippingAddValue.getColletionValue());
        tvPriceSignback.setText(rmb + shippingAddValue.getSignFee());
        initSign();
        initNotice();
    }

    private void initSign() {
        switch (shippingAddValue.getSignType()) {
            case 1:
                currentSign = rbNo;
                break;
            case 2:
                currentSign = rbPaper;
                break;
            case 3:
                currentSign = rbElectronic;
                break;
            default:
                currentSign = rbNo;
                shippingAddValue.setSignType(1);
                break;
        }
        currentSign.setChecked(true);
    }

    private void initNotice() {
        if (shippingAddValue.getReceiveType() == 1) {
            currentNotice = rbYes;
        } else {
            currentNotice = rbNo;
        }
        currentNotice.setChecked(true);
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    @OnClick({R.id.rb_none, R.id.rb_paper, R.id.rb_electronic, R.id.rb_yes, R.id.rb_no, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_none:
                switchSign(view, 1);
                break;
            case R.id.rb_paper:
                switchSign(view, 2);
                break;
            case R.id.rb_electronic:
                switchSign(view, 3);
                break;
            case R.id.rb_yes:
                switchNotice(view, 1);
                break;
            case R.id.rb_no:
                switchNotice(view, 0);
                break;
            case R.id.btn_add:
                save();
                break;
            default:
                break;
        }
    }

    private void switchSign(View view, int i) {
        if (view.getId() != currentSign.getId()) {
            currentSign.setChecked(false);
            currentSign.setTextColor(getColor(R.color.text_gray));
            currentSign = (RadioButton) view;
            currentSign.setTextColor(getColor(R.color.white));
            currentSign.setChecked(true);
            shippingAddValue.setSignType(i);
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
