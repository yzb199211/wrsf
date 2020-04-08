package com.yyy.wrsf.mine.order;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.enums.ContractStatusEnum;
import com.yyy.wrsf.enums.PayTypeEnum;
import com.yyy.wrsf.beans.OrderBean;
import com.yyy.wrsf.mine.order.bean.LogBean;
import com.yyy.wrsf.mine.order.persenter.LogPersenter;
import com.yyy.wrsf.mine.order.view.ILogView;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.recycle.NoScrollGvManager;
import com.yyy.wrsf.view.topview.TopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends BaseActivity implements ILogView<LogBean> {

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

    private OrderBean order;
    private LogPersenter<LogBean> logPersenter;

    private List<LogBean> logs = new ArrayList<>();
    private LogAdapter logAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        logPersenter = new LogPersenter<>(this);
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
        Log.e("order", getIntent().getStringExtra("data"));
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

    private void initList() {
        recyclerView.setLayoutManager(new NoScrollGvManager(this, 1).setScrollEnabled(false));
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

//        Log.e("logs", new Gson().toJson(data));
    }

    @Override
    public void showAll(List<LogBean> data) {

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
    protected void onDestroy() {
        logPersenter.detachView();
        super.onDestroy();
    }
}
