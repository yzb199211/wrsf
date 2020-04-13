package com.yyy.wrsf.mine.address.view;

import com.yyy.wrsf.base.view.ILoadingV;
import com.yyy.wrsf.beans.address.AddressB;

import java.util.List;

public interface IAddressV extends ILoadingV {
    void getTabs();

    void refreshList();

    void addItem(AddressB item);

    void addList(List<AddressB> list);

    String getUrl();

    String deleteUrl();

    void delete(int pos);

}
