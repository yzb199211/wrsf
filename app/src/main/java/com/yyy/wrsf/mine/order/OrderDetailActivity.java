package com.yyy.wrsf.mine.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.OrderBean;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.enums.ContractStatusEnum;
import com.yyy.wrsf.enums.PayTypeEnum;
import com.yyy.wrsf.mine.order.bean.LogBean;
import com.yyy.wrsf.mine.order.persenter.LogPersenter;
import com.yyy.wrsf.mine.order.view.ILogView;
import com.yyy.wrsf.mine.pay.PayActivity;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.TopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity implements ILogView {

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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ecv_value_add)
    EditClearView ecvValueAdd;
    @BindView(R.id.ecv_receive_tel)
    EditClearView ecvReceiveTel;
    @BindView(R.id.ecv_send_add)
    EditClearView ecvSendAdd;
    @BindView(R.id.ecv_send_tel)
    EditClearView ecvSendTel;
    @BindView(R.id.ecv_receive_add)
    EditClearView ecvReceiveAdd;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.ecv_wait_notice)
    EditClearView ecvWaitNotice;
    @BindView(R.id.ecv_sign)
    EditClearView ecvSign;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.ecv_dis_total)
    EditClearView ecvDisTotal;
    @BindView(R.id.ecv_price)
    EditClearView ecvPrice;
    @BindView(R.id.ecv_goods_num)
    EditClearView ecvGoodsNum;


    private OrderBean order;
    private LogPersenter logPersenter;

    private List<LogBean> logs = new ArrayList<>();
    private LogAdapter logAdapter;
    private boolean isCompany = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        logPersenter = new LogPersenter(this);

        init();
        getLog();
    }

    private void getLog() {
        logPersenter.getLog();
    }

    private void init() {
        initData();
        initTop();
        initOrder();
        initGoods();
        initFee();
        initList();
    }

    private void initData() {
        order = new Gson().fromJson(getIntent().getStringExtra("data"), OrderBean.class);
        initBottom(getIntent().getIntExtra("cancle", 0), getIntent().getIntExtra("confirm", 0));
        isCompany = getIntent().getBooleanExtra("isCompany", false);
        Log.e("order", getIntent().getStringExtra("data"));
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initBottom(int cancle, int confirm) {
        if (cancle != 0) {
            llBottom.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.VISIBLE);
        }
        if (confirm != 0) {
            llBottom.setVisibility(View.VISIBLE);
            tvPay.setVisibility(View.VISIBLE);
        }
    }

    private void initOrder() {
        tvOrderNo.setText(getString(R.string.order_no) + "：" + order.getContractNo());
        tvOrderCompany.setText(order.getCompanyName() + "");
        tvOrderType.setText(ContractStatusEnum.getDescByStatus(order.getContractStatus()));
        tvSendArea.setText(order.getSendAdd() + "");
        tvSendPerson.setText(order.getSendName() + "");
        tvReceiveArea.setText(order.getRecAdd() + "");
        tvReceivePerson.setText(order.getRecName() + "");

        ecvSendAdd.setText(order.getSendDetail());
        ecvSendTel.setText(order.getSendTel());
        ecvReceiveAdd.setText(order.getRecDetail());
        ecvReceiveTel.setText(order.getRecTel());
    }

    private void initGoods() {
        ecvGoodsName.setText(order.getGoodsName() + "");
        ecvGoodsWeight.setText(order.getWeight() + "");
        ecvGoodsVolume.setText(order.getSize() + "");
        ecvGoodsNum.setText(order.getPackNumber() + "");
    }

    private void initFee() {
        ecvFeeType.setText(PayTypeEnum.getName(order.getPayType()));
        ecvTotal.setText(getString(R.string.common_rmb) + order.getContractTotalDetail());
        ecvDisTotal.setText(getString(R.string.common_rmb) + order.getContractTotal());
        ecvFeeUser.setText(order.getPlantMemberName());
        ecvFeeBase.setText(getString(R.string.common_rmb) + order.getTransTotal());
        ecvFeePick.setText(getString(R.string.common_rmb) + order.getPicTotal());
        ecvFeeSend.setText(getString(R.string.common_rmb) + order.getSendTotal());
        ecvValueAdd.setText(getString(R.string.common_rmb) + order.getValueAdd());
        ecvFeeInsure.setText(getString(R.string.common_rmb) + order.getBaoAsk());
        ecvFeeCollection.setText(getString(R.string.common_rmb) + order.getDaiTotal());
        ecvWaitNotice.setText(order.getNoticeYesNo() == 0 ? getString(R.string.common_no) : getString(R.string.common_yes));
        ecvSign.setText(order.getQianTypeName());
        ecvPrice.setText(order.getPrice());
    }

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new NoScrollGvManager(this, 1).setScrollEnabled(false));
        recyclerView.setAdapter(getAdapter());
    }

    private LogAdapter getAdapter() {
        logAdapter = new LogAdapter(this, logs);
        return logAdapter;
    }

    @Override
    public void startLoading() {
        LoadingDialog.showDialogForLoading(this);
    }

    @Override
    public void finishLoading(@Nullable String s) {
        LoadingFinish(s);
    }

    @Override
    public void toast(String s) {
        Toast(s);
    }

    @Override
    public void addLog(List<LogBean> data) {
        logs.addAll(data);
    }


    @Override
    public String getContractNo() {
        return order.getContractNo();
    }

    @Override
    public void refreshList() {
        logAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoad() {
        llMore.setVisibility(View.GONE);
    }

    @Override
    public void finishs() {
        finish();
    }

    @Override
    protected void onDestroy() {
        logPersenter.detachView();
        super.onDestroy();
    }

    @OnClick(R.id.tv_load_more)
    public void onViewClicked() {
        hideLoad();
        logPersenter.showAll();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                logPersenter.cancel();
                break;
            case R.id.tv_pay:
                if (!isCompany) {
                    go2Pay();
                } else {
                    logPersenter.confirm();
                }
                break;
        }
    }

    private void go2Pay() {
        startActivityForResult(
                new Intent()
                        .setClass(this, PayActivity.class)
                        .putExtra("data", new Gson().toJson(order))
                , CodeUtil.PAY);
    }

}
