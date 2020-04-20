package com.yyy.wrsf.mine.shipping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.FileUtil;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShipProtocolActivity extends AppCompatActivity {

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
        topView.setOnLeftClickListener(() -> {
            finish();
        });
        topView.setOnRightClickListener(() -> {
            setResult(CodeUtil.CONFIRM, new Intent());
            finish();
        });
        tvContent.setText(Html.fromHtml(FileUtil.readToText2(("ship_protocol.html"))));
    }
}
