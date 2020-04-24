package com.yyy.wrsf.mine.bill;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.beans.company.bill.CompanyBillDetailB;
import com.yyy.wrsf.company.bill.persenter.CompanyBillDetailP;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.mine.bill.adapter.BillMonthDetailAdapter;
import com.yyy.wrsf.mine.bill.persenter.BillMonthDetailP;
import com.yyy.wrsf.mine.bill.view.IBillMonthDetailV;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.TopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BillMonthDetailActivity extends BaseActivity implements XRecyclerView.LoadingListener, IBillMonthDetailV {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    private String month;
    private List<CompanyBillDetailB> billDetailBS = new ArrayList<>();
    private BillMonthDetailAdapter adapter;
    private BillMonthDetailP billMonthDetailP;
    private CompanyBillB companyBillB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_bill_detail);
        ButterKnife.bind(this);
        billMonthDetailP = new BillMonthDetailP(this);
        init();
        billMonthDetailP.getData();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
        month = getIntent().getStringExtra("month");
        companyBillB = new Gson().fromJson(getIntent().getStringExtra("data"), CompanyBillB.class);
    }

    private void initView() {
        initTop();
        initRecycle();
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

    private BillMonthDetailAdapter initAdapter() {
        adapter = new BillMonthDetailAdapter(this, billDetailBS);
        adapter.setOnItemClickListener(pos -> {

        });
        return adapter;
    }

    @Override
    public void onRefresh() {
        setLoad(true);

    }

    @Override
    public void onLoadMore() {
        billMonthDetailP.getData();
    }

    @Override
    public String getMonth() {
        return month;
    }

    @Override
    public int getCustomerId() {
        return companyBillB.getCustomerId();
    }

    @Override
    public int getCustomerTypeId() {
        return companyBillB.getCustomerType();
    }

    @Override
    public int getCompanyId() {
        return companyBillB.getTransCompanyRecNo();
    }

    @Override
    public void setLoad(boolean canLoad) {
        recyclerView.setLoadingMoreEnabled(canLoad);
    }

    @Override
    public void addList(List<CompanyBillDetailB> list) {
        billDetailBS.addAll(list);
    }

    @Override
    public void refreshList() {
        adapter.notifyDataSetChanged();
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
        billMonthDetailP.detachView();
        super.onDestroy();
    }
}
