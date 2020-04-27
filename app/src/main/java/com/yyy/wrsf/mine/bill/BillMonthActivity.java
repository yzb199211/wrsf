package com.yyy.wrsf.mine.bill;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.base.BasePickActivity;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.beans.company.bill.CompanyBillTotalB;
import com.yyy.wrsf.beans.person.PersonBillB;
import com.yyy.wrsf.company.bill.CompanyBillDetailActivity;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.enums.OrderEnum;
import com.yyy.wrsf.enums.bill.BillFieldEnum;
import com.yyy.wrsf.mine.bill.adapter.BillMonthAdapter;
import com.yyy.wrsf.mine.bill.persenter.BillMonthP;
import com.yyy.wrsf.mine.bill.view.IBillMonthV;
import com.yyy.wrsf.utils.DateUtil;
import com.yyy.wrsf.utils.TimeUtil;
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

public class BillMonthActivity extends BasePickActivity implements XRecyclerView.LoadingListener, IBillMonthV {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_unpay)
    TextView tvUnpay;

    private BillMonthAdapter adapter;
    private TimePickerView pvDate;
    private BillMonthP billMonthP;
    private String month;
    private String field;
    private String order;

    private List<PersonBillB> billBS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_month);
        ButterKnife.bind(this);
        billMonthP = new BillMonthP(this);
        init();
        billMonthP.getData();
        billMonthP.getBill();
    }

    private void init() {
        initTop();
        initRecycle();
        initData();
    }

    private void initData() {
        month = DateUtil.getYearAndMonth(new Date(System.currentTimeMillis()));
        field = BillFieldEnum.UNPAD.getFiled();
        order = OrderEnum.DESC.getOrder();
        tvMonth.setText(month + getString(R.string.common_select_img));
    }


    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initRecycle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayout.VERTICAL));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerView.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLoadingListener(this);
        recyclerView.setAdapter(initAdapter());
    }

    private BillMonthAdapter initAdapter() {
        adapter = new BillMonthAdapter(this, billBS);
        adapter.setOnItemClickListener(pos -> {
            go2Detai(pos);
        });
        adapter.setOnBillApplyListener(pos -> {
        });
        adapter.setOnPayListener(pos -> {
        });
        return adapter;
    }

    private void go2Detai(int pos) {
        startActivity(new Intent()
                .setClass(this, BillMonthDetailActivity.class)
                .putExtra("month", month)
                .putExtra("data", new Gson().toJson(billBS.get(pos))));
    }

    @OnClick(R.id.tv_month)
    public void onViewClicked() {
        if (pvDate == null) {
            try {
                initPvDate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pvDate.show();
    }

    private void initPvDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarLast = Calendar.getInstance();
        calendarLast.add(Calendar.YEAR, -3);
        pvDate = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                month = DateUtil.getYearAndMonth(date);
                tvMonth.setText(BillMonthActivity.this.month + getString(R.string.common_select_img));
                tvTotal.setText(getString(R.string.bill_total) + "：" + getString(R.string.common_conjunction));
                tvUnpay.setText(getString(R.string.bill_unpay) + "：" + getString(R.string.common_conjunction));
                billMonthP.getData();
                onRefresh();
            }
        }).setRangDate(calendarLast, calendar)
                .setDate(calendar)
                .setType(new boolean[]{true, true, false, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setContentTextSize(18)
                .setBgColor(0xFFFFFFFF)
                .build();
        setDialog(pvDate);
        initDialogWindow(pvDate.getDialog().getWindow());
        pvDate.show();
    }

    @Override
    public void onRefresh() {
        billBS.clear();
        refreshList();
        loadMore(true);
        billMonthP.resetPage(0);
        billMonthP.getBill();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public String getMonth() {
        return month;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getOrder() {
        return order;
    }

    @Override
    public void addList(List<PersonBillB> list) {
        billBS.addAll(list);
    }

    @Override
    public void setTotal(CompanyBillTotalB item) {
        tvTotal.setText(getString(R.string.bill_total) + "：" + item.getContractTotal());
        tvUnpay.setText(getString(R.string.bill_unpay) + "：" + item.getUnpaid());
    }

    @Override
    public void refreshList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(boolean canLoad) {
        recyclerView.setLoadingMoreEnabled(canLoad);
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
    protected void onDestroy() {
        billMonthP.detachView();
        super.onDestroy();
    }
}
