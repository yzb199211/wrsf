package com.yyy.wrsf.common.company;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.model.CompanyModel;
import com.yyy.wrsf.model.filter.ShipCompany;
import com.yyy.wrsf.utils.PxUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.NetConfig;
import com.yyy.wrsf.utils.net.NetParams;
import com.yyy.wrsf.utils.net.NetUtil;
import com.yyy.wrsf.utils.net.RequstType;
import com.yyy.wrsf.utils.net.ResponseListener;
import com.yyy.wrsf.utils.net.bill.BillUrl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanySelect extends PopupWindow {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Context context;
    private SharedPreferencesHelper preferencesHelper;
    private View view;

    private List<CompanyModel> companys = new ArrayList<>();
    private CompanyAdapter adapter;
    private ShipCompany companyFilter;
    private OnLoadingListener onLoadingListener;

    public CompanySelect(Context context) {
        super(context);
        this.context = context;
        preferencesHelper = new SharedPreferencesHelper(context, context.getString(R.string.preferenceCache));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_company_select, null);
        setHeight(((Activity) context).getWindowManager().getDefaultDisplay().getHeight());
        setWidth(PxUtil.getWidth(context));
        ButterKnife.bind(this, view);
        setContentView(view);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        CompanySelect.this.dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refresh(ShipCompany companyFilter) {
        this.companyFilter = companyFilter;
        Log.e("hi", "hi");
        getData();
    }

    @Override
    public void dismiss() {
        Log.e("hi", "hi2");
        EventBus.getDefault().unregister(this);
        super.dismiss();
    }

    private void getData() {
        LoadingDialog.showDialogForLoading((Activity) context);
        new NetUtil(getParams(), NetConfig.address + BillUrl.getTransCompanyList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                Log.e("data", string);
                LoadingFinish(null);
                if (onLoadingListener != null) {
                    onLoadingListener.onLoading(true);
                }
            }

            @Override
            public void onFail(Exception e) {
                LoadingFinish(e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(companyFilter)));
        return params;
    }

    private void LoadingFinish(String msg) {
        ((Activity) context).runOnUiThread(new Runnable() {
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
        Toasts.showShort(context, msg);
    }

    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
        this.onLoadingListener = onLoadingListener;
    }

}
