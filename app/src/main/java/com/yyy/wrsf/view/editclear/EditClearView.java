package com.yyy.wrsf.view.editclear;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
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
import com.yyy.wrsf.interfaces.OnEnterListerner;
import com.yyy.wrsf.interfaces.OnItemClickListener;

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
    private int textGravity;
    private int hintColor;
    private int textType;
    private int leftSrc;
    private int leftSrcPadding;
    private int commonPadding;
    private int textPadding;
    private int textMarginLeft;
    private int lines;
    private int digit;
    private boolean singleLine;
    private boolean editable;
    private boolean hasDelete = true;
    private boolean formatTitle = true;
    private OnTextChange onTextChange;
    private OnTextChangeAfter onTextChangeAfter;
    private OnItemClickListener onItemClickListener;
    private OnEnterListerner onEnterListerner;
    private boolean onItemAble = true;

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
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EditClearView);
        title = array.getString(R.styleable.EditClearView_ecTitle);
        titleColor = array.getColor(R.styleable.EditClearView_ecTitleColor, context.getResources().getColor(R.color.text_common));
        titleSize = array.getDimensionPixelSize(R.styleable.EditClearView_ecTitleSize, context.getResources().getDimensionPixelSize(R.dimen.text_common));
        text = array.getString(R.styleable.EditClearView_ecText);
        textColor = array.getColor(R.styleable.EditClearView_ecTextColor, context.getResources().getColor(R.color.text_gray2));
        textSize = array.getDimensionPixelSize(R.styleable.EditClearView_ecTextSize, context.getResources().getDimensionPixelSize(R.dimen.text_common));
        textLength = array.getInteger(R.styleable.EditClearView_ecTextLength, -1);
        digit = array.getInteger(R.styleable.EditClearView_ecTextDigit, 0);
        textGravity = array.getInteger(R.styleable.EditClearView_ecTextGravity, -1);
        hint = array.getString(R.styleable.EditClearView_ecHint);
        hintColor = array.getColor(R.styleable.EditClearView_ecHintColor, 0);
        leftSrc = array.getResourceId(R.styleable.EditClearView_ecSrc, -1);
        leftSrcPadding = array.getDimensionPixelSize(R.styleable.EditClearView_ecSrcPadding, 0);
        type = array.getInteger(R.styleable.EditClearView_ecType, 2);
        editable = array.getBoolean(R.styleable.EditClearView_ecEditable, true);
        textType = array.getInteger(R.styleable.EditClearView_ecTextType, 0);
        lines = array.getInteger(R.styleable.EditClearView_ecTextLines, 1);
        formatTitle = array.getBoolean(R.styleable.EditClearView_ecFormatTitle, true);
        hasDelete = array.getBoolean(R.styleable.EditClearView_ecHasDetele, true);
        singleLine = array.getBoolean(R.styleable.EditClearView_ecSingleLine, true);
        textPadding = array.getDimensionPixelSize(R.styleable.EditClearView_ecTextPadding, commonPadding);
        textMarginLeft = array.getDimensionPixelSize(R.styleable.EditClearView_ecTextMarginLeft, context.getResources().getDimensionPixelSize(R.dimen.dp_10));
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
        if (hasDelete)
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
        tvTitle.setText(formatTitle ? StringUtil.formatTitle(title) : title);
        tvTitle.setTextColor(titleColor);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        addView(tvTitle);
    }

    private void initText() {
        editText = new EditText(context);
        editText.setHint(TextUtils.isEmpty(hint) ? "" : context.getString(R.string.common_input) + hint);
        editText.setTextColor(textColor);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        editText.setLayoutParams(etParams());
        editText.setPadding(textPadding, textPadding, textPadding, textPadding);
        editText.setGravity(Gravity.CENTER_VERTICAL);
        if (singleLine) {
            editText.setSingleLine();
            editText.setInputType(getInputType());
        } else {
            editText.setHorizontallyScrolling(false);
            editText.setSingleLine(false);
            editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }
        editText.setText(text);
        editText.setBackground(null);
        editText.setOnKeyListener(this);
        setTextLength();
        addTextListener();
        if (hintColor != 0)
            editText.setHintTextColor(hintColor);
        addView(editText, type == 0 ? 0 : 1);
    }

    private void initTvText() {
        tvText = new TextView(context);
        tvText.setText(text);
        tvText.setHint(TextUtils.isEmpty(hint) ? "" : context.getString(R.string.common_input) + hint);
        tvText.setTextColor(textColor);
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tvText.setLayoutParams(etParams());
        if (textGravity == 5)
            tvText.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        else tvText.setGravity(Gravity.CENTER_VERTICAL);
        tvText.setPadding(commonPadding, commonPadding, commonPadding, commonPadding);
        if (singleLine)
            tvText.setSingleLine();
        tvText.setBackground(null);
        tvText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null && onItemAble) {
                    onItemClickListener.onItemClick(0);
                }
            }
        });
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
            return InputType.TYPE_CLASS_NUMBER |EditorInfo.TYPE_NUMBER_FLAG_DECIMAL;
        }
        return InputType.TYPE_CLASS_TEXT;
    }

    private void setTextLength() {
        if (textLength != -1) {
            //手动设置maxLength为20
            InputFilter[] filters = {getFilter()};
            editText.setFilters(filters);
        }
    }

    private InputFilter getFilter() {
        InputFilter lengthfilter;
        if (digit == 0) {
            lengthfilter = new InputFilter.LengthFilter(textLength);
        } else {
            lengthfilter = (source, start, end, dest, dstart, dend) -> {
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString())) {
                    return null;
                }
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - digit;
                    if (diff > 0) {
                        return source.subSequence(start, end - diff);
                    }
                }
                return null;
            };
        }
        return lengthfilter;
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
                if (onTextChangeAfter != null)
                    onTextChangeAfter.onText(s);
            }
        });
    }


    private void initDelete() {
        ivDelete = new ImageView(context);
        ivDelete.setImageResource(R.mipmap.icon_delete);
        ivDelete.setPadding(commonPadding, commonPadding, commonPadding, commonPadding);
        ivDelete.setLayoutParams(deleteParams());
        if (text == null || text.length() == 0)
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
        LayoutParams params = new LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.dp_40), ViewGroup.LayoutParams.MATCH_PARENT);
        return params;
    }

    private LayoutParams etParams() {
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        params.leftMargin = textMarginLeft;
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
            if (onEnterListerner != null) {
                onEnterListerner.onEnter();
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
        if (!editable) return;
        editable = false;
        if (editText != null && ivDelete != null) {
            editText.setVisibility(GONE);
            ivDelete.setVisibility(GONE);
            text = editText.getText().toString();
        }
        if (tvText == null)
            initTvText();
        else {
            tvText.setText(text);
            tvText.setVisibility(VISIBLE);
        }
    }

    public void setEditable() {
        if (editable) return;
        editable = true;
        if (tvText != null) {
            tvText.setVisibility(GONE);
            text = tvText.getText().toString();
        }
        if (editText == null) {
            initText();
        } else {
            editText.setVisibility(VISIBLE);
        }
        if (ivDelete == null) {
            initDelete();
        } else {
            ivDelete.setVisibility(text.length() == 0 ? INVISIBLE : VISIBLE);
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

    public String getTitle() {
        return TextUtils.isEmpty(title) ? "" : title;
    }

    public TextView getTvText() {
        return tvText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return TextUtils.isEmpty(hint) ? "" : context.getString(R.string.common_input) + hint;
    }

    public void  setHint(String hint){
        if (editText!=null){
            this.hint = hint;
            editText.setHint(context.getString(R.string.common_input) +hint);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnTextChangeAfter(OnTextChangeAfter onTextChangeAfter) {
        this.onTextChangeAfter = onTextChangeAfter;
    }

    public void setOnEnterListerner(OnEnterListerner onEnterListerner) {
        this.onEnterListerner = onEnterListerner;
    }

    public void setOnItemAble(boolean onItemAble) {
        this.onItemAble = onItemAble;
    }
}
