package com.yyy.wrsf.login.view;

import com.yyy.wrsf.bean.MemberBean;

public interface ILoginCodeV extends IVeridfyV {
    String getCode();

    void go2Main();

    void setPreference(MemberBean model);
}
