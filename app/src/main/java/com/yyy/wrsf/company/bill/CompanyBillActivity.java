package com.yyy.wrsf.company.bill;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.company.bill.adapter.CompanyBillAdapter;
import com.yyy.wrsf.company.bill.persenter.CompanyBillP;
import com.yyy.wrsf.company.bill.view.ICompanyBillV;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.enums.OrderEnum;
import com.yyy.wrsf.enums.bill.BillFieldEnum;
import com.yyy.wrsf.utils.DateUtil;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.TopView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyBillActivity extends BaseActivity implements ICompanyBillV, XRecyclerView.LoadingListener {

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

    private int current;
    private String month;
    private String field;
    private String order;
    private CompanyBillP companyBillP;
    private CompanyBillAdapter adapter;
    private List<CompanyBillB> list;


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
        companyBillP.getBill();
    }

    private void initData() {
        month = DateUtil.getYearAndMonth(new Date(System.currentTimeMillis()));
        field = BillFieldEnum.UNPAD.getFiled();
        order = OrderEnum.DESC.getOrder();
    }

    private void initList() {
        list = new ArrayList<>();
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

    private CompanyBillAdapter initAdapter() {
        adapter = new CompanyBillAdapter(this, list);
        adapter.setOnItemClickListener(pos -> {
        });
        return adapter;
    }

    @OnClick({R.id.tv_month, R.id.ll_unpay, R.id.ll_total})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_month:
                break;
            case R.id.ll_unpay:
                break;
            case R.id.ll_total:
                break;
            default:
                break;
        }
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
        list.addAll(list);
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
        companyBillP.resetPage(0);
        companyBillP.getBill();
    }

    @Override
    public void onLoadMore() {
        companyBillP.getBill();
    }
}
