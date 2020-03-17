package com.yyy.wrsf.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.yyy.wrsf.R;
import com.yyy.wrsf.model.MemberModel;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.TimeUtil;
import com.yyy.wrsf.view.textselect.TextMenuItem;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.TimePickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnTimeSelectListener;
import com.yyy.yyylibrary.pick.view.BasePickerView;
import com.yyy.yyylibrary.pick.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tmi_person_nickname)
    TextMenuItem tmiPersonNickname;
    @BindView(R.id.tmi_person_name)
    TextMenuItem tmiPersonName;
    @BindView(R.id.tmi_person_mobile)
    TextMenuItem tmiPersonMobile;
    @BindView(R.id.tmi_person_brithday)
    TextMenuItem tmiPersonBrithday;
    @BindView(R.id.tmi_person_sex)
    TextMenuItem tmiPersonSex;
    @BindView(R.id.tmi_person_email)
    TextMenuItem tmiPersonEmail;

    SharedPreferencesHelper preferencesHelper;
    private MemberModel memberModel;

    private TimePickerView pvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
    }

    private void init() {
        memberModel = new Gson().fromJson((String) preferencesHelper.getSharedPreference("member", ""), MemberModel.class);
        tmiPersonName.setText(memberModel.getMemberName());
        tmiPersonNickname.setText(memberModel.getMemberPetname());
        tmiPersonBrithday.setText(StringUtil.getBrithDay(memberModel.getBrithday()));
        tmiPersonEmail.setText(memberModel.getMail());
        tmiPersonMobile.setText(memberModel.getMemberTel());
        tmiPersonSex.setText(memberModel.getMemberSex());
        initTop();
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    @OnClick({R.id.iv_logo, R.id.tmi_person_nickname, R.id.tmi_person_brithday, R.id.tmi_person_sex, R.id.tmi_person_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
                break;
            case R.id.tmi_person_nickname:
                eidtPerson();
                break;
            case R.id.tmi_person_brithday:
                try {
                    if (pvDate == null)
                        initPvDate();
                    else {
                        pvDate.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tmi_person_sex:
                break;
            case R.id.tmi_person_email:
                eidtMail();
                break;
            default:
                break;
        }
    }

    private void initPvDate() throws Exception {
        pvDate = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tmiPersonBrithday.setText(StringUtil.getDate(date));
            }
        }).setRangDate(TimeUtil.str2calendar(getString(R.string.common_date_min)), Calendar.getInstance())
                .setDate(TextUtils.isEmpty(memberModel.getBrithday()) ? Calendar.getInstance() : TimeUtil.str2calendar(getString(R.string.common_date_min)))
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setContentTextSize(18)
                .setBgColor(0xFFFFFFFF)
                .build();
        setDialog(pvDate);
        initDialogWindow(pvDate.getDialog().getWindow());
        pvDate.show();
    }

    private void eidtPerson() {
        Intent intent = new Intent();
        intent.setClass(this, EditActivity.class);
        intent.putExtra("code", CodeUtil.PersonInfoName);
        intent.putExtra("data", tmiPersonName.getText());
        intent.putExtra("title", tmiPersonName.getTitle());
        startActivityForResult(intent, CodeUtil.PersonInfoName);
    }

    private void eidtMail() {
        Intent intent = new Intent();
        intent.setClass(this, EditActivity.class);
        intent.putExtra("code", CodeUtil.PersonInfoMail);
        intent.putExtra("data", tmiPersonEmail.getText());
        intent.putExtra("title", tmiPersonEmail.getTitle());
        startActivityForResult(intent, CodeUtil.PersonInfoMail);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (resultCode) {
                case CodeUtil.PersonInfoName:
                    tmiPersonNickname.setText(data.getStringExtra("data"));
                    memberModel.setMemberPetname(data.getStringExtra("data"));
                    break;
                case CodeUtil.PersonInfoMail:
                    tmiPersonEmail.setText(data.getStringExtra("data"));
                    memberModel.setMail(data.getStringExtra("data"));
                    break;
                default:
                    break;
            }
        }
    }

    private void setDialog(BasePickerView pickview) {
        getDialogLayoutParams();
        pickview.getDialogContainerLayout().setLayoutParams(getDialogLayoutParams());
        initDialogWindow(pickview.getDialog().getWindow());
    }

    private void initDialogWindow(Window window) {
//        window.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
        window.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
        window.setDimAmount(0.1f);
        window.setAttributes(getDialogWindowLayoutParams(window));
    }

    private WindowManager.LayoutParams getDialogWindowLayoutParams(Window window) {
        WindowManager.LayoutParams winParams;
        winParams = window.getAttributes();
        winParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        return winParams;
    }

    private FrameLayout.LayoutParams getDialogLayoutParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM);
        return params;
    }
}
