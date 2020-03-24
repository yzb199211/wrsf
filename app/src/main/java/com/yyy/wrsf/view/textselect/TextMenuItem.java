package com.yyy.wrsf.view.textselect;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yyy.wrsf.R;
import com.yyy.wrsf.view.editclear.StringUtil;

public class TextMenuItem extends LinearLayout {
    private Context context;
    private ImageView ivMenu;
    private TextView tvTitle;
    private TextView tvDetail;

    private String title;
    private int titleSize;
    private int titleColor;

    private String text;
    private int textSize;
    private int textColor;
    private int textGravity;
    private String hint;
    private int leftSrc;
    private int leftSize;


    private boolean isSelected;

    @Override
    public void removeOnUnhandledKeyEventListener(OnUnhandledKeyEventListener listener) {
        super.removeOnUnhandledKeyEventListener(listener);
    }

    public TextMenuItem(Context context) {
        this(context, null);
    }

    public TextMenuItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextMenuItem);
        title = array.getString(R.styleable.TextMenuItem_tmiTitle);
        titleColor = array.getColor(R.styleable.TextMenuItem_tmiTitleColor, context.getColor(R.color.text_common));
        titleSize = array.getDimensionPixelSize(R.styleable.TextMenuItem_tmiTitleSize, context.getResources().getDimensionPixelSize(R.dimen.text_common));
        text = array.getString(R.styleable.TextMenuItem_tmiText);
        textColor = array.getColor(R.styleable.TextMenuItem_tmiTextColor, context.getColor(R.color.text_common));
        textSize = array.getDimensionPixelSize(R.styleable.TextMenuItem_tmiTextSize, context.getResources().getDimensionPixelSize(R.dimen.text_common));
        leftSrc = array.getResourceId(R.styleable.TextMenuItem_tmiLeftSrc, -1);
        leftSize = array.getResourceId(R.styleable.TextMenuItem_tmiLeftSize, context.getResources().getDimensionPixelSize(R.dimen.dp_20));
        isSelected = array.getBoolean(R.styleable.TextMenuItem_tmiSelected, false);
        textGravity = array.getInteger(R.styleable.TextMenuItem_tmiTextGravity, Gravity.RIGHT);
        hint = array.getString(R.styleable.TextMenuItem_tmiHint);
        array.recycle();
    }

    private void initView() {
        if (leftSrc != -1) {
            initLeft();
        }
        initTitle();
        initText();
        initSelected();

    }


    private void initLeft() {
        ivMenu = new ImageView(context);
        ivMenu.setImageResource(leftSrc);
        ivMenu.setLayoutParams(ivParams());
        addView(ivMenu);
    }

    private void initTitle() {
        tvTitle = new TextView(context);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        tvTitle.setTextColor(titleColor);
        tvTitle.setText(StringUtil.formatTitle(title));
        tvTitle.setLayoutParams(titleParams());
        tvTitle.setSingleLine();
        addView(tvTitle);
    }

    private void initText() {
        tvDetail = new TextView(context);
        tvDetail.setText(text);
        tvDetail.setTextColor(textColor);
        tvDetail.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvDetail.setLayoutParams(textParams());
        tvDetail.setSingleLine();
        tvDetail.setGravity(textGravity);
        tvDetail.setHint(TextUtils.isEmpty(hint) ? "" : context.getString(R.string.common_input) + hint);
        addView(tvDetail);
    }

    private void initSelected() {
        ImageView ivSelect = new ImageView(context);
        ivSelect.setImageResource(R.mipmap.arrow_right);
        ivSelect.setLayoutParams(selectParams());
        if (isSelected) {
            ivSelect.setVisibility(VISIBLE);
        } else {
            ivSelect.setVisibility(INVISIBLE);
        }
        addView(ivSelect);
    }

    private ViewGroup.LayoutParams selectParams() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.dp_20), context.getResources().getDimensionPixelSize(R.dimen.dp_20));
        return params;
    }

    private LayoutParams ivParams() {
        LayoutParams params = new LayoutParams(leftSize, leftSize);
        params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.dp_10);
        return params;
    }

    private LayoutParams titleParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return params;
    }

    private LayoutParams textParams() {
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.dp_10);
        return params;
    }

    public void setText(String text) {
        this.text = text;
        if (tvDetail != null)
            tvDetail.setText(text);
    }

    public String getText() {
        return tvDetail.getText().toString();
    }

    public String getTitle() {
        return tvTitle.getText().toString();
    }

    public TextView getTextView() {
        return tvDetail;
    }
}
