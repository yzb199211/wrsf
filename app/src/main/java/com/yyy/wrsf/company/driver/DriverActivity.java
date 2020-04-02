package com.yyy.wrsf.company.driver;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.beans.filter.DriverFilterB;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.mine.notice.NoticeFragment;
import com.yyy.wrsf.beans.DriverB;
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
import com.yyy.wrsf.utils.net.driver.DriverUrl;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    @BindView(R.id.ecv_search)
    EditClearView ecvSearch;

    private PagerRequestBean pager;
    private List<DriverB> driverModels = new ArrayList<>();
    private DriverAdapter driverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        ButterKnife.bind(this);
        init();
        getData();
    }


    private void init() {
        initTop();
        initRecycle();
        initPager();
        initSearch();
    }

    private void initSearch() {
        ecvSearch.setOnEnterListerner(() -> {
            driverModels.clear();
            refrishList();
            getData();
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
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));

    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    private void initPager() {
        pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(500);
    }

    private void getData() {
        new NetUtil(getParams(), NetConfig.address + DriverUrl.getDriver, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(this.getClass().getName(), "data:" + string);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<DriverB> list = new Gson().fromJson(result.getData(), new TypeToken<List<DriverB>>() {
                        }.getType());
                        if (list != null) {
                            driverModels.clear();
                            driverModels.addAll(list);
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
        setPager();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (driverAdapter == null) {
                    driverAdapter = new DriverAdapter(DriverActivity.this, driverModels);
                    recyclerView.setAdapter(driverAdapter);
                    driverAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            go2detail(CodeUtil.MODIFY, pos);
                        }
                    });
                } else {
                    driverAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setPager() {
        pager.setQueryParam(getFilter(ecvSearch.getText()));
    }

    public DriverFilterB getFilter(String tel) {
        DriverFilterB driverFilterB = new DriverFilterB();
        if (!TextUtils.isEmpty(tel)) {
            driverFilterB.setDriverTel(tel);
            return driverFilterB;
        }
        return null;
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(pager)));
        return params;
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        go2detail(CodeUtil.ADD, -1);
    }

    private void go2detail(int code, int pos) {
        Intent intent = new Intent();
        intent.setClass(this, DriverDetailActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("pos", pos);
        if (pos != -1) {
            intent.putExtra("data", new Gson().toJson(driverModels.get(pos)));
        }
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CodeUtil.ADD) {
            driverModels.clear();
            refrishList();
            getData();
        } else if (resultCode == CodeUtil.MODIFY) {
            if (data != null) {
                int pos = data.getIntExtra("pos", -1);
                if (pos > -1 && pos < driverModels.size()) {
                    driverModels.set(pos, new Gson().fromJson(data.getStringExtra("data"), DriverB.class));
//                    addresses.get(pos) = ;
                    refrishList();
                }
            }
        }
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
}
