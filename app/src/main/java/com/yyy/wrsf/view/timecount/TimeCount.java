package com.yyy.wrsf.view.timecount;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCount extends CountDownTimer {
    private Button button;
    private String tickText;
    private String finishText;
    OnTickListener onTickListener;

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * @param millisInFuture    倒计时总时长
     * @param countDownInterval 倒计时单位 毫秒.
     */
    public TimeCount(long millisInFuture, long countDownInterval,
                     Button button, String tickText, String finishText) {
        super(millisInFuture, countDownInterval);
        this.button = button;
        this.tickText = tickText;
        this.finishText = finishText;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        if (onTickListener != null) {
            onTickListener.onTick(millisUntilFinished);
        }
//        button.setText(millisUntilFinished / 1000 + tickText);
//        button.setEnabled(false);
    }

    @Override
    public void onFinish() {
        if (onTickListener != null) {
            onTickListener.onFinish();
        }
//        button.setEnabled(true);
//        button.setText(finishText);
    }

    public void setOnTickListener(OnTickListener onTickListener) {
        this.onTickListener = onTickListener;
    }
}