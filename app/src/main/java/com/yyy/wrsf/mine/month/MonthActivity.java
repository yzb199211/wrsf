package com.yyy.wrsf.mine.month;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.TabB;
import com.yyy.wrsf.view.topview.TopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MonthActivity extends BaseActivity {
    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private Fragment currentFragment;
    private int currentTab = 0;

    private List<TabB> tabs;
    private List<TabLayout.Tab> tabsV = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month2);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initTop();
        initTabs();
        initFragment();
    }

    private void initTop() {
        topView.setOnLeftClickListener(()->{
            finish();
        });
    }

    private void initTabs() {
        tabs = new ArrayList<>();
        tabs.add(new TabB(4,getString(R.string.month_status_wait)));
        tabs.add(new TabB(0,getString(R.string.month_status_fail)));
        tabs.add(new TabB(5,getString(R.string.month_status_success)));
        setTabLayout();
    }
    private void initFragment() {
        fragments.add(new MonthFragment(4));
        fragments.add(new MonthFragment(0));
        fragments.add(new MonthFragment(5));
        setDefaultFragment();
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

    private void setDefaultFragment() {
        currentFragment = fragments.get(0);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction
                .add(R.id.frame_layout, currentFragment)
                .commit();
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
}
