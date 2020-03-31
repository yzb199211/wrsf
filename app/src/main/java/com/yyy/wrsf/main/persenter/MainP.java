package com.yyy.wrsf.main.persenter;

import com.yyy.wrsf.main.model.MainM;
import com.yyy.wrsf.main.view.IMainV;

public class MainP implements IMainP {
    private IMainV iMainV;
    private MainM mainM;
    //    private Handler handler = new Handler();
    private boolean destroyFlag;

    public MainP(IMainV iMainV) {
        this.iMainV = iMainV;
        mainM = new MainM();
    }

    @Override
    public void judgeCompany() {
        if (!destroyFlag) {
            if (mainM.isCompany(iMainV.getAuthority())) {
                iMainV.go2Company();
            } else {
                iMainV.go2RegisterCompany();
            }
        }
    }

    public void detachView() {
        destroyFlag = true;
        this.iMainV = null;
    }


}
