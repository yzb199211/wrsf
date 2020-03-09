package com.yyy.wrsf.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.yyy.wrsf.R;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.view.cycle.CircleBanner;
import com.yyy.wrsf.view.cycle.adapter.DataViewHolder;
import com.yyy.wrsf.view.cycle.adapter.HolderCreator;
import com.yyy.wrsf.view.cycle.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainFragment extends Fragment {

    @BindView(R.id.viewpager)
    CircleBanner viewpager;
    private List<DataBean> mDataList = new ArrayList<>();
    private String[] picUrls = {"http://pic1.win4000.com/wallpaper/9/5450ae2fdef8a.jpg",
            "http://pic1.nipic.com/2008-08-14/2008814183939909_2.jpg",
            "http://pic1.win4000.com/wallpaper/9/5450ae2fdef8a.jpg",
            "http://pic1.nipic.com/2008-08-14/2008814183939909_2.jpg"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        setDefaultImg();
        setViewPager();
        return view;
    }

    private void setDefaultImg() {
        for (int i = 0; i < picUrls.length; i++) {
            DataBean dataBean = new DataBean(picUrls[i], "图片" + (i + 1));
            mDataList.add(dataBean);
        }
    }

    private void setViewPager() {
        //  设置指示器位置
        // mViewpager.setIndicatorGravity(CircleViewPager.END);
        //  是否显示指示器
        viewpager.isShowIndicator(true);
        //  设置图片切换时间间隔
        viewpager.setInterval(5000);
        //  设置指示器圆点半径
        // mViewpager.setIndicatorRadius(6);
        viewpager.setAutoPlay(true);
        viewpager.setCurrentItem(1, true);

        //  设置页面点击事件
        viewpager.setOnPageClickListener(new CircleBanner.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                List<DataBean> list = viewpager.getList();
                String describe = list.get(position).getDescribe();
                Toasts.showShort(getActivity(), "点击了" + describe);
            }
        });
        //  设置数据
        viewpager.setPages(mDataList, new HolderCreator<DataViewHolder>() {
            @Override
            public DataViewHolder createViewHolder() {
                return new DataViewHolder();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @OnClick({R.id.iv_scan, R.id.mi_receipt, R.id.mi_send_notice, R.id.mi_pending_payment, R.id.mi_insured_tansportation, R.id.mi_statements, R.id.mi_apply_month, R.id.btn_shipping, R.id.btn_receive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scan:
                break;
            case R.id.mi_receipt:
                break;
            case R.id.mi_send_notice:
                break;
            case R.id.mi_pending_payment:
                break;
            case R.id.mi_insured_tansportation:
                break;
            case R.id.mi_statements:
                break;
            case R.id.mi_apply_month:
                break;
            case R.id.btn_shipping:
                break;
            case R.id.btn_receive:
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewpager.stopLoop();

    }
}
