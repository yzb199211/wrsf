package com.yyy.wrsf.company.outlets;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.MemberModel;
import com.yyy.wrsf.model.OutletModel;
import com.yyy.wrsf.model.Sex;
import com.yyy.wrsf.model.filter.MemberFilterModel;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.SexUtil;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.utils.net.Result;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.utils.net.outlet.OutleUrl;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.textselect.TextMenuItem;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.OptionsPickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnOptionsSelectListener;
import com.yyy.yyylibrary.pick.view.BasePickerView;
import com.yyy.yyylibrary.pick.view.OptionsPickerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OutletDetailActivity extends AppCompatActivity {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_name)
    EditClearView ecvName;
    @BindView(R.id.ecv_address)
    EditClearView ecvAddress;
    @BindView(R.id.ecv_person)
    TextMenuItem ecvPerson;
    @BindView(R.id.ecv_send_tel)
    EditClearView ecvSendTel;
    @BindView(R.id.ecv_receive_tel)
    EditClearView ecvReceiveTel;
    @BindView(R.id.ecv_status)
    EditClearView ecvStatus;
    @BindView(R.id.ecv_remark)
    EditClearView ecvRemark;
    private OutletModel outletModel;
    private List<Sex> status = SexUtil.getShopStatus();
    private List<MemberModel> member;
    private OptionsPickerView pvStatus;
    private OptionsPickerView pvPerson;
    private int pos;
    private MemberFilterModel memberFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initTop();
        initData();
        initStatusListener();
        initPersonListener();
    }


    private void initStatusListener() {
        ecvStatus.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (pvStatus == null) {
                    initPvStatus();
                } else {
                    pvStatus.show();
                }
            }
        });
    }

    private void initPersonListener() {
        ecvPerson.getTextView().setOnClickListener((View v) -> {
            if (member == null) {
                getMember();
            } else {
                pvPerson.show();
            }
        });
    }


    private void initData() {
        String data = getIntent().getStringExtra("data");
        pos = getIntent().getIntExtra("pos", -1);
        outletModel = TextUtils.isEmpty(data) ? new OutletModel() : new Gson().fromJson(data, OutletModel.class);
        initMemberFilter();
        setOutletView();
    }

    private void initMemberFilter() {
        memberFilter = new MemberFilterModel();
        memberFilter.setStopYesno(0);
    }

    private void setOutletView() {
        ecvName.setText(outletModel.getShopName());
        ecvAddress.setText(outletModel.getDetailAdd());
        ecvPerson.setText(outletModel.getPersonName());
        ecvSendTel.setText(outletModel.getSendTel());
        ecvReceiveTel.setText(outletModel.getRecTel());
        ecvRemark.setText(outletModel.getRemark());
        ecvStatus.setText(outletModel.getShopStatus() == 0 ? status.get(0).getSex() : status.get(1).getSex());
    }

    private void initTop() {
        topView.setOnLeftClickListener(new OnLeftClickListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
    }

    private void initPvStatus() {
        pvStatus = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ecvStatus.setText(status.get(options1).getPickerViewText());
                outletModel.setShopStatus(status.get(options1).getId());
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
        pvStatus.setPicker(status);//一级选择器
        setDialog(pvStatus);
        pvStatus.show();
    }

    private void initPvPerson() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pvPerson = new OptionsPickerBuilder(OutletDetailActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        ecvPerson.setText(member.get(options1).getPickerViewText());
                        outletModel.setPerson(member.get(options1).getRecNo());
                        outletModel.setPersonName(member.get(options1).getPickerViewText());
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
                pvPerson.setPicker(member);//一级选择器
                setDialog(pvPerson);
                pvPerson.show();
            }
        });

    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        save();
    }

    private void getMember() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(memberParams(), NetConfig.address + MemberURL.getShopPersonList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    LoadingFinish(null);
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        if (StringUtil.isNotEmpty(result.getData())) {
                            member = new Gson().fromJson(result.getData(), new TypeToken<List<MemberModel>>() {
                            }.getType());
                            initPvPerson();
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


    private List<NetParams> memberParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(memberFilter)));
        return params;
    }

    private void save() {
        LoadingDialog.showDialogForLoading(this);
        new NetUtil(saveParams(), NetConfig.address + OutleUrl.updateShop, RequstType.PUT, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        setResult(CodeUtil.MODIFY, new Intent().putExtra("data", new Gson().toJson(outletModel)).putExtra("pos", pos));
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
                e.printStackTrace();
                LoadingFinish(e.getMessage());
            }
        });
    }

    private List<NetParams> saveParams() {
        setOutlet();
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(outletModel)));
        return params;
    }

    private void setOutlet() {
        outletModel.setRecTel(ecvReceiveTel.getText());
        outletModel.setSendTel(ecvSendTel.getText());
        outletModel.setRemark(ecvRemark.getText());
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
