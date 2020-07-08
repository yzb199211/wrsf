package com.yyy.wrsf.mine.pay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.OrderBean;
import com.yyy.wrsf.beans.PayResult;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.WeChatPayCallback;
import com.yyy.wrsf.mine.pay.persenter.PayP;
import com.yyy.wrsf.mine.pay.view.IPayV;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.wrsf.wxapi.WXPayEntryActivity;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends BaseActivity implements IPayV, WeChatPayCallback {
    private final static String weixin = "WXPAY_APP";
    private final static String alibaba = "ALIPAY_APP";
    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.rb_weixin)
    RadioButton rbWeixin;
    @BindView(R.id.rb_alibaba)
    RadioButton rbAlibaba;
    @BindView(R.id.tv_pay)
    TextView tvPay;

    private int current;
    private static final int SDK_PAY_FLAG = 1;
    private OrderBean orderBean;
    private String payType = alibaba;
    private PayP payP;

//    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        payP = new PayP(this);
//        api = WXAPIFactory.createWXAPI(this,null );
        // 将该app注册到微信
//        api.registerApp("wx191cbb7ec0178fcb");
//        api.handleIntent(getIntent(), this);
        init();
    }


    private void init() {
        initRadio();
        initTop();
        initData();
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initRadio() {
        current = R.id.rb_alibaba;
        rbAlibaba.setButtonDrawable(R.drawable.rb_check);
        rbWeixin.setButtonDrawable(R.drawable.rb_check);
    }

    private void initData() {
        String data = getIntent().getStringExtra("data");
        try {
            orderBean = new Gson().fromJson(data, OrderBean.class);
            tvMoney.setText(getString(R.string.common_rmb) + orderBean.getUnpaid());
            tvPay.setText(getString(R.string.pay_alibaba) + getString(R.string.common_rmb) + orderBean.getUnpaid());
        } catch (JsonSyntaxException e) {

        }


    }

    @OnClick({R.id.ll_weixin, R.id.ll_alibaba, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_weixin:
                switchRadio(R.id.rb_weixin, getString(R.string.pay_weixin));
                payType = weixin;
                break;
            case R.id.ll_alibaba:
                switchRadio(R.id.rb_alibaba, getString(R.string.pay_alibaba));
                payType = alibaba;
                break;
            case R.id.tv_pay:
                payP.pay();
                break;
            default:
                break;
        }
    }

    private void switchRadio(int viewId, String text) {
        if (viewId != current) {
            RadioButton radioButton = findViewById(current);
            radioButton.setChecked(false);
            current = viewId;
            RadioButton radioButton1 = findViewById(current);
            radioButton1.setChecked(true);
            tvPay.setText(text + getString(R.string.common_rmb) + orderBean.getUnpaid());
        }
    }

    @Override
    public void pay(String orderInfo) {
        if (payType == alibaba) {
            alibabaPay(orderInfo);
        } else {
            weixinPay(orderInfo);
        }
    }

    private void weixinPay(String orderInfo) {
//        api.sendReq(payP.getWexinReq(orderInfo));
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this, "wx05eba7f79b845a4b", false);
        BaseApplication.getInstance().weChatPayCallback = this;
//        PayReq req=new PayReq();
        //设置PayReq的属性，一般由后台接口返回
        //像req.appId=xxx这样设置
        iwxapi.sendReq(payP.getWexinReq(orderInfo));//此处有返回值，如果成功调起微信支付，返回true，否则返回false
    }

    private void alibabaPay(String orderInfo) {
        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast(getString(R.string.common_pay_success));
                        setResult(CodeUtil.PAY);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast(getString(R.string.common_pay_fail));
                        Log.e("pay", resultInfo);
                    }
                    finish();
                    break;
                }

                default:
                    break;
            }
        }

        ;
    };

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.common_comfirm, null)
                .setOnDismissListener(onDismiss)
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        return false;
                    }
                })
                .show();
    }

    @Override
    public void getOrder() {

    }

    @Override
    public String getOrderNo() {
        return orderBean.getContractNo();
    }

    @Override
    public void setOrder() {

    }

    @Override
    public String getPayType() {
        return payType;
    }

    @Override
    public void startLoading() {
        LoadingDialog.showDialogForLoading(this);
    }

    @Override
    public void finishLoading(@Nullable String s) {
        LoadingFinish(s);
    }

    @Override
    public void toast(String s) {
        Toast(s);
    }


//    @Override
//    public void onReq(BaseReq baseReq) {
//        Log.e("type", baseReq.openId);
//        Log.e("type", baseReq.transaction + "");
//
//    }
//
//    @Override
//    public void onResp(BaseResp resp) {
//        Log.e("onResp", resp.errStr);
//        Log.e("onResp", resp.errCode + "");
//        Log.e("onResp", resp.transaction);
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            if (resp.errCode == 0) { //支付成功
//                Toast(getString(R.string.common_pay_success));
//                setResult(CodeUtil.PAY);
//            } else {
//                Toast(getString(R.string.common_pay_fail));
//                Log.e("weixin", resp.errStr);
//            }
//            finish();
//        }
//    }

    @Override
    protected void onDestroy() {
        payP.detachView();
        super.onDestroy();
    }

    @Override
    public void onWeChatPaySuccess() {
        Toast(getString(R.string.common_pay_success));
        setResult(CodeUtil.PAY);
        finish();
    }

    @Override
    public void onWeChatPayFailure() {
        Toast(getString(R.string.common_pay_fail));
        finish();
    }

    @Override
    public void onWeChatPayCancel() {
        Toast(getString(R.string.common_pay_cancel));
        finish();
    }
}
