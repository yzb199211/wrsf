package com.yyy.wrsf.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.yyy.wrsf.R;
import com.yyy.wrsf.login.LoginActivity;
import com.yyy.wrsf.login.PwdBackActivity;
import com.yyy.wrsf.mine.MineActivity;
import com.yyy.wrsf.mine.address.AddressActivity;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.view.textselect.TextMenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MineFragment extends Fragment {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tmi_person_company)
    TextMenuItem tmiPersonCompany;

    SharedPreferencesHelper preferencesHelper;
    private String tel;
    private String petname;
    private String company;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesHelper = new SharedPreferencesHelper(getActivity(), getString(R.string.preferenceCache));
        initDefaultData();
    }

    private void initDefaultData() {
        tel = (String) preferencesHelper.getSharedPreference("tel", "");
        petname = (String) preferencesHelper.getSharedPreference("petname", "");
        company = (String) preferencesHelper.getSharedPreference("companyName", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view); // Inflate the layout for this fragment
        init();
        return view;
    }

    private void init() {
        initView();
    }

    private void initView() {
        tvTel.setText(tel);
        tvName.setText(petname);
        tmiPersonCompany.setText(company);
    }

    @OnClick({R.id.iv_setting, R.id.mi_order, R.id.mi_invoice, R.id.mi_address, R.id.mi_detail, R.id.cardView, R.id.tmi_about, R.id.tmi_person_bill, R.id.tmi_person_company, R.id.tmi_modify_pwd, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                break;
            case R.id.mi_order:
                break;
            case R.id.mi_invoice:
                break;
            case R.id.mi_address:
                go2Address();
                break;
            case R.id.mi_detail:
                go2Detail();
                break;
            case R.id.cardView:
                break;
            case R.id.tmi_about:
                break;
            case R.id.tmi_person_bill:
                break;
            case R.id.tmi_person_company:
                break;
            case R.id.tmi_modify_pwd:
                go2pwd();
                break;
            case R.id.tv_exit:
                exit();
                break;
            default:
                break;
        }
    }

    private void go2Detail() {
        startActivityForResult(new Intent().setClass(getActivity(), MineActivity.class), CodeUtil.PersonInfo);
    }

    private void go2pwd() {
        startActivity(new Intent().setClass(getActivity(), PwdBackActivity.class));
    }

    private void go2Address() {
        startActivity(new Intent().setClass(getActivity(), AddressActivity.class).putExtra("isSelect", false));
    }

    private void exit() {
        startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
