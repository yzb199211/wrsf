package com.yyy.wrsf.company.worker.model;

import android.text.TextUtils;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.beans.filter.WorkerFilterB;

public class WorkerM extends BaseM {

    public WorkerFilterB getFilter(String orderNo, int stopYesNo, boolean admin) {
        WorkerFilterB workerFilterB = new WorkerFilterB();
        workerFilterB.setStopYesno(stopYesNo);
        workerFilterB.setMemberName(TextUtils.isEmpty(orderNo) ? null : orderNo);
        workerFilterB.setAdmin(admin);
        return workerFilterB;
    }
}
