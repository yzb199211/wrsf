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
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.PayTask;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.beans.PayResult;
import com.yyy.wrsf.view.topview.TopView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends BaseActivity {

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

    private RadioButton current;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initRadio();
    }

    private void initRadio() {
        rbAlibaba.setButtonDrawable(R.drawable.rb_check);
        rbWeixin.setButtonDrawable(R.drawable.rb_check);
    }

    @OnClick({R.id.ll_weixin, R.id.ll_alibaba, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_weixin:
                break;
            case R.id.ll_alibaba:
                break;
            case R.id.tv_pay:
                break;
            default:
                break;
        }
    }

    private void switchRadio(RadioButton view, String text) {
        if (view.getId() != current.getId()) {
            current.setChecked(false);
            current = view;
            current.setChecked(true);
            tvPay.setText(text + tvMoney.getText().toString());
        }
    }

    private void pay(String orderInfo) {
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

                        showAlert(PayActivity.this, getString(R.string.common_pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(PayActivity.this, getString(R.string.common_pay_fail) + payResult);
                    }
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
                .show();
    }
}
