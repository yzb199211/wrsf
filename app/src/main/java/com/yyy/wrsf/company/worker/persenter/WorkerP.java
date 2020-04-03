package com.yyy.wrsf.company.worker.persenter;

import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyy.wrsf.beans.WorkerB;
import com.yyy.wrsf.company.worker.model.WorkerM;
import com.yyy.wrsf.company.worker.view.IWorkerV;
import com.yyy.wrsf.interfaces.OnResultListener;
import com.yyy.wrsf.utils.net.net.NetConfig;
import com.yyy.wrsf.utils.net.net.NetParams;
import com.yyy.wrsf.utils.net.net.PagerRequestBean;
import com.yyy.wrsf.utils.net.net.RequstType;
import com.yyy.wrsf.utils.net.worker.WorkerUrl;

import java.util.ArrayList;
import java.util.List;

public class WorkerP implements IWorkerP {
    private IWorkerV iWorkerV;
    private WorkerM workerM;
    private Handler handler = new Handler();
    private boolean destroyFlag;
    private PagerRequestBean pager;
    private int pageSize = 500;
    private int pageIndex = 0;

    public WorkerP(IWorkerV iWorkerV) {
        this.iWorkerV = iWorkerV;
        workerM = new WorkerM();
        initPager();
    }

    private void initPager() {
        pager = new PagerRequestBean();
        pager.setPageIndex(pageIndex);
        pager.setPageSize(pageSize);
    }

    private void initParams() {
        pager.setQueryParam(workerM.getFilter(iWorkerV.getFilter(), iWorkerV.isShowStop(), iWorkerV.getRoleType()));
    }

    @Override
    public void getWorker() {
        initParams();
        iWorkerV.startLoading();
        workerM.Requset(getParams(), NetConfig.address + WorkerUrl.getPageList, RequstType.POST, new OnResultListener() {
            @Override
            public void onSuccess(String data) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iWorkerV.finishLoading(null);
                        iWorkerV.addList(new Gson().fromJson(data, new TypeToken<List<WorkerB>>() {
                        }.getType()));
                        iWorkerV.refreshList();
                    });
                }
            }

            @Override
            public void onFail(String error) {
                if (!destroyFlag) {
                    handler.post(() -> {
                        iWorkerV.finishLoading(error);
                    });
                }
            }
        });
    }

    private List<NetParams> getParams() {
        List<NetParams> params = new ArrayList<>();
        params.add(new NetParams("param", new Gson().toJson(pager)));
        return params;
    }

    public void detachView() {
        destroyFlag = true;
        this.iWorkerV = null;
    }
}
