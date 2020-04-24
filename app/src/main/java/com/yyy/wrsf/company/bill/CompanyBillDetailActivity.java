package com.yyy.wrsf.company.bill;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.company.bill.adapter.CompanyBillAdapter;
import com.yyy.wrsf.company.bill.adapter.CompanyBillDetailAdapter;
import com.yyy.wrsf.company.bill.persenter.CompanyBillDetailP;
import com.yyy.wrsf.company.bill.view.ICompanyBillDetailV;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.view.TimePickerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyBillDetailActivity extends BaseActivity implements XRecyclerView.LoadingListener, ICompanyBillDetailV {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    private String month;
    private int customerId;

    private CompanyBillDetailAdapter adapter;
    private CompanyBillDetailP companyBillDetailP;
    private CompanyBillB companyBillB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_bill_detail);
        ButterKnife.bind(this);
        companyBillDetailP = new CompanyBillDetailP(this);
        init();
        companyBillDetailP.getData();
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
//        recyclerView.setAdapter(initAdapter());
    }

    private CompanyBillDetailAdapter initAdapter() {
        return adapter;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

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
    public void setLoad(boolean canLoad) {
        recyclerView.setLoadingMoreEnabled(canLoad);
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
}
