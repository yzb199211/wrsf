package com.yyy.wrsf.view.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yyy.wrsf.R;


public class ButtonWithImg extends LinearLayout {
    Context context;

    int textColor;
    int textSize;
    String text;

    int src;
    int srcSize;

    TextView textView;
    ImageView img;


    public ButtonWithImg(Context context) {
        this(context, null);
    }

    public ButtonWithImg(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonWithImg);
        text = typedArray.getString(R.styleable.ButtonWithImg_bwiTilte);
        textColor = typedArray.getColor(R.styleable.ButtonWithImg_bwiTitleColor, context.getColor(R.color.text_common));
        textSize = typedArray.getDimensionPixelSize(R.styleable.ButtonWithImg_bwiTitleSize, context.getResources().getDimensionPixelSize(R.dimen.text_common));
        src = typedArray.getResourceId(R.styleable.ButtonWithImg_bwiSrc, 0);
        srcSize = typedArray.getDimensionPixelSize(R.styleable.ButtonWithImg_bwiSrcSize, context.getResources().getDimensionPixelSize(R.dimen.dp_20));
        typedArray.recycle();
    }

    private void initView() {
        initMain();
        if (src != 0)
            initImageView();
        initTextView();
    }


    private void initMain() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
//        setPadding(5, 0, 5, 0);
    }

    private void initImageView() {
        img = new ImageView(context);
        img.setLayoutParams(ivParams());
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageResource(src);
        addView(img);
    }

    private void initTextView() {
        textView = new TextView(context);
        textView.setMaxEms(4);
        textView.setSingleLine();
        textView.setTextColor(textColor);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setLayoutParams(tvParams());
        addView(textView);
    }

    private LayoutParams ivParams() {
        LayoutParams params = new LayoutParams(srcSize, srcSize);
        params.rightMargin = 10;
        return params;
    }

    private LayoutParams tvParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return params;
    }
}
