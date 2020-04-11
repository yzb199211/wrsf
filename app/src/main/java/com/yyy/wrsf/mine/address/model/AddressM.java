package com.yyy.wrsf.mine.address.model;

import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.beans.TabB;

import java.util.ArrayList;
import java.util.List;

public class AddressM {

    public List<TabB> getTabs() {
        List<TabB> tabs = new ArrayList<>();
        tabs.add(new TabB(0, BaseApplication.getInstance().getString(R.string.address_title_receive)));
        tabs.add(new TabB(1, BaseApplication.getInstance().getString(R.string.address_title_send)));
        return tabs;
    }

}
