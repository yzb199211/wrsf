package com.yyy.wrsf.mine.month;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseFragment;
import com.yyy.wrsf.beans.month.MonthB;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.mine.month.persenter.MonthApplyP;
import com.yyy.wrsf.mine.month.view.IMonthApplyV;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MonthFragment extends BaseFragment implements IMonthApplyV, XRecyclerView.LoadingListener {
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    private int status;
    private List<MonthB> list = new ArrayList<>();
    private MonthAdapter monthAdapter;
    private MonthApplyP monthApplyP;

    public MonthFragment(int status) {
        this.status = status;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        monthApplyP = new MonthApplyP(this);
        monthApplyP.getData(0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        initAdapter();
        initRecycle();
    }


    private void initAdapter() {
        monthAdapter = new MonthAdapter(getActivity(), list);
        monthAdapter.setOnItemClickListener(pos -> {
            go2Detail(pos);
        });
    }

    private void go2Detail(int pos) {
        startActivityForResult(new Intent()
                .setClass(getActivity(), MonthApplyModifyActivity.class)
                .putExtra("pos",pos)
                .putExtra("data",new Gson().toJson(list.get(pos)))
                , CodeUtil.MODIFY);
    }

    private void initRecycle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerView.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setLoadingListener(this);
        recyclerView.setAdapter(monthAdapter);
    }

    @Override
    public void forbidLoad(boolean b) {
        recyclerView.setLoadingMoreEnabled(b);
    }

    @Override
    public void refreshList() {
        monthAdapter.notifyDataSetChanged();
    }

    @Override
    public void setList(List<MonthB> list) {
        this.list.addAll(list);
        refreshList();
    }

    @Override
    public int getStatus() {
        return status;
    }



    @Override
    public void startLoading() {
        LoadingDialog.showDialogForLoading(getActivity());
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
    public void onDestroy() {
        monthApplyP.detachView();
        super.onDestroy();
    }
    @Override
    public void stopLoad() {
        recyclerView.loadMoreComplete();
    }

    @Override
    public void stopRefresh() {
        recyclerView.refreshComplete();
    }
    @Override
    public void onRefresh() {
        list.clear();
        refreshList();
        monthApplyP.resetPageIndex();
        forbidLoad(true);
        monthApplyP.getData(1);
    }

    @Override
    public void onLoadMore() {
        monthApplyP.getData(2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
