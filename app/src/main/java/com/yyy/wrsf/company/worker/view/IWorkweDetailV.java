package com.yyy.wrsf.company.worker.view;

import android.content.Intent;
import android.widget.LinearLayout;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.WorkerB;
import com.yyy.wrsf.utils.net.net.RequstType;

public interface IWorkweDetailV extends ILoadingV {
    int getCode();

    WorkerB getWorker();

    LinearLayout getContent();

    void result(Intent intent, int resultCode);
}
