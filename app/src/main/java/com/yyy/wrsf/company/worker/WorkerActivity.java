package com.yyy.wrsf.company.worker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.WorkerB;
import com.yyy.wrsf.company.worker.persenter.IWorkerP;
import com.yyy.wrsf.company.worker.persenter.WorkerP;
import com.yyy.wrsf.company.worker.view.IWorkerV;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.TopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkerActivity extends BaseActivity implements IWorkerV {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_search)
    EditClearView ecvSearch;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    private WorkerAdapter adapter;
    private List<WorkerB> workerBS = new ArrayList<>();
    private IWorkerP workerP;

    private Integer showStop = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        ButterKnife.bind(this);
        workerP = new WorkerP(this);
        init();
    }

    private void init() {
        initTop();
        initRecycler();
        initSearch();
        workerP.getWorker();
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
        topView.setOnRightClickListener(() -> {
            if (showStop == null) {
                showStop = 0;
                topView.setRightText(getString(R.string.common_stop_invisiable));
                workerBS.clear();
                refreshList();
                workerP.getWorker();
            } else {
                showStop = null;
                topView.setRightText(getString(R.string.common_stop_visiable));
                workerBS.clear();
                refreshList();
                workerP.getWorker();
            }
        });
    }

    private void initSearch() {
        ecvSearch.setOnEnterListerner(() -> {
            workerBS.clear();
            refreshList();
            workerP.getWorker();
        });
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerView.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(initAdapter());
    }

    private WorkerAdapter initAdapter() {
        adapter = new WorkerAdapter(this, workerBS);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                go2Detail(pos);
            }
        });
        return adapter;
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        go2Detail(-1);
    }

    public void go2Detail(int pos) {
        startActivityForResult(
                new Intent()
                        .setClass(this, WorkerDetailActivity.class)
                        .putExtra("data", pos == -1 ? "" : new Gson().toJson(workerBS.get(pos)))
                        .putExtra("pos", pos)
                , CodeUtil.MODIFY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == CodeUtil.REFRESH) {
                workerBS.clear();
                refreshList();
                workerP.getWorker();
            } else if (resultCode == CodeUtil.MODIFY) {
                try {
                    workerBS.set(data.getIntExtra("pos", -1), new Gson().fromJson(data.getStringExtra("data"), WorkerB.class));
                    refreshList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CodeUtil.DELETE) {
                try {
                    remove(data.getIntExtra("pos", -1));
//                    workerBS.remove(workerBS.get(data.getIntExtra("pos", -1)));
//                    refreshList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Integer isShowStop() {
        return showStop;
    }

    @Override
    public Integer getRoleType() {
        return null;
    }

    @Override
    public String getFilter() {
        return ecvSearch.getText();
    }

    @Override
    public void refreshList() {
        adapter.notifyDataSetChanged();
    }

    public void remove(int pos) {
        workerBS.remove(pos);
        refreshList();
    }

    @Override
    public void addList(List<WorkerB> list) {
        workerBS.addAll(list);
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
