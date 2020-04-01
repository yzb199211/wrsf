package com.yyy.wrsf.common.company;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.model.company.CompanyB;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.view.topview.TopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private SharedPreferencesHelper preferencesHelper;

    private List<CompanyB> companys = new ArrayList<>();
    private CompanyAdapter adapter;

    private int sendRec;
    private int receiveRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
        getData();
    }


    private void init() {
        initTop();
        initRecycle();
        initData();
    }


    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initRecycle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {

    }

    private void getData() {
    }
}
