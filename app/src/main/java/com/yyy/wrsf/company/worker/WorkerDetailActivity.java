package com.yyy.wrsf.company.worker;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.WorkerB;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkerDetailActivity extends BaseActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_name)
    EditClearView ecvName;
    @BindView(R.id.ecv_tel)
    EditClearView ecvTel;
    @BindView(R.id.ecv_petname)
    EditClearView ecvPetname;
    @BindView(R.id.ecv_sex)
    EditClearView ecvSex;
    @BindView(R.id.ecv_status)
    EditClearView ecvStatus;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.btn_add)
    TextView btnAdd;

    private WorkerB workerB;
    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {

    }
}
