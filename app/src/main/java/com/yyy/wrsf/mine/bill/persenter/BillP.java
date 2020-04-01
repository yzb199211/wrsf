package com.yyy.wrsf.mine.bill.persenter;

import android.os.Handler;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.mine.bill.model.BillM;
import com.yyy.wrsf.mine.bill.view.IBillV;
import com.yyy.wrsf.beans.BillBean;
import com.yyy.wrsf.utils.net.bill.BillUrl;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;

import java.util.ArrayList;
import java.util.List;

public class BillP implements IBillP {
    private IBillV iBillV;
    private BillM billM;
    private Handler handler = new Handler();
    private boolean destroyFlag;

    public BillP(IBillV iBillV) {
        this.iBillV = iBillV;
        billM = new BillM();
    }

    @Override
    public void getData() {
        iBillV.startLoading();
        billM.Requset(getParams(), NetConfig.address + BillUrl.getBill, RequstType.GET, new OnResultListener() {
            @Override
            public void onSuccess(String string) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iBillV.finishLoading(null);
                        if (!TextUtils.isEmpty(string)) {
                            iBillV.setBill(new Gson().fromJson(string, BillBean.class));
                            iBillV.setRequstType(RequstType.PUT);
                            iBillV.setUrl(BillUrl.update);
                        } else {
                            billM.Editable(iBillV.getContent(), true);
                            iBillV.setEditAble(true);
                        }
                    });
                }
            }

            @Override
            public void onFail(String string) {
                if (!destroyFlag)
                    handler.post(() -> {
                        iBillV.finishLoading(string);
                    });
            }
        });

    }

    @Override
    public void save() {
        if (TextUtils.isEmpty(billM.canSave(iBillV.getContent()))) {
            iBillV.startLoading();
            billM.Requset(getSaveParams(), NetConfig.address + iBillV.getUrl(), iBillV.getRequstType(), new OnResultListener() {
                @Override
                public void onSuccess(String string) {
                    if (!destroyFlag) {
                        handler.post(() -> {
                            iBillV.finishLoading(BaseApplication.getInstance().getString(R.string.common_save_success));
                            iBillV.setEditAble(false);
                            billM.Editable(iBillV.getContent(), false);
                        });
                    }
                }

                @Override
                public void onFail(String string) {
                    if (!destroyFlag) {
                        handler.post(() -> {
                            iBillV.finishLoading(string);
                        });
                    }
                }
            });
        } else {
            iBillV.toast(billM.canSave(iBillV.getContent()));
        }
    }

    private List<NetParams> getSaveParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("params", new Gson().toJson(iBillV.getBill())));
        return params;
    }

    @Override
    public void setEdit(boolean b) {
        billM.Editable(iBillV.getContent(), b);
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.iBillV = null;
    }
}
