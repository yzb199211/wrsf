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
import com.yyy.wrsf.company.car.CarActivity;
import com.yyy.wrsf.company.car.CarAdapter;
import com.yyy.wrsf.company.driver.DriverActivity;
import com.yyy.wrsf.company.month.MonthActivity;
import com.yyy.wrsf.company.order.OrderActivity;
import com.yyy.wrsf.company.outlets.OutletActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CompanyFragment extends Fragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.cardView)
    CardView cardView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.mi_order, R.id.mi_car, R.id.mi_outlets, R.id.mi_check_bill, R.id.tmi_company_detail, R.id.tmi_company_member, R.id.tmi_company_driver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mi_order:
                go2Order();
                break;
            case R.id.mi_car:
                go2Car();
                break;
            case R.id.mi_outlets:
                go2Outlet();
                break;
            case R.id.mi_check_bill:
                break;
            case R.id.tmi_company_detail:
                go2Detail();
                break;
            case R.id.tmi_company_member:
                go2Month();
                break;
            case R.id.tmi_company_driver:
                go2Driver();
                break;
            default:
                break;
        }
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
}
