package com.yyy.wrsf.mine.outlets;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.BaseActivity;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnDialListener;
import com.yyy.wrsf.interfaces.PermissionListener;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.view.recycle.LoadingType;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutletsActivity extends BaseActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.refresh_list)
    XRecyclerView refreshList;

    private LinearLayoutManager manager;
    private LoadingType loadingType = LoadingType.NORMAL;
    private OutletsAdapter listAdapter;
    private int pageNo;
    private List<OutleModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlets);
        ButterKnife.bind(this);
        initList();
        intiView();
    }

    private void initList() {
        list = new ArrayList<>();
    }

    private void intiView() {
        initTop();
        initRefreshList();
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    private void initRefreshList() {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        refreshList.setLayoutManager(manager);
        refreshList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        refreshList.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        refreshList.setArrowImageView(R.mipmap.iconfont_downgrey);
        refreshList.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);

        refreshList.setLoadingMoreEnabled(true);
        refreshList.setLoadingListener(this);
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.address, RequstType.GET, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                refresh();
            }

            @Override
            public void onFail(IOException e) {
                String error = OutletsActivity.this.getString(R.string.error_net) + e.getMessage();
                Log.e(this.getClass().getName(), error);
                LoadingFinish(error);
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        return params;
    }

    private void refresh() {
        if (listAdapter == null) {
            refreshList.setAdapter(listAdapter);
            listAdapter.setOnDialListener(new OnDialListener() {
                @Override
                public void onDial(String string) {
                    callPermission(string);
                }
            });
        } else {
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        loadingType = LoadingType.REFRESH;

    }

    @Override
    public void onLoadMore() {
        loadingType = LoadingType.LOADINGMORE;

    }

    private void setListStatus() {
        switch (loadingType) {
            case NORMAL:
                break;
            case REFRESH:
                refreshList.refreshComplete();
                loadingType = LoadingType.NORMAL;
                break;
            case LOADINGMORE:
                refreshList.loadMoreComplete();
                loadingType = LoadingType.NORMAL;
                break;
            default:
                break;
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

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void callPermission(String string) {
        requestRunPermisssion(new String[]{Manifest.permission.CALL_PHONE}, new PermissionListener() {
            @Override
            public void onGranted() {
                call(string);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                Toast(getString(R.string.error_permission));
            }
        });
    }
}
