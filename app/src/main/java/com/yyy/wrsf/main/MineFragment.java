package com.yyy.wrsf.main;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseFragment;
import com.yyy.wrsf.interfaces.PermissionListener;
import com.yyy.wrsf.login.LoginActivity;
import com.yyy.wrsf.login.PwdBackActivity;
import com.yyy.wrsf.mine.MineActivity;
import com.yyy.wrsf.mine.address.AddressActivity;
import com.yyy.wrsf.mine.bill.BillActivity;
import com.yyy.wrsf.mine.bill.BillMonthActivity;
import com.yyy.wrsf.mine.month.MonthActivity;
import com.yyy.wrsf.mine.order.OrderActivity;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.view.textselect.TextMenuItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MineFragment extends BaseFragment {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tmi_person_company)
    TextMenuItem tmiPersonCompany;

    SharedPreferencesHelper preferencesHelper;
    @BindView(R.id.tmi_about)
    TextMenuItem tmiAbout;
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

    @OnClick({R.id.iv_setting, R.id.mi_order, R.id.mi_invoice,
            R.id.mi_address, R.id.mi_detail, R.id.cardView,
            R.id.tmi_about, R.id.tmi_person_bill, R.id.tmi_person_company,
            R.id.tmi_person_month, R.id.tmi_modify_pwd, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                break;
            case R.id.mi_order:
                go2Order();
                break;
            case R.id.mi_invoice:
                go2bill();
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
                callPermission(tmiAbout.getText());
                break;
            case R.id.tmi_person_bill:
                go2Bill();
                break;
            case R.id.tmi_person_month:
                go2Month();
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


    private void go2Bill() {
        startActivity(new Intent().setClass(getActivity(), BillMonthActivity.class));
    }

    private void go2Month() {
        startActivity(new Intent().setClass(getActivity(), MonthActivity.class));
    }

    private void go2Order() {
        startActivity(new Intent().setClass(getActivity(), OrderActivity.class));
    }


    private void go2Detail() {
        startActivityForResult(new Intent().setClass(getActivity(), MineActivity.class), CodeUtil.PersonInfo);
    }

    private void go2pwd() {
        startActivity(new Intent().setClass(getActivity(), PwdBackActivity.class));
    }

    private void go2bill() {
        startActivity(new Intent().setClass(getActivity(), BillActivity.class));
    }

    private void go2Address() {
        startActivity(new Intent().setClass(getActivity(), AddressActivity.class).putExtra("isSelect", false));
    }

    private void exit() {
        startActivity(new Intent().setClass(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void callPermission(String string) {
        requestRunPermisssion(new String[]{Manifest.permission.CALL_PHONE}, new PermissionListener() {
            @Override
            public void onGranted() {
                call(string);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                Toast(getString(R.string.error_permission));
            }
        });
    }
}
