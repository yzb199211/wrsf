package com.yyy.wrsf.company.worker.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.WorkerB;

import java.util.List;

public interface IWorkerV extends ILoadingV {
    int isShowStop();

    boolean isShowAdmin();

    String getFilter();

    void refreshList(List<WorkerB> list);
}
