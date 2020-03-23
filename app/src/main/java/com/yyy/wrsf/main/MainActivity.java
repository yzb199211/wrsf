package com.yyy.wrsf.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.yyy.wrsf.R;
import com.yyy.wrsf.common.AreaSelect;
import com.yyy.wrsf.company.CompanyRegisterActivity;
import com.yyy.wrsf.mine.address.AddressActivity;
import com.yyy.wrsf.mine.notice.NoticeFragment;
import com.yyy.wrsf.mine.shipping.ShippingActivity;
import com.yyy.wrsf.view.cycle.bean.DataBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.iv_main)
    ImageView ivMain;
    @BindView(R.id.tv_main)
    TextView tvMain;
    @BindView(R.id.iv_send)
    ImageView ivSend;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.iv_notice)
    ImageView ivNotice;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.iv_company)
    ImageView ivCompany;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.iv_mine)
    ImageView ivMine;
    @BindView(R.id.tv_mine)
    TextView tvMine;

    MainFragment mainFragment;
    MineFragment mineFragment;
    NoticeFragment noticeFragment;
    CompanyFragment companyFragment;
    Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        mainFragment = new MainFragment();
        currentFragment = mainFragment;
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction
                .add(R.id.fl_content, mainFragment)
                .commit();
    }

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.fl_content, targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }


    @OnClick({R.id.rl_main, R.id.rl_send, R.id.rl_notice, R.id.rl_company, R.id.rl_mine, R.id.ll_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_main:
                switchFragment(mainFragment);
                break;
            case R.id.rl_send:

                break;
            case R.id.rl_notice:
                if (noticeFragment == null) {
                    noticeFragment = new NoticeFragment();
                }
                switchFragment(noticeFragment);
                EventBus.getDefault().post("");
                break;
            case R.id.rl_company:
//                go2Company();
                if (companyFragment == null) {
                    companyFragment = new CompanyFragment();
                }
                switchFragment(companyFragment);
                break;
            case R.id.rl_mine:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                switchFragment(mineFragment);
                break;
            case R.id.ll_send:
                go2Shipping();
                break;
            default:
                break;
        }
    }

    private void go2Shipping() {
        startActivity(new Intent().setClass(this, ShippingActivity.class));
    }

    private void go2Company() {
        startActivity(new Intent().setClass(this, CompanyRegisterActivity.class));
    }
}
