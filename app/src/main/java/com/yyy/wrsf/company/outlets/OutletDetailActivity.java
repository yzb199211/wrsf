package com.yyy.wrsf.company.outlets;

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
import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.OutletModel;
import com.yyy.wrsf.model.Sex;
import com.yyy.wrsf.utils.SexUtil;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.OnLeftClickListener;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.OptionsPickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnOptionsSelectListener;
import com.yyy.yyylibrary.pick.view.BasePickerView;
import com.yyy.yyylibrary.pick.view.OptionsPickerView;

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
    EditClearView ecvPerson;
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
    private OptionsPickerView pvStatus;

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
    }

    private void initStatusListener() {
        ecvStatus.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (pvStatus != null) {
                    initStatusListener();
                } else {
                    pvStatus.show();
                }
            }
        });
    }


    private void initData() {
        String data = getIntent().getStringExtra("data");
        outletModel = TextUtils.isEmpty(data) ? new OutletModel() : new Gson().fromJson(data, OutletModel.class);
        setOutletView();
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

    private void initPvSex() {
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

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        save();
    }

    private void save() {

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
}
