package com.yyy.wrsf.mine.address;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnDeleteListener;
import com.yyy.wrsf.interfaces.OnEditListener;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.AddressModel;
import com.yyy.wrsf.model.filter.AddressFilterModel;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.utils.net.address.AddressUrl;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<AddressModel> list = new ArrayList<>();
    private AddressAdapter addressAdapter;

    private boolean isSelect = false;
    private PagerRequestBean pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        initDefaultData();
        initView();
        getData();
    }

    private void initDefaultData() {
        initPagerData();
        isSelect = getIntent().getBooleanExtra("isSelect", false);
    }

    private void initPagerData() {
        pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(500);
    }

    private void getData() {
        new NetUtil(getParams(), NetConfig.address + AddressUrl.getAddressList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(AddressActivity.this.getClass().getName(), "data:" + string);
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
//        pager.setQueryParam(new AddressFilterModel());
        params.add(new NetParams("bean", new Gson().toJson(pager)));
        return params;
    }

    private void refrishList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (addressAdapter == null) {
                    initAdapter();
                } else {
                    addressAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void initAdapter() {
        addressAdapter = new AddressAdapter(this, list);
        recyclerView.setAdapter(addressAdapter);
        addressAdapter.setOnDeleteListener(new OnDeleteListener() {
            @Override
            public void onDelete(int pos) {

            }
        });
        addressAdapter.setOnEditListener(new OnEditListener() {
            @Override
            public void onEdit(int pos) {

            }
        });
        if (isSelect)
            addressAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {

                }
            });

    }

    private void initView() {
        initTop();
        initRecycle();
    }

    private void initRecycle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        startActivityForResult(new Intent().setClass(this, AddressDetailActivity.class), CodeUtil.ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
