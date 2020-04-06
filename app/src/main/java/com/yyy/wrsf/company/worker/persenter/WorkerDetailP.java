package com.yyy.wrsf.company.worker.persenter;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.company.worker.model.WorkerDetailM;
import com.yyy.wrsf.company.worker.view.IWorkweDetailV;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.net.member.MemberURL;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.worker.WorkerUrl;

import java.util.ArrayList;
import java.util.List;

public class WorkerDetailP implements IWorkDetailP {
    private IWorkweDetailV iWorkweDetailV;
    private WorkerDetailM workerDetailM;
    private Handler handler = new Handler();
    private boolean destroyFlag;

    public WorkerDetailP(IWorkweDetailV iWorkweDetailV) {
        this.iWorkweDetailV = iWorkweDetailV;
        workerDetailM = new WorkerDetailM();
    }

    @Override
    public void insert() {
        if (!TextUtils.isEmpty(workerDetailM.canSave(iWorkweDetailV.getContent()))) {
            iWorkweDetailV.toast(workerDetailM.canSave(iWorkweDetailV.getContent()));
            return;
        }
        iWorkweDetailV.startLoading();
        workerDetailM.Requset(getParams(), NetConfig.address + WorkerUrl.insertMember, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iWorkweDetailV.finishLoading(BaseApplication.getInstance().getString(R.string.common_save_success));
                        iWorkweDetailV.result(new Intent(), iWorkweDetailV.getCode());
                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iWorkweDetailV.finishLoading(error);
                    });
                }
            }
        });
    }

    @Override
    public void modify() {
        if (!TextUtils.isEmpty(workerDetailM.canSave(iWorkweDetailV.getContent()))) {
            iWorkweDetailV.toast(workerDetailM.canSave(iWorkweDetailV.getContent()));
            return;
        }
        iWorkweDetailV.startLoading();
        workerDetailM.Requset(getParams(), NetConfig.address + MemberURL.updateMember, RequstType.PUT, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iWorkweDetailV.finishLoading(BaseApplication.getInstance().getString(R.string.common_save_success));
                        iWorkweDetailV.result(new Intent()
                                        .putExtra("data", new Gson().toJson(iWorkweDetailV.getWorker()))
                                        .putExtra("pos", iWorkweDetailV.getPos())
                                , iWorkweDetailV.getCode());
                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iWorkweDetailV.finishLoading(error);
                    });
                }
            }
        });
    }

    @Override
    public void delete() {
        iWorkweDetailV.startLoading();
        workerDetailM.Requset(deleteParams(), NetConfig.address + MemberURL.stopMember, RequstType.GET, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iWorkweDetailV.finishLoading(BaseApplication.getInstance().getString(R.string.common_stop_success));
                        iWorkweDetailV.result(new Intent().putExtra("pos", iWorkweDetailV.getPos()), CodeUtil.DELETE);
                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iWorkweDetailV.finishLoading(error);
                    });
                }
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(iWorkweDetailV.getWorker())));
        return params;
    }

    private List<NetParams> deleteParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("recNo", iWorkweDetailV.getWorker().getRecNo() + ""));
        return params;
    }

    @Override
    public void setEdit(boolean b) {
        workerDetailM.Editable(iWorkweDetailV.getContent(), b, iWorkweDetailV.getCode());
    }

    public void detachView() {
        destroyFlag = true;
        this.iWorkweDetailV = null;
    }
}
