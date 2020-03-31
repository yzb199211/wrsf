package com.yyy.wrsf.login.view;

public interface IBackPwdV extends IVeridfyV {
    String getCode();

    void backLogin();

    String getPwd();

    String getPwdComfir();
}
