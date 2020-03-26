package com.yyy.wrsf.mine.bill;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.company.car.CarDetailActivity;
import com.yyy.wrsf.dialog.DialogUtil;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.model.BillModel;
import com.yyy.wrsf.model.publicm.PublicArray;
import com.yyy.wrsf.model.publicm.PublicModel;
import com.yyy.wrsf.model.filter.PublicFilterModel;
import com.yyy.wrsf.utils.PhoneUtils;
import com.yyy.wrsf.utils.PublicCode;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.bill.BillUrl;
import com.yyy.wrsf.utils.net.publics.PublicUrl;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.OptionsPickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnOptionsSelectListener;
import com.yyy.yyylibrary.pick.view.OptionsPickerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_company)
    EditClearView ecvCompany;
    @BindView(R.id.ecv_tax)
    EditClearView ecvTax;
    @BindView(R.id.ecv_bank)
    EditClearView ecvBank;
    @BindView(R.id.ecv_account)
    EditClearView ecvAccount;
    @BindView(R.id.ecv_type)
    EditClearView ecvType;
    @BindView(R.id.ecv_company_tel)
    EditClearView ecvCompanyTel;
    @BindView(R.id.ecv_company_address)
    EditClearView ecvCompanyAddress;
    @BindView(R.id.ecv_contract)
    EditClearView ecvContract;
    @BindView(R.id.ecv_contract_tel)
    EditClearView ecvContractTel;
    @BindView(R.id.ecv_contract_address)
    EditClearView ecvContractAddress;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    private BillModel billModel;
    private RequstType requstType;
    private String url;

    private PublicFilterModel publicFilter;
    private OptionsPickerView pvBilltype;
    private List<PublicModel> billTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        ButterKnife.bind(this);
        init();
        getData();
    }

    private void init() {
        initPublicFilter();
        initData();
        initTop();
        initType();
    }

    private void initPublicFilter() {
        publicFilter = new PublicFilterModel();
        List<Integer> list = new ArrayList<>();
        list.add(PublicCode.BillType);
        publicFilter.setPublicCodes(list);
    }

    private void initType() {
        ecvType.setOnItemClickListener((int pos) -> {
            if (billTypes.size() == 0) {
                getBillType();
            } else {
                pvBilltype.show();
            }
        });
    }


    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
    }

    private void initData() {
        requstType = RequstType.POST;
        url = BillUrl.insert;
        billModel = new BillModel();
    }

    private void getData() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getParams(), NetConfig.address + BillUrl.getBill, RequstType.GET, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                LoadingFinish(null);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        if (!TextUtils.isEmpty(result.getData())) {
                            billModel = new Gson().fromJson(result.getData(), BillModel.class);
                            requstType = RequstType.PUT;
                            url = BillUrl.update;
                            setBill();
                        }
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
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private void setBill() {
        runOnUiThread(() -> {
            ecvCompany.setText(billModel.getInvoiceCompany());
            ecvTax.setText(billModel.getInvoiceTax());
            ecvBank.setText(billModel.getInvoiceBank());
            ecvAccount.setText(billModel.getInvoiceBankNumber());
            ecvType.setText(billModel.getInvoiceTypeName());
            ecvCompanyTel.setText(billModel.getInvoiceTel());
            ecvCompanyAddress.setText(billModel.getInvoiceAdd());
            ecvContract.setText(billModel.getRecPerson());
            ecvContractTel.setText(billModel.getRecTel());
            ecvContractAddress.setText(billModel.getRecAdd());
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        return params;
    }

    private void getBillType() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(getBillTypeParams(), NetConfig.address + PublicUrl.getPublic, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        List<PublicArray> list = new Gson().fromJson(result.getData(), new TypeToken<List<PublicArray>>() {
                        }.getType());
                        for (PublicArray array : list) {
                            if (array.equals(PublicCode.BillType) && array.getPlantPublicDetails() != null) {
                                billTypes.addAll(array.getPlantPublicDetails());
                                initPvBillType();
                            }
                        }
                        LoadingFinish(null);

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
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private void initPvBillType() {
        runOnUiThread(() -> {
            pvBilltype = new OptionsPickerBuilder(BillActivity.this, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    ecvType.setText(billTypes.get(options1).getPickerViewText());
                    billModel.setInvoiceType(billTypes.get(options1).getRecNo());
                    billModel.setInvoiceTypeName(billTypes.get(options1).getPickerViewText());
//                        car.setCarTypeName(drivers.get(options1).getPickerViewText());
                }
            }).setContentTextSize(18)//设置滚轮文字大小
                    .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                    .setSelectOptions(0)//默认选中项
                    .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                    .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                    .setLabels("", "", "")
                    .isDialog(true)
                    .setTitleText(ecvType.getTitle())
                    .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                    .build();
            pvBilltype.setPicker(billTypes);//一级选择器
            DialogUtil.setDialog(pvBilltype);
            pvBilltype.show();

        });

    }

    private List<NetParams> getBillTypeParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(publicFilter)));
        return params;
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        if (canSave()) {
            saveData();
        }
    }

    private void saveData() {
        new NetUtil(sendParams(), NetConfig.address + url, requstType, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e(CarDetailActivity.class.getName(), "data:" + string);
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        finish();
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
                Log.e("error", e.getMessage());
            }
        });
    }

    private List<NetParams> sendParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(billModel)));
        return params;
    }

    private boolean canSave() {
        llContent.getChildCount();
        for (int i = 0; i < llContent.getChildCount(); i++) {
            if (TextUtils.isEmpty(((EditClearView) llContent.getChildAt(i)).getText())) {
                Toast(((EditClearView) llContent.getChildAt(i)).getHint());
                return false;
            }
        }
        if (PhoneUtils.isNotValidChinesePhone(ecvContractTel.getText()) || PhoneUtils.isNotValidChinesePhone(ecvCompanyTel.getText())) {
            Toast(getString(R.string.error_phone));
            return false;
        }
        initBill();
        return true;
    }

    private void initBill() {
        billModel.setInvoiceCompany(ecvCompany.getText());
        billModel.setInvoiceTax(ecvTax.getText());
        billModel.setInvoiceBank(ecvBank.getText());
        billModel.setInvoiceBankNumber(ecvAccount.getText());
        billModel.setInvoiceTel(ecvCompanyTel.getText());
        billModel.setInvoiceAdd(ecvCompanyAddress.getText());
        billModel.setRecPerson(ecvContract.getText());
        billModel.setRecAdd(ecvContractAddress.getText());
        billModel.setRecTel(ecvContractTel.getText());
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
