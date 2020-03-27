package com.yyy.wrsf.company.order;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.dialog.LoadingDialog;

import com.yyy.wrsf.mine.order.OrderDetailActivity;
import com.yyy.wrsf.model.OrderModel;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.order.OrderUrl;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderActivity extends BaseActivity implements XRecyclerView.LoadingListener {
    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    private PagerRequestBean pager;
    private List<OrderModel> orders = new ArrayList<>();
    private OrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order); init();
        getData();
    }
    private void init() {
        initTop();
        initRecycle();
        initPager();
    }

    private void initPager() {
        pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(30);
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
    }private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.address + OrderUrl.getPageList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                LoadingFinish(null);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<OrderModel> list = new Gson().fromJson(result.getData(), new TypeToken<List<OrderModel>>() {
                        }.getType());
                        orders.addAll(list);
                        refrishList();
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(com.yyy.wrsf.mine.order.OrderActivity.class.getName(), result.getMsg());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoadingFinish(e.getMessage());
                }
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private void refrishList() {
        if (adapter == null) {
            adapter = new OrderAdapter(this, orders);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((int pos) -> {
                go2Detail(pos);
            });
            adapter.setOnConfirmListener((int pos) -> {
                go2Pay(pos);
            });
            adapter.setOnCancleListener((int pos) -> {
                go2Cancle(pos);
            });
        } else {
        }
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(pager)));
        return params;
    }


    private void go2Detail(int pos) {
        startActivityForResult(new Intent().setClass(this, OrderDetailActivity.class).putExtra("pos", pos), CodeUtil.MODIFY);
    }

    private void go2Pay(int pos) {
    }

    private void go2Cancle(int pos) {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(cancleParams(), NetConfig.address, RequstType.PUT, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                LoadingFinish(null);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        orders.get(pos).setContractStatus(-1);
                        refrishList();
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(com.yyy.wrsf.mine.order.OrderActivity.class.getName(), result.getMsg());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoadingFinish(e.getMessage());
                }
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });

    }

    private List<NetParams> cancleParams() {
        List<NetParams> params = new ArrayList<>();
        return params;
    }
    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
