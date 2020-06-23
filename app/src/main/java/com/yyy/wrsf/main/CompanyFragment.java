package com.yyy.wrsf.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.yyy.wrsf.R;
import com.yyy.wrsf.company.CompanyDetailActivity;
import com.yyy.wrsf.company.bill.CompanyBillActivity;
import com.yyy.wrsf.company.car.CarActivity;
import com.yyy.wrsf.company.collection.CollectionActivity;
import com.yyy.wrsf.company.driver.DriverActivity;
import com.yyy.wrsf.company.month.MonthActivity;
import com.yyy.wrsf.company.order.OrderActivity;
import com.yyy.wrsf.company.order.OrderWaitActivity;
import com.yyy.wrsf.company.outlets.OutletActivity;
import com.yyy.wrsf.company.worker.WorkerActivity;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.view.textselect.TextMenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CompanyFragment extends Fragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.tmi_company_member)
    TextMenuItem tmiCompanyMember;
    @BindView(R.id.tmi_outlets)
    TextMenuItem tmiOutlets;

    SharedPreferencesHelper preferencesHelper;
    int roleType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        ButterKnife.bind(this, view);
        preferencesHelper = new SharedPreferencesHelper(getActivity(), getString(R.string.preferenceCache));
        roleType = (int) preferencesHelper.getSharedPreference("roleType", 3);
        initView();
        return view;
    }

    private void initView() {
        tvName.setText((String) preferencesHelper.getSharedPreference("companyName", ""));
        tmiCompanyMember.setVisibility(roleType == 2 ? View.VISIBLE : View.GONE);
        tmiOutlets.setVisibility(roleType == 2 ? View.VISIBLE : View.GONE);
    }


    @OnClick({R.id.mi_order, R.id.tmi_car, R.id.tmi_outlets, R.id.mi_check_bill, R.id.tmi_company_detail,
            R.id.tmi_company_member, R.id.tmi_company_driver, R.id.tmi_company_worker, R.id.mi_pending_payment, R.id.mi_wait})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mi_order:
                go2Order();
                break;
            case R.id.mi_wait:
                go2Wait();
                break;
            case R.id.mi_pending_payment:
                go2Pending();
                break;
            case R.id.mi_check_bill:
                go2Bill();
                break;
            case R.id.tmi_company_detail:
                go2Detail();
                break;
            case R.id.tmi_company_member:
                go2Month();
                break;
            case R.id.tmi_car:
                go2Car();
                break;
            case R.id.tmi_outlets:
                go2Outlet();
                break;
            case R.id.tmi_company_driver:
                go2Driver();
                break;
            case R.id.tmi_company_worker:
                go2Worker();
                break;
            default:
                break;
        }
    }

    private void go2Bill() {
        startActivity(new Intent().setClass(getActivity(), CompanyBillActivity.class));
    }

    private void go2Pending() {
        startActivity(new Intent().setClass(getActivity(), CollectionActivity.class));
    }

    private void go2Wait() {
        startActivity(new Intent().setClass(getActivity(), OrderWaitActivity.class));
    }


    private void go2Order() {
        startActivity(new Intent().setClass(getActivity(), OrderActivity.class));
    }

    private void go2Outlet() {
        startActivity(new Intent().setClass(getActivity(), OutletActivity.class));
    }

    private void go2Month() {
        startActivity(new Intent().setClass(getActivity(), MonthActivity.class));
    }

    private void go2Car() {
        startActivity(new Intent().setClass(getActivity(), CarActivity.class));
    }

    private void go2Driver() {
        startActivity(new Intent().setClass(getActivity(), DriverActivity.class));
    }

    private void go2Detail() {
        startActivity(new Intent().setClass(getActivity(), CompanyDetailActivity.class));
    }

    private void go2Worker() {
        startActivity(new Intent().setClass(getActivity(), WorkerActivity.class));
    }
}
