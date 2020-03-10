package com.yyy.wrsf.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.AreaModel;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.net.NetUtil;

import java.util.ArrayList;
import java.util.List;

public class AreaSelect extends PopupWindow {
    View view;
    TextView tvProvince;
    TextView tvCity;
    TextView tvDistrict;
    View line;
    RecyclerView recyclerView;
    TextView currentView;

    Context context;
    AreaModel province;
    AreaModel city;
    AreaModel district;

    List<AreaModel> provinces = new ArrayList<>();
    List<AreaModel> citys = new ArrayList<>();
    List<AreaModel> districts = new ArrayList<>();
    List<AreaModel> showList = new ArrayList<>();
    AreaAdapter areaAdapter;

    SharedPreferencesHelper preferencesHelper;

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
        line = view.findViewById(R.id.line);
    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    private void refreshList() {
        if (areaAdapter == null) {
            areaAdapter = new AreaAdapter(context, showList);
            areaAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int pos) {

                }
            });
            recyclerView.setAdapter(areaAdapter);
        } else {
            areaAdapter.notifyDataSetChanged();
        }

    }

    private void initView() {
        if (province == null) {
            currentView = tvProvince;
            getData(0);
        } else {

        }
    }

    private void getData(int parentId) {
//        new NetUtil()
    }
}
