package com.yyy.wrsf.mine.addvalue;

import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yyy.wrsf.R;
import com.yyy.wrsf.utils.FileUtil;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddValueActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_value);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
        tvContent.setText(Html.fromHtml(FileUtil.readToText(("add_value.html"))));
    }

}
