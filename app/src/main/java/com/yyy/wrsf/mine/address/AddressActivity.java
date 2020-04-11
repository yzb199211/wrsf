package com.yyy.wrsf.mine.address;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.TabB;
import com.yyy.wrsf.view.topview.TopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressActivity extends BaseActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    private Fragment currentFragment;
    private int currentTab = 0;
    private AddressSendFragment sendFragment;
    private AddressReceiveFragment receiveFragment;

    private List<TabB> tabs;
    private List<TabLayout.Tab> tabsV = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initFragment();
        initView();
    }

    private void initFragment() {
        sendFragment = new AddressSendFragment();
        receiveFragment = new AddressReceiveFragment();
        fragments.add(receiveFragment);
        fragments.add(sendFragment);
    }

    private void initView() {
        initTop();
        initTab();
        setDefaultFragment();
    }

    private void initTab() {
        tabs = getTabs();
        setTabLayout();
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }


    public List<TabB> getTabs() {
        List<TabB> tabs = new ArrayList<>();
        tabs.add(new TabB(0, BaseApplication.getInstance().getString(R.string.address_title_receive)));
        tabs.add(new TabB(1, BaseApplication.getInstance().getString(R.string.address_title_send)));
        return tabs;
    }

    private void setDefaultFragment() {
        currentFragment = receiveFragment;
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction
                .add(R.id.frame_layout, receiveFragment)
                .commit();
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.frame_layout, targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }

    private void setTabLayout() {
        for (TabB tabB : tabs) {
            TabLayout.Tab tab = tabLayout.newTab().setText(tabB.getName());
            tabsV.add(tab);
            tabLayout.addTab(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (currentTab != tab.getPosition()) {
                    currentTab = tab.getPosition();
                    switchFragment(fragments.get(currentTab));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
