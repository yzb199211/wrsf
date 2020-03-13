package com.yyy.wrsf.view.editclear;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yyy.wrsf.R;

public class EditClearView extends LinearLayout implements View.OnKeyListener {
    Context context;
    private ImageView ivLeft;
    private TextView tvTitle;
    private EditText editText;
    private TextView tvText;
    private ImageView ivDelete;

    private String text;
    private String title;
    private String hint;
    private int type;

    private int titleColor;
    private int titleSize;
    private int textColor;
    private int textSize;
    private int textLength;
    private int hintColor;
    private int textType;
    private int leftSrc;
    private int leftSrcPadding;
    private int commonPadding;
    private int lines;

    private boolean editable;
    private OnTextChange onTextChange;

    public EditClearView(Context context) {
        this(context, null);
    }

    public EditClearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        commonPadding = context.getResources().getDimensionPixelSize(R.dimen.padding_common);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EditClear);
        title = array.getString(R.styleable.EditClear_ecTitle);
        titleColor = array.getColor(R.styleable.EditClear_ecTextColor, context.getResources().getColor(R.color.text_common));
        titleSize = array.getDimensionPixelSize(R.styleable.EditClear_ecTextSize, context.getResources().getDimensionPixelSize(R.dimen.text_title_edit));
        text = array.getString(R.styleable.EditClear_ecText);
        textColor = array.getColor(R.styleable.EditClear_ecTextColor, context.getResources().getColor(R.color.text_common));
        textSize = array.getDimensionPixelSize(R.styleable.EditClear_ecTextSize, context.getResources().getDimensionPixelSize(R.dimen.text_common));
        textLength = array.getInteger(R.styleable.EditClear_ecTextLength, -1);
        hint = array.getString(R.styleable.EditClear_ecHint);
        hintColor = array.getColor(R.styleable.EditClear_ecHintColor, 0);
        leftSrc = array.getResourceId(R.styleable.EditClear_ecSrc, -1);
        leftSrcPadding = array.getDimensionPixelSize(R.styleable.EditClear_ecSrcPadding, 0);
        type = array.getInteger(R.styleable.EditClear_ecType, 2);
        editable = array.getBoolean(R.styleable.EditClear_ecEditable, true);
        textType = array.getInteger(R.styleable.EditClear_ecTextType, 0);
        lines = array.getInteger(R.styleable.EditClear_ecTextLines, 1);
        array.recycle();
    }

    private void initView() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setGravity(Gravity.CENTER_VERTICAL);
        if (type == 1) {
            initLeft();
        } else if (type == 2) {
            initTitle();
        }
        if (editable) {
            initText();
        } else {
            initTvText();
        }
        initDelete();
    }

    private void initLeft() {
        ivLeft = new ImageView(context);
        ivLeft.setImageResource(leftSrc);
        ivLeft.setLayoutParams(ivParams());
        ivLeft.setPadding(leftSrcPadding, leftSrcPadding, leftSrcPadding, leftSrcPadding);
        addView(ivLeft);
    }

    private void initTitle() {
        tvTitle = new TextView(context);
        tvTitle.setText(StringUtil.formatTitle(title));
        tvTitle.setTextColor(titleColor);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        tvTitle.setPadding(commonPadding, commonPadding, commonPadding, commonPadding);
//        tvTitle.setGravity(Gravity.CENTER);
        addView(tvTitle);
    }

    private void initText() {
        editText = new EditText(context);
        editText.setHint(TextUtils.isEmpty(hint) ? "" : context.getString(R.string.common_input) + hint);
        editText.setText(text);
        editText.setTextColor(textColor);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        editText.setLayoutParams(etParams());
        editText.setPadding(commonPadding, commonPadding, commonPadding, commonPadding);
        editText.setSingleLine();
        editText.setBackground(null);
        editText.setOnKeyListener(this);
        editText.setInputType(getInputType());
        setTextLength();
        addTextListener();
        if (hintColor != 0)
            editText.setHintTextColor(hintColor);
        addView(editText);
    }

    private void initTvText() {
        tvText = new TextView(context);
        tvText.setText(text);
        tvText.setHint(TextUtils.isEmpty(hint) ? "" : context.getString(R.string.common_input) + hint);
        tvText.setTextColor(textColor);
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvText.setLayoutParams(etParams());
        tvText.setGravity(Gravity.CENTER_VERTICAL);
        tvText.setPadding(commonPadding, commonPadding, commonPadding, commonPadding);
        tvText.setSingleLine();
        tvText.setBackground(null);
        addView(tvText, type == 0 ? 0 : 1);
    }

    private int getInputType() {
        if (textType == 1) {
            return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        }
        if (textType == 2) {
            return EditorInfo.TYPE_CLASS_NUMBER;
        }
        if (textType == 3) {
            return EditorInfo.TYPE_NUMBER_FLAG_DECIMAL;
        }
        return InputType.TYPE_CLASS_TEXT;
    }

    private void setTextLength() {
        if (textLength != -1) {
            //手动设置maxLength为20
            InputFilter[] filters = {new InputFilter.LengthFilter(textLength)};
            editText.setFilters(filters);

        }
    }

    private void addTextListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && ivDelete.getVisibility() == View.INVISIBLE) {
                    ivDelete.setVisibility(VISIBLE);
                } else if (s.length() == 0 && ivDelete.getVisibility() == View.VISIBLE) {
                    ivDelete.setVisibility(INVISIBLE);
                }
                if (onTextChange != null) {
                    onTextChange.onText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initDelete() {
        ivDelete = new ImageView(context);
        ivDelete.setImageResource(R.mipmap.icon_delete);
        ivDelete.setPadding(commonPadding, commonPadding, commonPadding, commonPadding);
        ivDelete.setLayoutParams(deleteParams());
        ivDelete.setVisibility(INVISIBLE);
        ivDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        addView(ivDelete);
    }

    private LayoutParams ivParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return params;
    }

    private LayoutParams etParams() {
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        return params;
    }

    private LayoutParams deleteParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return params;
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((keyCode == EditorInfo.IME_ACTION_SEND
                || keyCode == EditorInfo.IME_ACTION_DONE || keyCode == KeyEvent.KEYCODE_ENTER) && event.getAction() == KeyEvent.ACTION_DOWN) {
            closeKeybord();
            if (editText != null) {
                this.requestFocus();
            }
            return true;
        }
        return false;
    }

    public void closeKeybord() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void setOnTextChange(OnTextChange onTextChange) {
        this.onTextChange = onTextChange;
    }

    public void forbidEdit() {
        if (editText != null && ivDelete != null) {
            text = getText();
            editText.setVisibility(GONE);
            ivDelete.setVisibility(GONE);
            initTvText();
        }
    }

    public void setText(String s) {
        if (editable)
            editText.setText(s);
        else
            tvText.setText(s);
    }

    public String getText() {
        if (editText != null) {
            return editText.getText().toString();
        }
        if (tvText != null) {
            return tvText.getText().toString();
        }
        return "";
    }

    public TextView getTvText() {
        return tvText;
    }

    public String getHint() {
        return TextUtils.isEmpty(hint) ? "" : context.getString(R.string.common_input) + hint;
    }
}
