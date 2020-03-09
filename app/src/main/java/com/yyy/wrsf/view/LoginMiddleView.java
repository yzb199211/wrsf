package com.yyy.wrsf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yyy.wrsf.R;

public class LoginMiddleView extends LinearLayout {
    private Context context;
    private String title;
    private int titleSize;
    private int titleColor;
    private String text;
    private int textSize;
    private int textColor;
    private int type;

    private TextView tvTitle;
    private TextView tvDetail;

    public LoginMiddleView(Context context) {
        this(context, null);
    }

    public LoginMiddleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoginMiddle);
        title = array.getString(R.styleable.LoginMiddle_lmTitle);
        titleColor = array.getColor(R.styleable.LoginMiddle_lmTitleColor, context.getResources().getColor(R.color.text_common));
        titleSize = array.getDimensionPixelSize(R.styleable.LoginMiddle_lmTextSize, context.getResources().getDimensionPixelSize(R.dimen.text_login_title));
        text = array.getString(R.styleable.LoginMiddle_lmText);
        textColor = array.getColor(R.styleable.LoginMiddle_lmTextColor, context.getResources().getColor(R.color.text_gray));
        textSize = array.getDimensionPixelSize(R.styleable.LoginMiddle_lmTextSize, context.getResources().getDimensionPixelSize(R.dimen.text_common));
        type = array.getInt(R.styleable.LoginMiddle_lmType, 2);
        array.recycle();
    }

    private void initView() {
        initMain();
        initTitle();
        initDetail();
    }

    private void initMain() {
        LayoutInflater.from(context).inflate(R.layout.layout_middle_remark, this, true);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
    }

    private void initTitle() {
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        tvTitle.setTextColor(titleColor);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
    }

    private void initDetail() {
        tvDetail = findViewById(R.id.tv_remark);
        tvDetail.setText(TextUtils.isEmpty(text) ? "" : text);
        tvDetail.setTextColor(textColor);
        tvDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (type == 2) {
            tvDetail.setVisibility(GONE);
        }
    }
}
