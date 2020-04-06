package com.yyy.wrsf.company.worker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseActivity;
import com.yyy.wrsf.base.BasePickActivity;
import com.yyy.wrsf.beans.WorkerB;
import com.yyy.wrsf.beans.publicm.PublicBean;
import com.yyy.wrsf.beans.publicm.Sex;
import com.yyy.wrsf.company.worker.persenter.WorkerDetailP;
import com.yyy.wrsf.company.worker.persenter.WorkerP;
import com.yyy.wrsf.company.worker.view.IWorkweDetailV;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.enums.StatusEnum;
import com.yyy.wrsf.mine.bill.BillActivity;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.PublicCode;
import com.yyy.wrsf.utils.SexUtil;
import com.yyy.wrsf.view.editclear.EditClearView;
import com.yyy.wrsf.view.topview.TopView;
import com.yyy.yyylibrary.pick.builder.OptionsPickerBuilder;
import com.yyy.yyylibrary.pick.listener.OnOptionsSelectListener;
import com.yyy.yyylibrary.pick.view.OptionsPickerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkerDetailActivity extends BasePickActivity implements IWorkweDetailV {

    @BindView(R.id.top_view)
    TopView topView;
    @BindView(R.id.ecv_name)
    EditClearView ecvName;
    @BindView(R.id.ecv_tel)
    EditClearView ecvTel;
    @BindView(R.id.ecv_petname)
    EditClearView ecvPetname;
    @BindView(R.id.ecv_sex)
    EditClearView ecvSex;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.btn_add)
    TextView btnAdd;
    private OptionsPickerView pvSex;
    private WorkerB workerB;
    private WorkerDetailP workerP;
    private int pos;
    private List<Sex> sexes;
    private boolean editable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_detail);
        ButterKnife.bind(this);
        workerP = new WorkerDetailP(this);
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
        sexes = SexUtil.getSexs();
        pos = getIntent().getIntExtra("pos", -1);
        String data = getIntent().getStringExtra("data");
        workerB = TextUtils.isEmpty(data) ? new WorkerB() : new Gson().fromJson(data, WorkerB.class);
    }

    private void initView() {
        initTop();
        initSex();
        initStatus();
        if (pos == -1) {
            workerP.setEdit(true);
            btnAdd.setText(getString(R.string.common_save));
            ecvSex.setText(sexes.get(0).getPickerViewText());
            workerB.setMemberSex(sexes.get(0).getSex());
        } else {
            workerP.setEdit(false);
            setView();
        }
    }

    private void initTop() {
        topView.setOnLeftClickListener(() -> {
            finish();
        });
        initTopRight();
    }

    private void initTopRight() {
        if (pos != -1) {
            topView.setOnRightClickListener(() -> {
                workerP.delete();
            });
        } else {
            topView.setRightTvShow(false);
        }
    }

    private void initStatus() {

    }


    private void initSex() {
        initPvSex();
        ecvSex.setOnItemClickListener(i -> {
            pvSex.show();
        });
    }

    private void initPvSex() {
        pvSex = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ecvSex.setText(sexes.get(options1).getPickerViewText());
                workerB.setMemberSex(sexes.get(options1).getPickerViewText());
            }
        }).setContentTextSize(18)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .isDialog(true)
                .setTitleText(ecvSex.getTitle())
                .setBgColor(0xFFFFFFFF) //设置外部遮罩颜色
                .build();
        pvSex.setPicker(sexes);//一级选择器
        setDialog(pvSex);
    }

    private void setView() {
        ecvName.setText(workerB.getMemberName());
        ecvTel.setText(workerB.getMemberTel());
        ecvPetname.setText(workerB.getMemberPetname());
        ecvSex.setText(workerB.getMemberSex());
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        if (pos == -1) workerP.insert();
        else if (!editable) {
            workerP.setEdit(true);
            editable = true;
            btnAdd.setText(getString(R.string.common_save));
        } else {
            workerP.modify();
        }
    }

    @Override
    public int getCode() {
        return pos == -1 ? CodeUtil.REFRESH : CodeUtil.MODIFY;
    }

    @Override
    public WorkerB getWorker() {
        workerB.setMemberName(ecvName.getText());
        workerB.setMemberTel(ecvTel.getText());
        workerB.setMemberPetname(ecvPetname.getText());
        return workerB;
    }

    @Override
    public LinearLayout getContent() {
        return llContent;
    }

    @Override
    public void result(Intent intent, int resultCode) {
        setResult(resultCode, intent);
        finish();
    }

    @Override
    public void startLoading() {
        LoadingDialog.showDialogForLoading(this);
    }

    @Override
    public void finishLoading(@Nullable String s) {
        LoadingFinish(s);
    }

    @Override
    public void toast(String s) {
        Toast(s);
    }

    @Override
    public int getPos() {
        return pos;
    }

    @Override
    protected void onDestroy() {
        workerP.detachView();
        super.onDestroy();
    }
}
