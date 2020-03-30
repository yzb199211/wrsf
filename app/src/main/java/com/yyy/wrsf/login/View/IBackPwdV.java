package com.yyy.wrsf.login.View;

public interface IBackPwdV extends IVeridfyV {
    String getCode();

    void backLogin();

    String getPwd();

    String getPwdComfir();
}
