package com.yyy.wrsf.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.yyy.wrsf.R;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.timecount.CountDownButton;
import com.yyy.wrsf.view.timecount.OnSendListener;

public class VerificationCode extends LinearLayout {
    private Context context;
    private CountDownButton countDownButton;
    private EditClearView editClearView;

    public VerificationCode(Context context) {
        this(context, null);
    }

    public VerificationCode(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_verification_code, this, true);
        initView();
    }

    private void initView() {
        initMain();
        initCount();
    }

    private void initMain() {
        setOrientation(HORIZONTAL);
    }

    private void initCount() {
        countDownButton = findViewById(R.id.count_down);
        editClearView = findViewById(R.id.ec_text);

    }

    private void initText() {
        editClearView = findViewById(R.id.ec_text);
    }

    public String getText() {
        return editClearView.getText();
    }

    public CountDownButton getCountDownButton() {
        return countDownButton;
    }
}
