package com.yyy.wrsf.mine.bill;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.base.BasePickActivity;
import com.yyy.wrsf.mine.bill.adapter.BillMonthAdapter;
import com.yyy.wrsf.utils.DateUtil;
import com.yyy.wrsf.utils.TimeUtil;
import com.yyy.wrsf.view.recycle.RecyclerViewDivider;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.TimePickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnTimeSelectListener;
import com.yyy.yyylibrary.pick.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillMonthActivity extends BasePickActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_unpay)
    TextView tvUnpay;
    private BillMonthAdapter adapter;
    private TimePickerView pvDate;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_month);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initTop();
        initRecycle();
        initDate();
    }

    private void initDate() {
        date = DateUtil.getYearAndMonth(null);
        tvMonth.setText(date + getString(R.string.common_select_img));
    }


    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initRecycle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayout.VERTICAL));
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        recyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        recyclerView.getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
//        recyclerView.setLoadingListener(this);
        recyclerView.setAdapter(initAdapter());
    }

    private BillMonthAdapter initAdapter() {
        adapter = new BillMonthAdapter(this, null);
        adapter.setOnItemClickListener(pos -> {
        });
        adapter.setOnBillApplyListener(pos -> {
        });
        adapter.setOnPayListener(pos -> {
        });
        return adapter;
    }

    @OnClick(R.id.tv_month)
    public void onViewClicked() {
        if (pvDate == null) {
            try {
                initPvDate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pvDate.show();
    }

    private void initPvDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarLast = Calendar.getInstance();
        calendarLast.add(Calendar.YEAR, -3);
        pvDate = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                BillMonthActivity.this.date = DateUtil.getYearAndMonth(date);
                tvMonth.setText(BillMonthActivity.this.date + getString(R.string.common_select_img));
            }
        }).setRangDate(calendarLast, calendar)
                .setDate(calendar)
                .setType(new boolean[]{true, true, false, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setContentTextSize(18)
                .setBgColor(0xFFFFFFFF)
                .build();
        setDialog(pvDate);
        initDialogWindow(pvDate.getDialog().getWindow());
        pvDate.show();
    }
}
