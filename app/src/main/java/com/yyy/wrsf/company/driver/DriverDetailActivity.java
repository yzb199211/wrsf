package com.yyy.wrsf.company.driver;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.yyy.wrsf.R;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.TopView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DriverDetailActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_name)
    EditClearView ecvName;
    @BindView(R.id.ecv_tel)
    EditClearView ecvTel;
    @BindView(R.id.ecv_license)
    EditClearView ecvLicense;
    @BindView(R.id.ecv_sex)
    EditClearView ecvSex;
    @BindView(R.id.ecv_type)
    EditClearView ecvType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_detail);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.ecv_sex, R.id.ecv_type, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ecv_sex:
                break;
            case R.id.ecv_type:
                break;
            case R.id.btn_confirm:
                break;
            default:
                break;
        }
    }
}
