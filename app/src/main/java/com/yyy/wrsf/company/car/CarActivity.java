package com.yyy.wrsf.company.car;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.company.driver.DriverActivity;
import com.yyy.wrsf.company.driver.DriverAdapter;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.mine.notice.NoticeFragment;
import com.yyy.wrsf.model.CarModel;
import com.yyy.wrsf.model.DriverModel;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.utils.net.Result;
import com.yyy.wrsf.utils.net.car.CarUrl;
import com.yyy.wrsf.utils.net.driver.DriverUrl;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    private PagerRequestBean pager;
    private List<CarModel> cars = new ArrayList<>();
    private CarAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        init();
        getData();
    }

    private void init() {
        initTop();
        initRecycle();
        initPager();
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
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

    private void initPager() {
        pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(500);
    }

    private void getData() {
        new NetUtil(getParams(), NetConfig.address + CarUrl.getCarList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(this.getClass().getName(), "data:" + string);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<CarModel> list = new Gson().fromJson(result.getData(), new TypeToken<List<CarModel>>() {
                        }.getType());
                        if (list != null) {
                            cars.clear();
                            cars.addAll(list);
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
                if (carAdapter == null) {
                    carAdapter = new CarAdapter(CarActivity.this, cars);
                    recyclerView.setAdapter(carAdapter);
                    carAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            go2detail(CodeUtil.MODIFY, pos);
                        }
                    });
                } else {
                    carAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(pager)));
        return params;
    }

    private void go2detail(int code, int pos) {
        Intent intent = new Intent();
        intent.setClass(this, CarDetailActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("pos", pos);
        if (pos != -1) {
            intent.putExtra("data", new Gson().toJson(cars.get(pos)));
        }
        startActivityForResult(intent, code);
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        go2detail(CodeUtil.ADD, -1);

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
