package com.yyy.wrsf.mine.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.OrderBean;
import com.yyy.wrsf.beans.TabB;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.mine.order.persenter.OrderP;
import com.yyy.wrsf.mine.order.persenter.OrderReceiveP;
import com.yyy.wrsf.mine.order.view.IOrderV;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.TopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderReceiveActivity extends BaseActivity implements XRecyclerView.LoadingListener, IOrderV {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private List<OrderBean> orders = new ArrayList<>();
    private OrderReceiveAdapter adapter;
    private List<TabB> tabs;
    private List<TabLayout.Tab> tabsV = new ArrayList<>();
    private OrderReceiveP orderP;
    private int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        orderP = new OrderReceiveP(this);
        init();
        orderP.getData(0);
    }

    private void init() {
        initTop();
        initRecycle();
        tabLayout.setVisibility(View.GONE);
        orderP.getTabs();
    }


    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
        topView.setTitle(getString(R.string.main_receive));
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

    private OrderReceiveAdapter initAdapter() {
        adapter = new OrderReceiveAdapter(OrderReceiveActivity.this, orders);
        adapter.setOnItemClickListener((int pos) -> {
            go2Detail(pos);
        });
        adapter.setOnEditListener((int pos) -> {
            go2Pay(pos);
        });
        adapter.setOnCancleListener((int pos) -> {
//                        go2Cancle(pos);
            orderP.cancel(pos, orders.get(pos).getContractNo());
        });
        return adapter;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        orderP.getData(1);
    }


    private void go2Detail(int pos) {
        startActivityForResult(
                new Intent()
                        .setClass(this, OrderDetailActivity.class)
                        .putExtra("pos", pos)
                        .putExtra("data", new Gson().toJson(orders.get(pos)))
                , CodeUtil.MODIFY);
    }

    private void go2Pay(int pos) {
    }

    private void clear() {
        orders.clear();
        refreshList();
    }

    @Override
    public void refreshList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addDatas(List<OrderBean> list) {
        orders.addAll(list);
    }

    @Override
    public void cancelLoadMore() {
        recyclerView.setLoadingMoreEnabled(false);
    }

    @Override
    public void setItemType(int pos, int type) {
        orders.get(pos).setContractStatus(type);
    }

    @Override
    public Integer getType() {
        return null;
    }

    @Override
    public String getOrderName() {
        return null;
    }

    @Override
    public void setTabs(List<TabB> tabs) {
        this.tabs = tabs;
        initTabs();
    }

    @Override
    public void stopLoad() {
        recyclerView.loadMoreComplete();
    }

    private void initTabs() {
        for (TabB tabB : tabs) {
            TabLayout.Tab tab = tabLayout.newTab().setText(tabB.getName());
            tabsV.add(tab);
            tabLayout.addTab(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                clear();
                orderP.resetPage();
                orderP.getData(0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
        orderP.detachView();
        super.onDestroy();
    }
}
