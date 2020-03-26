package com.yyy.wrsf.company;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.yyy.wrsf.BaseActivity;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.model.company.CompanyModel;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.company.CompanyUrl;
import com.yyy.wrsf.view.textselect.TextMenuItem;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyDetailActivity extends BaseActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.tmi_company_name)
    TextMenuItem tmiCompanyName;
    @BindView(R.id.tmi_company_address)
    TextMenuItem tmiCompanyAddress;
    @BindView(R.id.tmi_company_address_detail)
    TextMenuItem tmiCompanyAddressDetail;
    @BindView(R.id.tmi_company_legal)
    TextMenuItem tmiCompanyLegal;
    @BindView(R.id.tmi_company_contract)
    TextMenuItem tmiCompanyContract;
    @BindView(R.id.tmi_company_tel)
    TextMenuItem tmiCompanyTel;
    private CompanyModel companyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);
        init();
        getData();
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(),
                NetConfig.address + CompanyUrl.getCompany,
                RequstType.GET,
                new ResponseListener() {
                    @Override
                    public void onSuccess(String string) {
                        LoadingFinish(null);
                        Log.e(CompanyRegisterActivity.class.getName(), "data:" + string);
                        try {
                            Result result = new Result(string);
                            if (result.isSuccess()) {
                                companyModel = new Gson().fromJson(result.getData(), CompanyModel.class);
                                initData();
                            } else {
                                LoadingFinish(result.getMsg());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LoadingFinish(e.getMessage());
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        LoadingFinish(e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    private void initData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tmiCompanyName.setText(companyModel.getCompanyName());
                tmiCompanyAddress.setText(companyModel.getFristAdd() + "\u3000" + companyModel.getSecondAdd() + "\u3000" + companyModel.getThirdAdd());
                tmiCompanyAddressDetail.setText(companyModel.getDetailAdd());
                tmiCompanyLegal.setText(companyModel.getPerson());
                tmiCompanyContract.setText(companyModel.getContractPerson());
                tmiCompanyTel.setText(companyModel.getContractTel());
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("recNo", "2"));
        return params;
    }

    private void init() {
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

    private void LoadingFinish(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (StringUtil.isNotEmpty(msg)) {
                    Toast(msg);
                }
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private void Toast(String msg) {
        Toasts.showShort(this, msg);
    }
}
