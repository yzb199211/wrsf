package com.yyy.wrsf.mine;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yyy.wrsf.R;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.OnRightClickListener;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_data)
    EditClearView ecvData;
    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initTop();
        initData();
    }

    private void initData() {
        code = getIntent().getIntExtra("code", 0);
        ecvData.setText(getIntent().getStringExtra("data"));
    }

    private void initTop() {
        topView.setTitle(getIntent().getStringExtra("title"));
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
        topView.setOnRightClickListener(new OnRightClickListener() {
            @Override
            public void onRight() {
                setResult(code, new Intent().putExtra("data", ecvData.getText()));
                finish();
            }
        });
    }
}
