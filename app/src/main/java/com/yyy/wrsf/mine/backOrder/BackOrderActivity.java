package com.yyy.wrsf.mine.backOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.utils.FileUtil;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BackOrderActivity extends BaseActivity {
    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
        tvContent.setText(Html.fromHtml(FileUtil.readToText(("back_order.html"))));
    }
}
