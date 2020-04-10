package com.yyy.wrsf.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.company.CompanyRegisterActivity;
import com.yyy.wrsf.main.persenter.MainP;
import com.yyy.wrsf.main.view.IMainV;
import com.yyy.wrsf.mine.notice.NoticeFragment;
import com.yyy.wrsf.mine.shipping.ShippingActivity;
import com.yyy.wrsf.utils.SharedPreferencesHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainV {

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

    private MainFragment mainFragment;
    private MineFragment mineFragment;
    private NoticeFragment noticeFragment;
    private CompanyFragment companyFragment;
    private Fragment currentFragment;

    private String role;
    private MainP mainP;

    private ImageView currentIv;
    private TextView currentTv;
    private int currentSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainP = new MainP(this);
        initData();
        setDefaultFragment();
    }

    private void initData() {
        role = (String) preferencesHelper.getSharedPreference("authority", "");
    }

    private void setDefaultFragment() {
        setCheck(ivMain, tvMain, R.mipmap.menu_main_checked, R.mipmap.menu_main);
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
                setNormal();
                setCheck(ivMain, tvMain, R.mipmap.menu_main_checked, R.mipmap.menu_main);
                switchFragment(mainFragment);
                break;
            case R.id.rl_send:
                break;
            case R.id.rl_notice:
                setNormal();
                setCheck(ivNotice, tvNotice, R.mipmap.menu_notice_checked, R.mipmap.menu_notice);
                if (noticeFragment == null) {
                    noticeFragment = new NoticeFragment();
                }
                switchFragment(noticeFragment);
                EventBus.getDefault().post("");
                break;
            case R.id.rl_company:
                mainP.judgeCompany();
                break;
            case R.id.rl_mine:
                setNormal();
                setCheck(ivMine, tvMine, R.mipmap.menu_mine_check, R.mipmap.menu_mine);
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

    private void setCheck(ImageView iv, TextView tv, int src, int currentSrc) {
        currentTv = tv;
        currentIv = iv;
        this.currentSrc = currentSrc;
        currentTv.setTextColor(getColor(R.color.default_blue));
        currentIv.setImageResource(src);
    }

    private void setNormal() {
        currentIv.setImageResource(currentSrc);
        currentTv.setTextColor(getColor(R.color.text_gray));
    }

    @Override
    public void go2Company() {
        setNormal();
        setCheck(ivCompany, tvCompany, R.mipmap.menu_company_checked, R.mipmap.menu_company);
        if (companyFragment == null) {
            companyFragment = new CompanyFragment();
        }
        switchFragment(companyFragment);

    }

    @Override
    public void go2RegisterCompany() {
        startActivity(new Intent().setClass(this, CompanyRegisterActivity.class));
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
