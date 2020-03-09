package com.yyy.wrsf.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yyy.wrsf.R;
import com.yyy.wrsf.view.textselect.OnMenuClick;

public class MenuItem extends LinearLayout {
    private Context context;
    private TextView tvMenu;
    private ImageView ivMenu;
    private int src;
    private int srcSize;
    private String title;
    private int titleSize;
    private int titleColor;
    private OnMenuClick onMenuClick;

    public MenuItem(Context context) {
        this(context, null);
    }

    public MenuItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MenuItem);
        src = array.getResourceId(R.styleable.MenuItem_miSrc, -1);
        srcSize = array.getDimensionPixelSize(R.styleable.MenuItem_miSrcSize, context.getResources().getDimensionPixelSize(R.dimen.dp_30));
        title = array.getString(R.styleable.MenuItem_miTitle);
        titleSize = array.getDimensionPixelSize(R.styleable.MenuItem_miTitleSize, context.getResources().getDimensionPixelSize(R.dimen.text_common));
        titleColor = array.getColor(R.styleable.MenuItem_miTitlteColor, context.getColor(R.color.text_common));
        array.recycle();
    }

    private void initView() {
        initMain();
        initImg();
        initText();
    }

    private void initMain() {
        setOrientation(VERTICAL);
    }


    private void initImg() {
        ivMenu = new ImageView(context);
        ivMenu.setImageResource(src);
        ivMenu.setLayoutParams(ivParams());
        addView(ivMenu);
    }

    private LayoutParams ivParams() {
        LayoutParams params = new LayoutParams(srcSize, srcSize);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        return params;
    }

    private void initText() {
        tvMenu = new TextView(context);
        tvMenu.setText(title);
        tvMenu.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        tvMenu.setTextColor(titleColor);
        tvMenu.setLayoutParams(tvParams());
        addView(tvMenu);
    }

    private LayoutParams tvParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = context.getResources().getDimensionPixelSize(R.dimen.dp_5);
        return params;
    }
}
