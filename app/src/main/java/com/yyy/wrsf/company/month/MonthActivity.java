package com.yyy.wrsf.company.month;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.month.MonthB;
import com.yyy.wrsf.company.month.persenter.MonthP;
import com.yyy.wrsf.company.month.view.IMonthV;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.mine.notice.NoticeFragment;
import com.yyy.wrsf.beans.month.CustomerMonthB;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.month.MonthUrl;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MonthActivity extends BaseActivity implements IMonthV, XRecyclerView.LoadingListener {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    @BindView(R.id.ecv_search)
    EditClearView ecvSearch;

    private List<CustomerMonthB> monthModels = new ArrayList<>();
    private MonthAdapter monthAdapter;
    private MonthP monthP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
        ButterKnife.bind(this);
        monthP = new MonthP(this);
        init();
        monthP.getData();
    }


    private void init() {
        initTop();
        initRecycle();
        initsearch();
    }

    private void initsearch() {
        ecvSearch.setOnEnterListerner(() -> {
            monthModels.clear();
            refreshList();
            monthP.getData();
        });
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
        recyclerView.setAdapter(initAdapter());
        recyclerView.setLoadingListener(this);
    }

    private MonthAdapter initAdapter() {
        monthAdapter = new MonthAdapter(this, monthModels);
        monthAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {

            }
        });
        return monthAdapter;

    }


    @Override
    public String getFilter() {
        return ecvSearch.getText();
    }

    @Override
    public void refreshList() {
        monthAdapter.notifyDataSetChanged();
    }

    @Override
    public void addList(List<CustomerMonthB> list) {
        monthModels.addAll(list);
    }

    @Override
    public void stopLoadMore() {
        recyclerView.setLoadingMoreEnabled(false);
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

    }

    @Override
    public void onLoadMore() {
        monthP.getData();
    }

    @Override
    protected void onDestroy() {
        monthP.detachView();
        super.onDestroy();
    }
}
