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
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.address.AddressB;
import com.yyy.wrsf.beans.filter.AddressFilterB;
import com.yyy.wrsf.dialog.JudgeDialog;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnDeleteListener;
import com.yyy.wrsf.interfaces.OnEditListener;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.address.AddressUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressReceiveActivity extends BaseActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    SharedPreferencesHelper preferencesHelper;

    private List<AddressB> addresses = new ArrayList<>();
    private AddressAdapter addressAdapter;

    private boolean isSelect = false;
    private PagerRequestBean<AddressFilterB> pager;
    private int memberId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address1);
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
        AddressFilterB filterModel = new AddressFilterB();
        filterModel.setRecNo(memberId);
        pager.setQueryParam(filterModel);

//        String s = new Gson().toJson(filterModel);
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.address + AddressUrl.getAddressList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(AddressReceiveActivity.this.getClass().getName(), "data:" + string);
                try {
                    LoadingFinish(null);
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<AddressB> list = new Gson().fromJson(result.getData(), new TypeToken<List<AddressB>>() {
                        }.getType());
                        if (list != null) {
                            addresses.clear();
                            addresses.addAll(list);
                            refrishList();
                        }
                    } else {
                        LoadingFinish(result.getMsg());
                        Log.e(AddressReceiveActivity.class.getName(), result.getMsg());
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
                                .setClass(AddressReceiveActivity.this, AddressDetailReceiveActivity.class)
                                .putExtra("data", new Gson().toJson(addresses.get(pos)))
                                .putExtra("pos", pos)
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
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(deleteParams(pos), NetConfig.address + AddressUrl.deleteAddress, RequstType.DELETE, new ResponseListener() {
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
        topView.setTitle(getString(R.string.address_title_receive));
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        startActivityForResult(new Intent()
                        .setClass(this, AddressDetailReceiveActivity.class)
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
                    AddressB item = new Gson().fromJson(data.getStringExtra("data"), AddressB.class);
                    if (addresses.get(pos).getIsDefault() != 1 && item.getIsDefault() == 1) {
                        resetDefault();
                    }
                    addresses.set(pos, item);
//                    addresses.get(pos) = ;

                    refrishList();
                }
            }
        }
    }

    private void resetDefault() {
        for (AddressB item : addresses) {
            if (item.getIsDefault() == 1) {
                item.setIsDefault(0);
                return;
            }
        }
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
