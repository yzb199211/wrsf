package com.yyy.wrsf.company.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.base.BasePickActivity;
import com.yyy.wrsf.base.BaseSelectBean;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.beans.company.bill.CompanyBillTotalB;
import com.yyy.wrsf.company.bill.adapter.CompanyBillAdapter;
import com.yyy.wrsf.company.bill.persenter.CompanyBillP;
import com.yyy.wrsf.company.bill.view.ICompanyBillV;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.enums.OrderEnum;
import com.yyy.wrsf.enums.bill.BillFieldEnum;
import com.yyy.wrsf.mine.bill.BillMonthActivity;
import com.yyy.wrsf.utils.DateUtil;
import com.yyy.wrsf.view.popwin.Popwin;
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

public class CompanyBillActivity extends BasePickActivity implements ICompanyBillV, XRecyclerView.LoadingListener {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_unpay)
    TextView tvUnpay;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_title_unpay)
    TextView tvTitleUnpay;
    @BindView(R.id.tv_title_total)
    TextView tvTitleTotal;

    //    private Popwin<BaseSelectBean> popMonth;
    private TimePickerView pvDate;

    private int current = 0;
    private String month;
    private String field;
    private String order;
    private CompanyBillP companyBillP;
    private CompanyBillAdapter adapter;
    private List<CompanyBillB> list;
    private List<BaseSelectBean> monthSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_bill);
        ButterKnife.bind(this);
        companyBillP = new CompanyBillP(this);
        init();
    }

    private void init() {
        initData();
        initList();
        initView();
        companyBillP.getData();
        companyBillP.getBill();
    }

    private void initData() {
        month = DateUtil.getYearAndMonth(new Date(System.currentTimeMillis()));
        field = BillFieldEnum.UNPAD.getFiled();
        order = OrderEnum.DESC.getOrder();
    }

    private void initList() {
        list = new ArrayList<>();
        monthSelect = new ArrayList<>();
    }


    private void initView() {
        initTop();
        initRecycle();
        tvMonth.setText(month + getString(R.string.common_select_img));
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

    private CompanyBillAdapter initAdapter() {
        adapter = new CompanyBillAdapter(this, list);
        adapter.setOnItemClickListener(pos -> {
            go2Detail(pos);
        });
        return adapter;
    }

    private void go2Detail(int pos) {
        startActivity(new Intent()
                .setClass(this, CompanyBillDetailActivity.class)
                .putExtra("month", month)
                .putExtra("data", new Gson().toJson(list.get(pos))));
    }

    @OnClick({R.id.tv_month, R.id.ll_unpay, R.id.ll_total})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_month:
                if (pvDate == null) {
                    initPvDate();
                }
                pvDate.show();
                break;
            case R.id.ll_unpay:
                if (current != 0) {
                    setUnpaid();
                }
                break;
            case R.id.ll_total:
                if (current != 1) {
                    setTotal();
                }
                break;
            default:
                break;
        }
    }

    private void setUnpaid() {
        current = 0;
        field = BillFieldEnum.UNPAD.getFiled();
        tvUnpay.setTextColor(getColor(R.color.text_common));
        tvTitleUnpay.setTextColor(getColor(R.color.text_common));
        tvTotal.setTextColor(getColor(R.color.text_gray));
        tvTitleTotal.setTextColor(getColor(R.color.text_gray));
        onRefresh();
    }

    private void setTotal() {
        current = 1;
        field = BillFieldEnum.TOTAL.getFiled();
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
        pvDate = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                month = DateUtil.getYearAndMonth(date);
                tvMonth.setText(month + getString(R.string.common_select_img));
                tvTotal.setText(getString(R.string.common_conjunction));
                tvUnpay.setText(getString(R.string.common_conjunction));
                companyBillP.getData();
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
    public void addList(List<CompanyBillB> list) {
        this.list.addAll(list);
    }

    @Override
    public void setTotal(CompanyBillTotalB item) {
        tvUnpay.setText(item.getUnpaid() + "");
        tvTotal.setText(item.getContractTotal() + "");
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
    public void onRefresh() {
        list.clear();
        refreshList();
        loadMore(true);
        companyBillP.resetPage(0);
        companyBillP.getBill();
    }

    @Override
    public void onLoadMore() {
        companyBillP.getBill();
    }

    @Override
    protected void onDestroy() {
        companyBillP.detachView();
        super.onDestroy();
    }
}
