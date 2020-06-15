package com.yyy.wrsf.mine.shipping;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yyy.wrsf.R;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.FileUtil;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShipPayNoticeActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_protocol);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topView.setTitle(getString(R.string.order_pay_notice));
        topView.setOnLeftClickListener(() -> {
            finish();
        });
        topView.setOnRightClickListener(() -> {
            setResult(CodeUtil.CONFIRM, new Intent());
            finish();
        });
        tvContent.setText(Html.fromHtml(FileUtil.readToText2(("pay_notice.html"))));
    }
}
