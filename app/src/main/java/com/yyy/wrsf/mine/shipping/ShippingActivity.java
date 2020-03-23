package com.yyy.wrsf.mine.shipping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yyy.wrsf.R;
import com.yyy.wrsf.view.textselect.TextMenuItem;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_address_detail_send, R.id.ll_send, R.id.tv_address_detail_receive, R.id.ll_receive, R.id.tmi_company, R.id.tmi_goods, R.id.tmi_value_add, R.id.tmi_pick_date, R.id.tmi_remark, R.id.tv_total, R.id.tv_protocol, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address_detail_send:
                break;
            case R.id.ll_send:
                break;
            case R.id.tv_address_detail_receive:
                break;
            case R.id.ll_receive:
                break;
            case R.id.tmi_company:
                break;
            case R.id.tmi_goods:
                initGoods();
                break;
            case R.id.tmi_value_add:
                break;
            case R.id.tmi_pick_date:
                break;
            case R.id.tmi_remark:
                break;
            case R.id.tv_total:
                break;
            case R.id.tv_protocol:
                break;
            case R.id.tv_submit:
                break;
            default:
                break;
        }
    }

    private void initGoods() {
        startActivityForResult(new Intent().setClass(this, ShippingGoodsActivity.class), 0);
    }
}
