package com.yyy.wrsf.mine.shipping;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShippingRemarkActivity extends BaseActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.et_remark)
    EditText etRemark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_remark);
        ButterKnife.bind(this);
        initTop();
        initRemark();
    }

    private void initRemark() {
        etRemark.setText(TextUtils.isEmpty(getIntent().getStringExtra("data")) ? "" : getIntent().getStringExtra("data"));
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        setResult(CodeUtil.ADD, new Intent().putExtra("data", etRemark.getText().toString()));
        finish();
    }
}
