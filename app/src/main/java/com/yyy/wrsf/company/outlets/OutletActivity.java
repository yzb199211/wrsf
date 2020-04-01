package com.yyy.wrsf.company.outlets;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.mine.notice.NoticeFragment;
import com.yyy.wrsf.model.OutletB;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.outlet.OutleUrl;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutletActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    private List<OutletB> outletModels = new ArrayList<>();
    private OutletAdapter outletAdapter;
    private PagerRequestBean pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        ButterKnife.bind(this);
        init();
        getData();
    }

    private void init() {
        initTop();
        initRecycle();
        initPager();
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.address + OutleUrl.getPageList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    LoadingFinish(null);
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<OutletB> list = new Gson().fromJson(result.getData(), new TypeToken<List<OutletB>>() {
                        }.getType());
                        if (list != null) {
                            outletModels.clear();
                            outletModels.addAll(list);
                            refrishList();
                        }
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(NoticeFragment.class.getName(), result.getMsg());
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (outletAdapter == null) {
                    outletAdapter = new OutletAdapter(OutletActivity.this, outletModels);
                    recyclerView.setAdapter(outletAdapter);
                    outletAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            go2detail(CodeUtil.MODIFY, pos);
                        }
                    });
                } else {
                    outletAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void go2detail(int modify, int pos) {
        startActivityForResult(
                new Intent()
                        .setClass(this, OutletDetailActivity.class)
                        .putExtra("pos",pos)
                        .putExtra("data", new Gson().toJson(outletModels.get(pos)))
                , CodeUtil.MODIFY);

    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(pager)));
        return params;
    }

    private void initRecycle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerView.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
    }

    private void initPager() {
        pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(500);
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    private void LoadingFinish(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (StringUtil.isNotEmpty(msg)) {
                    Toast(msg);
                }
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private void Toast(String msg) {
        Toasts.showShort(this, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == CodeUtil.MODIFY) {
            int pos = data.getIntExtra("pos", -1);
            if (pos > -1 && pos < outletModels.size()) {
                outletModels.set(pos, new Gson().fromJson(data.getStringExtra("data"), OutletB.class));
//                    addresses.get(pos) = ;
                refrishList();
            }
        }
    }
}
