package com.yyy.wrsf.mine.order;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.enums.ContractStatusEnum;
import com.yyy.wrsf.enums.PayTypeEnum;
import com.yyy.wrsf.model.OrderBean;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_date_lasts)
    TextView tvDateLasts;
    @BindView(R.id.tv_order_company)
    TextView tvOrderCompany;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_send_area)
    TextView tvSendArea;
    @BindView(R.id.tv_send_person)
    TextView tvSendPerson;
    @BindView(R.id.tv_receive_area)
    TextView tvReceiveArea;
    @BindView(R.id.tv_receive_person)
    TextView tvReceivePerson;
    @BindView(R.id.ecv_goods_name)
    EditClearView ecvGoodsName;
    @BindView(R.id.ecv_goods_weight)
    EditClearView ecvGoodsWeight;
    @BindView(R.id.ecv_goods_volume)
    EditClearView ecvGoodsVolume;
    @BindView(R.id.ecv_total)
    EditClearView ecvTotal;
    @BindView(R.id.ecv_fee_type)
    EditClearView ecvFeeType;
    @BindView(R.id.ecv_fee_user)
    EditClearView ecvFeeUser;
    @BindView(R.id.ecv_fee_base)
    EditClearView ecvFeeBase;
    @BindView(R.id.ecv_fee_pick)
    EditClearView ecvFeePick;
    @BindView(R.id.ecv_fee_send)
    EditClearView ecvFeeSend;
    @BindView(R.id.ecv_fee_insure)
    EditClearView ecvFeeInsure;
    @BindView(R.id.ecv_fee_collection)
    EditClearView ecvFeeCollection;


    private OrderBean order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initData();
        initTop();
        initOrder();
        initGoods();
        initFee();
    }

    private void initData() {
        order = new Gson().fromJson(getIntent().getStringExtra("data"), OrderBean.class);
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initOrder() {
        tvOrderNo.setText(getString(R.string.order_no) + "：" + order.getContractNo());
        tvOrderCompany.setText(order.getCompanyName());
        tvOrderType.setText(ContractStatusEnum.getDescByStatus(order.getContractStatus()));
        tvSendArea.setText(order.getSendAdd());
        tvSendPerson.setText(order.getSendName());
        tvReceiveArea.setText(order.getRecAdd());
        tvReceivePerson.setText(order.getRecName());
    }

    private void initGoods() {
        ecvGoodsName.setText(order.getGoodsName());
        ecvGoodsWeight.setText(order.getWeight() + "");
        ecvGoodsVolume.setText(order.getSize() + "");
    }

    private void initFee() {
        ecvFeeType.setText(PayTypeEnum.getName(order.getPayType()));
        ecvTotal.setText(order.getContractTotal() + "");
        ecvFeeUser.setText(order.getPlantMemberName());
        ecvFeeBase.setText(getString(R.string.common_rmb) + order.getTransTotal());
    }
}
