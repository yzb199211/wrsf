package com.yyy.wrsf.mine.address;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.JudgeDialog;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnDeleteListener;
import com.yyy.wrsf.interfaces.OnEditListener;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.AddressModel;
import com.yyy.wrsf.model.filter.AddressFilterModel;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.utils.net.Result;
import com.yyy.wrsf.utils.net.address.AddressUrl;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressSendActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    SharedPreferencesHelper preferencesHelper;

    private List<AddressModel> addresses = new ArrayList<>();
    private AddressAdapter addressAdapter;

    private boolean isSelect = false;
    private PagerRequestBean<AddressFilterModel> pager;
    private int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        initDefaultData();
        initView();
        getData();
    }

    private void initDefaultData() {
        isSelect = getIntent().getBooleanExtra("isSelect", false);
        memberId = (Integer) preferencesHelper.getSharedPreference("recNo", 0);
        initPagerData();
    }

    private void initPagerData() {
        pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(500);
        AddressFilterModel filterModel = new AddressFilterModel();
        filterModel.setRecNo(memberId);
        pager.setQueryParam(filterModel);

//        String s = new Gson().toJson(filterModel);
    }

    private void getData() {

        new NetUtil(getParams(), NetConfig.address + AddressUrl.getAddressSendList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(AddressSendActivity.this.getClass().getName(), "data:" + string);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<AddressModel> list = new Gson().fromJson(result.getData(), new TypeToken<List<AddressModel>>() {
                        }.getType());
                        if (list != null) {
                            addresses.clear();
                            addresses.addAll(list);
                            refrishList();
                        }
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(AddressSendActivity.class.getName(), result.getMsg());
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

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("platMemberRecaddBo", new Gson().toJson(pager)));
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

    JudgeDialog deleteDialog;

    private void initAdapter() {
        addressAdapter = new AddressAdapter(this, addresses);
        recyclerView.setAdapter(addressAdapter);
        addressAdapter.setOnDeleteListener(new OnDeleteListener() {
            @Override
            public void onDelete(int pos) {
                showDeleteDialog(pos);
            }
        });
        addressAdapter.setOnEditListener(new OnEditListener() {
            @Override
            public void onEdit(int pos) {
                startActivityForResult(new Intent()
                                .setClass(AddressSendActivity.this, AddressDetailActivity.class)
                                .putExtra("data", new Gson().toJson(addresses.get(pos)))
                                .putExtra("code", CodeUtil.MODIFY)
                        , CodeUtil.MODIFY);
            }
        });
        if (isSelect)
            addressAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {
                    setResult(CodeUtil.Address, new Intent().putExtra("data", new Gson().toJson(addresses.get(pos))));
                    finish();
                }
            });

    }

    private void showDeleteDialog(int pos) {
        if (deleteDialog == null) {
            deleteDialog = new JudgeDialog(this);
        }
        deleteDialog.setOnCloseListener(new JudgeDialog.OnCloseListener() {
            @Override
            public void onClick(boolean confirm) {
                if (confirm) {
                    delete(pos);
                }
            }
        });
        deleteDialog.show();
    }

    private void delete(int pos) {
        new NetUtil(deleteParams(pos), NetConfig.address + AddressUrl.deleteAddressSend, RequstType.DELETE, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("data", string);
                try {
                    Result result = new Result(string);
                    LoadingFinish(null);
                    if (result.isSuccess()) {
                        addresses.remove(pos);
                        refrishList();
                    } else {
                        LoadingFinish(result.getMsg());
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

    private List<NetParams> deleteParams(int pos) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("recNo", addresses.get(pos).getRecNo() + ""));
        return params;
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
        startActivityForResult(new Intent()
                        .setClass(this, AddressDetailSendActivity.class)
                        .putExtra("code", CodeUtil.ADD)
                , CodeUtil.ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CodeUtil.ADD) {
            addresses.clear();
            refrishList();
            getData();
        } else if (resultCode == CodeUtil.MODIFY) {
            if (data != null) {
                int pos = data.getIntExtra("pos", -1);
                if (pos > -1 && pos < addresses.size()) {
                    addresses.set(pos, new Gson().fromJson(data.getStringExtra("data"), AddressModel.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (deleteDialog != null && deleteDialog.isShowing()) {
            deleteDialog.dismiss();
            deleteDialog = null;
        }
    }
}