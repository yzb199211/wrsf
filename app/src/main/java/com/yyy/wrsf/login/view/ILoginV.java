package com.yyy.wrsf.login.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.bean.MemberBean;

public interface ILoginV extends ILoadingV {

    String getUser();

    String getPwd();

    void go2Main();

    void setPreference(MemberBean model);

    void judgeDownloadPermission();

}
