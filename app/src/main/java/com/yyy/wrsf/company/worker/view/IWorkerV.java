package com.yyy.wrsf.company.worker.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.WorkerB;

import java.util.List;

public interface IWorkerV extends ILoadingV {
    Integer isShowStop();

    Integer getRoleType();

    String getFilter();

    void refreshList();
    void addList(List<WorkerB> list);
}
