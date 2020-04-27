package com.yyy.wrsf.company.collection;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BasePickActivity;
import com.yyy.wrsf.beans.OrderBean;
import com.yyy.wrsf.beans.company.bill.CompanyBillCollectionTotalB;
import com.yyy.wrsf.company.collection.persenter.CollectionP;
import com.yyy.wrsf.company.collection.persenter.ICollectionP;
import com.yyy.wrsf.company.collection.view.ICollectionV;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.utils.DateUtil;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.TimePickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnTimeSelectListener;
import com.yyy.yyylibrary.pick.view.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectionActivity extends BasePickActivity implements ICollectionV, XRecyclerView.LoadingListener {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_title_unpay)
    TextView tvTitleUnpay;
    @BindView(R.id.tv_unpay)
    TextView tvUnpay;
    @BindView(R.id.ll_unpay)
    LinearLayout llUnpay;
    @BindView(R.id.tv_title_total)
    TextView tvTitleTotal;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.ll_total)
    LinearLayout llTotal;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    private OrderCollectionAdapter adapter;
    private List<OrderBean> list = new ArrayList<>();
    private String month;
    private TimePickerView pvDate;

    private int payType = 0;
    private ICollectionP collectionP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection2);
        ButterKnife.bind(this);
        collectionP = new CollectionP(this);
        init();
    }

    private void init() {
        initData();
        initView();
        collectionP.getData();
        collectionP.getOrder();
    }

    private void initData() {
        month = DateUtil.getYearAndMonth(new Date(System.currentTimeMillis()));
    }

    private void initView() {
        initTop();
        initRecycle();
        initMonth();
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initRecycle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerView.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLoadingListener(this);
        recyclerView.setAdapter(initAdapter());

    }

    private void initMonth() {
        tvMonth.setText(month + getString(R.string.common_select_img));
    }

    private OrderCollectionAdapter initAdapter() {
        adapter = new OrderCollectionAdapter(this, list);
        return adapter;
    }

    @OnClick({R.id.ll_unpay, R.id.ll_total, R.id.tv_month})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_unpay:
                if (payType != 0) {
                    setUnpaid();
                }
                break;
            case R.id.ll_total:
                if (payType != 1) {
                    setPaid();
                }
                break;
            case R.id.tv_month:
                if (pvDate == null) {
                    initPvDate();
                }
                pvDate.show();
                break;
            default:
                break;
        }
    }
    private void setUnpaid() {
        payType = 0;
        tvUnpay.setTextColor(getColor(R.color.text_common));
        tvTitleUnpay.setTextColor(getColor(R.color.text_common));
        tvTotal.setTextColor(getColor(R.color.text_gray));
        tvTitleTotal.setTextColor(getColor(R.color.text_gray));
        onRefresh();
    }
    private void setPaid() {
        payType = 1;
        tvTotal.setTextColor(getColor(R.color.text_common));
        tvTitleTotal.setTextColor(getColor(R.color.text_common));
        tvUnpay.setTextColor(getColor(R.color.text_gray));
        tvTitleUnpay.setTextColor(getColor(R.color.text_gray));
        onRefresh();
    }
    private void initPvDate() {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarLast = Calendar.getInstance();
        calendarLast.add(Calendar.YEAR, -3);
        pvDate = new TimePickerBuilder(this, (date, v) -> {
            month = DateUtil.getYearAndMonth(date);
            tvMonth.setText(month + getString(R.string.common_select_img));
            tvTotal.setText(getString(R.string.common_conjunction));
            tvUnpay.setText(getString(R.string.common_conjunction));
            collectionP.getData();
            onRefresh();
        }).setRangDate(calendarLast, calendar)
                .setDate(calendar)
                .setType(new boolean[]{true, true, false, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setContentTextSize(18)
                .setBgColor(0xFFFFFFFF)
                .build();
        setDialog(pvDate);
        initDialogWindow(pvDate.getDialog().getWindow());
    }

    @Override
    public String getMonth() {
        return month;
    }

    @Override
    public int getPayType() {
        return payType;
    }

    @Override
    public int getCompanyId() {
        return 0;
    }

    @Override
    public void addList(List<OrderBean> list) {
        this.list.addAll(list);
    }

    @Override
    public void refreshList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setLoad(boolean canLoad) {
        recyclerView.setLoadingMoreEnabled(canLoad);
    }

    @Override
    public void setTotal(CompanyBillCollectionTotalB total) {
        tvUnpay.setText(total.getUnpaidTotal() + "");
        tvTotal.setText(total.getPaidTotal() + "");
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
    public void onRefresh() {
        list.clear();
        refreshList();
        collectionP.resetPager();
        setLoad(true);
        collectionP.getOrder();
    }

    @Override
    public void onLoadMore() {
        collectionP.getOrder();
    }
}
