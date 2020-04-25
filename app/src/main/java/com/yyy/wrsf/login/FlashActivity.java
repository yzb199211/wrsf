package com.yyy.wrsf.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.yyy.wrsf.company.bill.CompanyBillActivity;
import com.yyy.wrsf.main.MainActivity;
import com.yyy.wrsf.R;
import com.yyy.wrsf.mine.pay.PayActivity;

public class FlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        new Handler().postDelayed(r, 1000);// 1秒后关闭，并跳转到主页面
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(FlashActivity.this, PayActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
