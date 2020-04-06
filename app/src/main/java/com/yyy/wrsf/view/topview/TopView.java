package com.yyy.wrsf.view.topview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.yyy.wrsf.R;

public class TopView extends RelativeLayout {
    private Context context;
    private ImageView ivLeft;
    private TextView tvTitle;
    private TextView tvRight;
    private boolean leftIvShow;

    private int leftSrc;
    private String title;
    @ColorInt
    private int titleColor;
    private int titleSize;
    private boolean rightTvShow;
    private String rightText;
    @ColorInt
    private int rightColor;
    private int rightSize;
    private OnLeftClickListener onLeftClickListener;
    private OnRightClickListener onRightClickListener;


    public TopView(Context context) {
        this(context, null);
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopView);
        leftIvShow = array.getBoolean(R.styleable.TopView_topLeftIvShow, false);
        leftSrc = array.getResourceId(R.styleable.TopView_topLeftSrc, -1);
        title = array.getString(R.styleable.TopView_topTitle);
        titleColor = array.getColor(R.styleable.TopView_topTitleColor, context.getResources().getColor(R.color.text_common));
        titleSize = array.getDimensionPixelSize(R.styleable.TopView_topTitleSize, context.getResources().getDimensionPixelSize(R.dimen.text_title_max));
        rightTvShow = array.getBoolean(R.styleable.TopView_topRightTvShow, false);
        rightText = array.getString(R.styleable.TopView_topRightText);
        rightColor = array.getColor(R.styleable.TopView_topRightTextColor, context.getResources().getColor(R.color.text_common));
        rightSize = array.getDimensionPixelSize(R.styleable.TopView_topRightTextSize, context.getResources().getDimensionPixelSize(R.dimen.text_title_max));
        array.recycle();
    }

    private void initView() {
        initTitle();
        if (leftIvShow)
            initLeft();
        if (rightTvShow)
            initRight();
    }


    private void initTitle() {
        tvTitle = new TextView(context);
        tvTitle.setText(title);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        tvTitle.setTextColor(titleColor);
        tvTitle.setLayoutParams(titleParams());
        tvTitle.setGravity(Gravity.CENTER);
        addView(tvTitle);
    }

    private void initLeft() {
        ivLeft = new ImageView(context);
        ivLeft.setImageResource(leftSrc);
        ivLeft.setLayoutParams(leftIvParams());
        ivLeft.setPadding(context.getResources().getDimensionPixelSize(R.dimen.padding_common), context.getResources().getDimensionPixelSize(R.dimen.padding_common), context.getResources().getDimensionPixelSize(R.dimen.padding_common), context.getResources().getDimensionPixelSize(R.dimen.padding_common));
        ivLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLeftClickListener != null) {
                    onLeftClickListener.onLeft();
                }
            }
        });
        addView(ivLeft);
    }

    private void initRight() {
        tvRight = new TextView(context);
        tvRight.setText(rightText);
        tvRight.setTextColor(rightColor);
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightSize);
        tvRight.setLayoutParams(rightTvParams());
        tvRight.setGravity(Gravity.CENTER);
        tvRight.setPadding(context.getResources().getDimensionPixelSize(R.dimen.padding_common), context.getResources().getDimensionPixelSize(R.dimen.padding_common), context.getResources().getDimensionPixelSize(R.dimen.padding_common), context.getResources().getDimensionPixelSize(R.dimen.padding_common));
        tvRight.setVisibility(rightTvShow ? VISIBLE : INVISIBLE);
        tvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRightClickListener != null) {
                    onRightClickListener.onRight();
                }
            }
        });
        addView(tvRight);
    }

    private LayoutParams titleParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        return params;
    }

    private LayoutParams leftIvParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        return params;
    }

    private LayoutParams rightTvParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        return params;
    }

    public void setOnLeftClickListener(OnLeftClickListener onLeftClickListener) {
        this.onLeftClickListener = onLeftClickListener;
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

    public void setTitle(String title) {
        this.title = title;
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    public void setRightText(String s) {
        tvRight.setText(s);
    }

    public void setRightTvShow(boolean rightTvShow) {
        this.rightTvShow = rightTvShow;
        if (tvRight == null) {
            initRight();
        } else {
            tvRight.setVisibility(rightTvShow ? VISIBLE : INVISIBLE);
        }

    }
}
