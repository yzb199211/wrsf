package com.yyy.wrsf.company.bill;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyBillActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.tv_bill)
    RadioButton tvBill;
    @BindView(R.id.tv_total)
    RadioButton tvTotal;

    private RadioButton current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_bill);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initView();

    }

    private void initView() {
        initCheckBill();
        initTotalBill();
    }

    private void initCheckBill() {
        Drawable drawable = getResources().getDrawable(R.drawable.radio_bill);//checkbox点击效果selector
        drawable.setBounds(0, getResources().getDimensionPixelOffset(R.dimen.dp_10), getResources().getDimensionPixelOffset(R.dimen.dp_18), getResources().getDimensionPixelOffset(R.dimen.dp_28));//设置大小  ，分别表示x ,y 宽，高
        tvBill.setCompoundDrawables(null, drawable, null, null);//选择位置上下左右
        tvBill.setOnCheckedChangeListener(this);
        current = tvBill;
    }

    private void initTotalBill() {
        Drawable drawable1 = getResources().getDrawable(R.drawable.radio_total);//checkbox点击效果selector
        drawable1.setBounds(0, getResources().getDimensionPixelOffset(R.dimen.dp_10), getResources().getDimensionPixelOffset(R.dimen.dp_15), getResources().getDimensionPixelOffset(R.dimen.dp_25));//设置大小  ，分别表示x ,y 宽，高
        tvTotal.setCompoundDrawables(null, drawable1, null, null);//选择位置上下左右
        tvTotal.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            current.setChecked(false);
            current.setTextColor(getColor(R.color.text_gray2));
            current = (RadioButton) compoundButton;
            current.setTextColor(getColor(R.color.default_bg_gray));
        }
    }
}
