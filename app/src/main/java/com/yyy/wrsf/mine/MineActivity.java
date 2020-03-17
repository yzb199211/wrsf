package com.yyy.wrsf.mine;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.yyy.wrsf.R;
import com.yyy.wrsf.model.MemberModel;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.view.textselect.TextMenuItem;
import com.yyy.wrsf.view.topview.TopView;

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
    }

    @OnClick({R.id.iv_logo, R.id.tmi_person_nickname, R.id.tmi_person_brithday, R.id.tmi_person_sex, R.id.tmi_person_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_logo:
                break;
            case R.id.tmi_person_nickname:

                break;
            case R.id.tmi_person_brithday:
                break;
            case R.id.tmi_person_sex:
                break;
            case R.id.tmi_person_email:
                break;
        }
    }
}
