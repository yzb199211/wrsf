package com.yyy.wrsf.common.address;

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
import com.yyy.wrsf.beans.address.AreaB;
import com.yyy.wrsf.utils.PxUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.address.AddressUrl;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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
    private AreaB province;
    private AreaB city;
    private AreaB district;

    private List<AreaB> provinces = new ArrayList<>();
    private List<AreaB> citys = new ArrayList<>();
    private List<AreaB> districts = new ArrayList<>();
    private List<AreaB> showList = new ArrayList<>();
    private AreaAdapter areaAdapter;

    private SharedPreferencesHelper preferencesHelper;
    private OnBackAreaListener onBackAreaListener;

    public AreaSelect(Context context) {
        this(context, null, null, null);
    }

    public AreaSelect(Context context, AreaB province, AreaB city, AreaB district) {
        super(context);
        this.context = context;
        this.province = province;
        this.city = city;
        this.district = district;

        preferencesHelper = new SharedPreferencesHelper(context, context.getString(R.string.preferenceCache));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_area_select, null);
        setHeight(((Activity) context).getWindowManager().getDefaultDisplay().getHeight());
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
        switch (this.areaLevel) {
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
        } else if (province != null && city == null) {
            areaLevel = AreaLevel.CITY;
            getData(province.getId());
            tvProvince.setText(province.getAreaName());
            tvCity.setVisibility(View.VISIBLE);
        } else if (province != null && city != null && district == null) {
            areaLevel = AreaLevel.DISTRICT;
            getData(city.getId());
            tvProvince.setText(province.getAreaName());
            tvCity.setText(city.getAreaName());
            tvCity.setVisibility(View.VISIBLE);
            tvDistrict.setVisibility(View.VISIBLE);
        }
        else if (province != null && city != null && district != null) {
            areaLevel = AreaLevel.DISTRICT;
            getData(city.getId());
            tvProvince.setText(province.getAreaName());
            tvCity.setText(city.getAreaName());
            tvDistrict.setText(district.getAreaName());
            tvCity.setVisibility(View.VISIBLE);
            tvDistrict.setVisibility(View.VISIBLE);
        }
    }

    private void getData(int parentId) {
        showList.clear();
        refreshList();
        new NetUtil(areaParams(parentId), NetConfig.address + AddressUrl.getArea, RequstType.GET, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
//                Log.e("data", string);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        String data = result.getData();
                        if (!TextUtils.isEmpty(data)) {
                            List<AreaB> list = new Gson().fromJson(data, new TypeToken<List<AreaB>>() {
                            }.getType());
                            setList(list);
                        }
                    } else {
                        Log.e(AreaSelect.class.getName(), result.getMsg());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setList(List<AreaB> list) {
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
                districts.clear();
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
            areaLevel = AreaLevel.PROVINCE;
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
            areaLevel = AreaLevel.CITY;
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
            if (areaLevel != AreaLevel.DISTRICT) {
                areaLevel = AreaLevel.DISTRICT;
                showList.clear();
                showList.addAll(districts);
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
