package com.yyy.wrsf.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.model.MemberModel;
import com.yyy.wrsf.model.publicm.Sex;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.SexUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.TimeUtil;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.view.textselect.TextMenuItem;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.OptionsPickerBuilder;
import com.yyy.yyylibrary.pick.builder.TimePickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnOptionsSelectListener;
import com.yyy.yyylibrary.pick.listener.OnTimeSelectListener;
import com.yyy.yyylibrary.pick.view.BasePickerView;
import com.yyy.yyylibrary.pick.view.OptionsPickerView;
import com.yyy.yyylibrary.pick.view.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private OptionsPickerView pvSex;
    List<Sex> sexes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.bind(this);
        preferencesHelper = new SharedPreferencesHelper(this, getString(R.string.preferenceCache));
        init();
    }

    private void init() {
        sexes = new SexUtil().getSexs();
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

    @OnClick({R.id.iv_logo, R.id.tmi_person_nickname, R.id.tmi_person_brithday, R.id.tmi_person_sex, R.id.tmi_person_email, R.id.btn_add})
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
                if (pvSex == null) {
                    initPvSex();
                } else {
                    pvSex.show();
                }
                break;
            case R.id.tmi_person_email:
                eidtMail();
                break;
            case R.id.btn_add:
                sendData();
                break;
            default:
                break;
        }
    }

    private void sendData() {
        new NetUtil(sexParams(), NetConfig.address + MemberURL.updateMember, RequstType.PUT, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("data", string);
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();

            }
        });
    }

    private List<NetParams> sexParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", getMember()));
        return params;
    }

    private String getMember() {
        MemberModel member = new MemberModel();
        member.setMail(tmiPersonEmail.getText());
        member.setMemberPetname(tmiPersonNickname.getText());
        member.setMemberSex(tmiPersonSex.getText());
        member.setBrithday(tmiPersonBrithday.getText());
        member.setRecNo(memberModel.getRecNo());
        return new Gson().toJson(member);
    }

    private void initPvSex() {
        pvSex = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tmiPersonSex.setText(sexes.get(options1).getPickerViewText());
            }
        }).setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .build();
        pvSex.setPicker(sexes);//一级选择器
        setDialog(pvSex);
        pvSex.show();
    }

    private void initPvDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        pvDate = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tmiPersonBrithday.setText(StringUtil.getDate(date));
            }
        }).setRangDate(TimeUtil.str2calendar(getString(R.string.common_date_min)),calendar )
                .setDate(TextUtils.isEmpty(memberModel.getBrithday()) ? calendar: TimeUtil.str2calendar(memberModel.getBrithday()))
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
        window.setWindowAnimations(R.style.picker_view_slide_anim);//修改动画样式
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
