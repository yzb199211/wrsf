package com.yyy.wrsf.mine.address.persenter;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.beans.filter.AddressFilterB;
import com.yyy.wrsf.mine.address.model.AddressM;
import com.yyy.wrsf.mine.address.view.IAddressV;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;

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

//        String s = new Gson().toJson(filterModel);
    }

    @Override
    public void getData() {

    }

    @Override
    public void delete() {

    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("platMemberRecaddBo", new Gson().toJson(pager)));
        return params;
    }
}
