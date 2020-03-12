package com.yyy.wrsf.common;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.AreaModel;
import com.yyy.wrsf.utils.PxUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.utils.net.Result;
import com.yyy.wrsf.utils.net.address.AddressUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class AreaSelect extends PopupWindow implements View.OnClickListener {
    private View view;
    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvDistrict;
    private ImageView ivClose;
    private View line;
    private RecyclerView recyclerView;
    private AreaLevel areaLevel;

    private Context context;
    private AreaModel province;
    private AreaModel city;
    private AreaModel district;

    private List<AreaModel> provinces = new ArrayList<>();
    private List<AreaModel> citys = new ArrayList<>();
    private List<AreaModel> districts = new ArrayList<>();
    private List<AreaModel> showList = new ArrayList<>();
    private AreaAdapter areaAdapter;

    private SharedPreferencesHelper preferencesHelper;
    OnBackAreaListener onBackAreaListener;

    public AreaSelect(Context context) {
        this(context, null, null, null);
    }

    public AreaSelect(Context context, AreaModel province, AreaModel city, AreaModel district) {
        super(context);
        this.context = context;
        this.province = province;
        this.city = city;
        this.district = district;

        preferencesHelper = new SharedPreferencesHelper(context, context.getString(R.string.preferenceCache));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_area_select, null);
        setHeight(PxUtil.getHeight(context));
        setWidth(PxUtil.getWidth(context));
        setContentView(view);
        init();
    }

    private void init() {
        bindView();
        initList();
        initView();
    }

    private void bindView() {
        tvProvince = view.findViewById(R.id.tv_province);
        tvCity = view.findViewById(R.id.tv_city);
        tvDistrict = view.findViewById(R.id.tv_district);
        recyclerView = view.findViewById(R.id.recycler_view);
        ivClose = view.findViewById(R.id.iv_close);
        line = view.findViewById(R.id.line);
        tvProvince.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        tvDistrict.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private void refreshList() {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (areaAdapter == null) {
                    areaAdapter = new AreaAdapter(context, showList);
                    areaAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            initClick(pos);
                        }
                    });
                    recyclerView.setAdapter(areaAdapter);
                } else {
                    areaAdapter.notifyDataSetChanged();
                }
            }
        });


    }

    private void initClick(int pos) {
        switch (areaLevel) {
            case PROVINCE:
                province = provinces.get(pos);
                tvProvince.setText(province.getAreaName());
                tvCity.setVisibility(View.VISIBLE);
                areaLevel = AreaLevel.CITY;
                getData(province.getId());
                clearCity();
                break;
            case CITY:
                city = citys.get(pos);
                tvCity.setText(city.getAreaName());
                tvDistrict.setVisibility(View.VISIBLE);
                areaLevel = AreaLevel.DISTRICT;
                getData(city.getId());
                clearDistrict();
                break;
            case DISTRICT:
                district = districts.get(pos);
                tvDistrict.setText(district.getAreaName());
                this.dismiss();
                break;
            default:
                break;
        }
    }

    private void initView() {
        if (province == null) {
            areaLevel = AreaLevel.PROVINCE;
            getData(0);
        } else {

        }
    }

    private void getData(int parentId) {
        showList.clear();
        refreshList();
        new NetUtil(areaParams(parentId), NetConfig.address + AddressUrl.getArea, RequstType.GET, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("data", string);
                Result result = new Gson().fromJson(string, Result.class);
                if (result.isSuccess()) {
                    String data = new Gson().toJson(result.getData());
                    if (!TextUtils.isEmpty(data)) {
                        List<AreaModel> list = new Gson().fromJson(data, new TypeToken<List<AreaModel>>() {
                        }.getType());
                        setList(list);
                    }
                } else {
                    Log.e(AreaSelect.class.getName(), result.getMsg());
                }
            }

            @Override
            public void onFail(IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setList(List<AreaModel> list) {
        switch (areaLevel) {
            case PROVINCE:
//                clearProvince();
                provinces.addAll(list);
                showList.addAll(list);
                refreshList();
                break;
            case CITY:
//                clearCity();
                citys.addAll(list);
                showList.addAll(list);
                refreshList();
                break;
            case DISTRICT:
//                clearDistrict();
                districts.addAll(list);
                showList.addAll(list);
                refreshList();
                break;
            default:
                break;
        }
    }


    private void clearProvince() {
        provinces.clear();
        clearCity();
        clearDistrict();
    }

    private void clearCity() {
        city = null;
        citys.clear();
        tvCity.setText("");
        tvDistrict.setVisibility(View.GONE);
        clearDistrict();
    }

    private void clearDistrict() {
        district = null;
        districts.clear();
        tvDistrict.setText("");
    }

    private List<NetParams> areaParams(int parentId) {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("cityId", parentId + ""));
        return params;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                AreaSelect.this.dismiss();
                break;
            case R.id.tv_province:
                clickProvince();
                break;
            case R.id.tv_city:
                clickCity();
                break;
            case R.id.tv_district:
                clickDistrict();
                break;
            default:
                break;
        }
    }

    private void clickProvince() {
        if (provinces.size() == 0) {
            getData(0);
        } else {
            if (areaLevel != AreaLevel.PROVINCE) {
                areaLevel = AreaLevel.PROVINCE;
                showList.clear();
                showList.addAll(provinces);
                refreshList();
            }
        }
    }

    private void clickCity() {
        if (citys.size() == 0) {
            getData(province.getId());
        } else {
            if (areaLevel != AreaLevel.CITY) {
                areaLevel = AreaLevel.CITY;
                showList.clear();
                showList.addAll(citys);
                refreshList();
            }
        }
    }

    private void clickDistrict() {
        if (districts.size() == 0) {
            getData(city.getId());
        } else {
            if (areaLevel != AreaLevel.CITY) {
                areaLevel = AreaLevel.DISTRICT;
                showList.clear();
                showList.addAll(citys);
                refreshList();
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (onBackAreaListener != null) {
            onBackAreaListener.backArea(province, city, district);
        }
    }

    public void setOnBackAreaListener(OnBackAreaListener onBackAreaListener) {
        this.onBackAreaListener = onBackAreaListener;
    }
}
