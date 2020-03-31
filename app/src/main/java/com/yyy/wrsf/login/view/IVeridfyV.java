package com.yyy.wrsf.login.view;

import com.yyy.wrsf.base.view.ILoadingV;

public interface IVeridfyV extends ILoadingV {
    String getTel();

    String getVeridfyType();

    boolean isEnabled();

    void startCount();
}
