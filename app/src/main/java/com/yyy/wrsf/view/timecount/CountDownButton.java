package com.yyy.wrsf.view.timecount;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.yyy.wrsf.R;

@SuppressLint("AppCompatCustomView")
public class CountDownButton extends Button {
    Context context;
    private TimeCount mTimeCount;
    private long millisInFuture = 60000;
    private long countDownInterval = 1000;
    private String mTickText = "";
    private String mFinishText = "";
    private int textColorNormal;
    private int textColorChecked;
    private OnSendListener onSendListener;

    public CountDownButton(Context context) {
        this(context, null);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }


    private void init() {
        mTickText = context.getString(R.string.common_code_count);
        mFinishText = context.getString(R.string.common_code_reacquire);
        textColorChecked = context.getResources().getColor(R.color.white);
        textColorNormal = context.getResources().getColor(R.color.text_common);
        setText(context.getString(R.string.common_code_get));
        setTextColor(textColorNormal);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendListener.onSend();
            }
        });
    }

    /**
     * 设置倒计时多少毫秒结束
     * 默认60秒
     *
     * @param millisInFuture 毫秒
     */
    public void setMillisInFuture(long millisInFuture) {
        this.millisInFuture = millisInFuture;
    }

    /**
     * 设置倒计时间隔
     * 默认1秒
     *
     * @param countDownInterval 倒计时间隔
     */
    public void setCountDownInterval(long countDownInterval) {
        this.countDownInterval = countDownInterval;
    }

    /**
     * 设置倒计时过程中button显示内容
     *
     * @param text 默认 s后重新获取
     */
    public void setOnTickText(String text) {
        this.mTickText = text;
    }

    /**
     * 设置倒计时结束button显示内容
     *
     * @param finishText 默认 重新获取
     */
    public void setOnFinishText(String finishText) {
        this.mFinishText = finishText;
    }


    public void startCount() {
        if (mTimeCount == null)
            mTimeCount = new TimeCount(millisInFuture, countDownInterval, this, mTickText, mFinishText);
        mTimeCount.setOnTickListener(new OnTickListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                setText(millisUntilFinished / 1000 + mTickText);
                setTextColor(textColorChecked);
                setEnabled(false);
            }

            @Override
            public void onFinish() {
                setText(mFinishText);
                setTextColor(textColorNormal);
                setEnabled(true);
            }
        });
        mTimeCount.start();
    }

    public void setOnSendListener(OnSendListener onSendListener) {
        this.onSendListener = onSendListener;
    }

}