package com.yyy.wrsf.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseFragment;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.PermissionListener;
import com.yyy.wrsf.main.persenter.CycleP;
import com.yyy.wrsf.main.persenter.ICycleP;
import com.yyy.wrsf.main.view.ICycleV;
import com.yyy.wrsf.mine.addvalue.AddValueActivity;
import com.yyy.wrsf.mine.backOrder.BackOrderActivity;
import com.yyy.wrsf.mine.month.MonthApplyActivity;
import com.yyy.wrsf.mine.order.OrderActivity;
import com.yyy.wrsf.mine.order.OrderNoticeActivity;
import com.yyy.wrsf.mine.order.OrderReceiveActivity;
import com.yyy.wrsf.mine.order.OrderSearchActivity;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.ImageLoaderUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.view.cycle.CircleBanner;
import com.yyy.wrsf.view.cycle.adapter.DataViewHolder;
import com.yyy.wrsf.view.cycle.adapter.HolderCreator;
import com.yyy.wrsf.view.cycle.bean.DataBean;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


public class MainFragment extends BaseFragment implements ICycleV {

    @BindView(R.id.viewpager)
    CircleBanner viewpager;
    @BindView(R.id.banner_image)
    ImageView bannerImage;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.rl_img)
    RelativeLayout rlImg;


    private CycleP cycleP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cycleP = new CycleP(this);
        cycleP.getImgs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        intiView();
        return view;
    }

    private void intiView() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({R.id.ll_search, R.id.iv_scan, R.id.mi_receipt, R.id.mi_send_notice, R.id.mi_pending_payment, R.id.mi_insured_tansportation, R.id.mi_statements, R.id.mi_apply_month, R.id.btn_shipping, R.id.btn_receive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                go2Search(null);
                break;
            case R.id.iv_scan:
                permission();
                break;
            case R.id.mi_receipt:
                go2BackOrder();
                break;
            case R.id.mi_send_notice:
                go2Notice();
                break;
            case R.id.mi_pending_payment:
                break;
            case R.id.mi_insured_tansportation:
                go2AddValue();
                break;
            case R.id.mi_statements:
                break;
            case R.id.mi_apply_month:
                go2Month();
                break;
            case R.id.btn_shipping:
                go2Order();
                break;
            case R.id.btn_receive:
                fo2Receive();
                break;
            default:
                break;
        }
    }

    private void fo2Receive() {
        startActivity(new Intent().setClass(getActivity(), OrderReceiveActivity.class));
    }

    private void go2Notice() {
        startActivity(new Intent().setClass(getActivity(), OrderNoticeActivity.class));
    }

    private void go2Order() {
        startActivity(new Intent().setClass(getActivity(), OrderActivity.class));
    }

    private void go2Month() {
        startActivity(new Intent().setClass(getActivity(), MonthApplyActivity.class));
    }

    private void go2AddValue() {
        startActivity(new Intent().setClass(getActivity(), AddValueActivity.class));
    }

    private void go2BackOrder() {
        startActivity(new Intent().setClass(getActivity(), BackOrderActivity.class));
    }

    @Override
    public void onDestroy() {
        viewpager.stopLoop();
        cycleP.detachView();
        super.onDestroy();
    }

    @Override
    public void setViewPager(List<DataBean> imgs) {
        viewpager.setVisibility(View.VISIBLE);
        rlImg.setVisibility(View.GONE);
        viewpager.isShowIndicator(true);
        //  设置图片切换时间间隔
        viewpager.setInterval(5000);
        //  设置指示器圆点半径
        // mViewpager.setIndicatorRadius(6);
        viewpager.setAutoPlay(true);
        viewpager.setCurrentItem(1, true);
        viewpager.setPages(imgs, new HolderCreator<DataViewHolder>() {
            @Override
            public DataViewHolder createViewHolder() {
                return new DataViewHolder();
            }
        });
    }

    @Override
    public void setImage(String s) {
        ImageLoaderUtil.loadImg(bannerImage, s);
    }

    @Override
    public void startLoading() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    @Override
    public void finishLoading(@Nullable String s) {
        if (!TextUtils.isEmpty(s)) {
            toast(s);
        } else {
            LoadingDialog.cancelDialogForLoading();
        }
    }

    @Override
    public void toast(String s) {
        Toasts.showShort(getActivity(), s);
    }

    /**
     * 扫描权限申请和扫描逻辑处理
     */
    private void permission() {
        requestRunPermisssion(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                go2Scan();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                toast(getString(R.string.error_permission));
            }
        });
    }

    private void go2Scan() {
        Intent intent = new Intent().setClass(getActivity(), CaptureActivity.class);
        /* ZxingConfig是配置类
         * 可以设置是否显示底部布局，闪光灯，相册，
         * 是否播放提示音  震动
         * 设置扫描框颜色等
         * 也可以不传这个参数
         * */
        ZxingConfig config = new ZxingConfig();
        config.setDecodeBarCode(false);//是否扫描条形码 默认为true
        config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        this.startActivityForResult(intent, CodeUtil.SCAN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码返回值
        if (requestCode == CodeUtil.SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                go2Search(content);
            } else {
                toast(getString(R.string.error_scan));
            }
        }
    }

    private void go2Search(String content) {
        startActivity(new Intent().setClass(getActivity(), OrderSearchActivity.class).putExtra("data", TextUtils.isEmpty(content) ? "" : content));
    }
}
