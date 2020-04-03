package com.yyy.wrsf.company.worker.model;

import android.text.TextUtils;

import com.yyy.wrsf.base.model.BaseM;
import com.yyy.wrsf.beans.filter.WorkerFilterB;
import com.yyy.wrsf.utils.StringUtil;

public class WorkerM extends BaseM {

    public WorkerFilterB getFilter(String filter, Integer stopYesNo, Integer role) {
        if (StringUtil.isNotEmpty(filter) || stopYesNo != null || role != null) {
            WorkerFilterB workerFilterB = new WorkerFilterB();
            workerFilterB.setStopYesno(stopYesNo);
            workerFilterB.setMemberName(TextUtils.isEmpty(filter) ? null : filter);
            workerFilterB.setRoleId(role);
            return workerFilterB;
        } else
            return null;
    }
}
