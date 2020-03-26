package com.yyy.wrsf.common.company;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.R;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.model.company.CompanyModel;
import com.yyy.wrsf.model.filter.ShipCompany;
import com.yyy.wrsf.utils.PxUtil;
import com.yyy.wrsf.utils.SharedPreferencesHelper;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.utils.Toasts;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.NetUtil;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.net.ResponseListener;
import com.yyy.wrsf.utils.net.net.Result;
import com.yyy.wrsf.utils.net.bill.BillUrl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

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
        if (companys.size() > 0) {
            companys.clear();
            refreshList();
        }
        getData();
    }

    @Override
    public void dismiss() {
        EventBus.getDefault().unregister(this);
        super.dismiss();
    }

    private void getData() {
        LoadingDialog.showDialogForLoading((Activity) context);
        new NetUtil(getParams(), NetConfig.address + BillUrl.getTransCompanyList, RequstType.POST, new ResponseListener() {
            @Override
            public void onSuccess(String string) {
                try {
                    LoadingFinish(null);
                    Result result = new Result(string);
                    if (result.isSuccess()) {
                        if (onLoadingListener != null) {
                            onLoadingListener.onLoading(true);
                        }
                        String data = result.getData();
                        if (!TextUtils.isEmpty(data)) {
                            List<CompanyModel> list = new Gson().fromJson(data, new TypeToken<List<CompanyModel>>() {
                            }.getType());
                            companys.addAll(list);
                            refreshList();
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
                LoadingFinish(e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private OnCompanySelectListener onCompanySelectListener;

    private void refreshList() {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter == null) {
                    adapter = new CompanyAdapter(context, companys);
                    adapter.setOnItemClickListener((int pos) -> {
                        if (onCompanySelectListener != null) {
                            onCompanySelectListener.onSelect(companys.get(pos));
                            dismiss();
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
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

    public void setOnCompanySelectListener(OnCompanySelectListener onCompanySelectListener) {
        this.onCompanySelectListener = onCompanySelectListener;
    }
}
