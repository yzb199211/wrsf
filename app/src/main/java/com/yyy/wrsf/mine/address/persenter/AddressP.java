package com.yyy.wrsf.mine.address.persenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.beans.address.AddressB;
import com.yyy.wrsf.beans.filter.AddressFilterB;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.address.model.AddressM;
import com.yyy.wrsf.mine.address.view.IAddressV;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class AddressP implements IAddressP {
    private IAddressV iAddressV;
    private AddressM addressM;
    private Handler handler = new Handler();
    private boolean destroyFlag;
    private PagerRequestBean<AddressFilterB> pager;
    private SharedPreferencesHelper preferencesHelper = new SharedPreferencesHelper(BaseApplication.getInstance().getApplicationContext(), BaseApplication.getInstance().getApplicationContext().getString(R.string.preferenceCache));

    public AddressP(IAddressV iAddressV) {
        this.iAddressV = iAddressV;
        addressM = new AddressM();
        initPagerData();
    }

    private void initPagerData() {
        pager = new PagerRequestBean();
        pager.setPageIndex(0);
        pager.setPageSize(500);
        AddressFilterB filterModel = new AddressFilterB();
        filterModel.setRecNo((Integer) preferencesHelper.getSharedPreference("recNo", 0));
        pager.setQueryParam(filterModel);
    }

    @Override
    public void getData() {
        iAddressV.startLoading();
        addressM.Requset(getParams(), NetConfig.address + iAddressV.getUrl(), RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iAddressV.finishLoading(null);
                        List<AddressB> list = new Gson().fromJson(data, new TypeToken<List<AddressB>>() {
                        }.getType());
                        if (list != null && list.size() > 0) {
                            iAddressV.addList(list);
                            iAddressV.refreshList();
                        }
                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iAddressV.finishLoading(error);
                    });
                }
            }
        });
    }

    @Override
    public void delete(int id, int pos) {
        addressM.Requset(deleteParams(id), NetConfig.address + iAddressV.deleteUrl(), RequstType.DELETE, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iAddressV.finishLoading(null);
                        iAddressV.delete(pos);
                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iAddressV.finishLoading(error);
                    });
                }
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("platMemberRecaddBo", new Gson().toJson(pager)));
        return params;
    }

    private List<NetParams> deleteParams(int id) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("recNo", id + ""));
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.iAddressV = null;
    }
}
