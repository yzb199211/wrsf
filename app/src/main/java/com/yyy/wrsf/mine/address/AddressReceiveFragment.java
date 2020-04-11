package com.yyy.wrsf.mine.address;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseFragment;
import com.yyy.wrsf.beans.address.AddressB;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.mine.address.persenter.AddressP;
import com.yyy.wrsf.mine.address.view.IAddressV;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddressReceiveFragment extends BaseFragment implements IAddressV {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_add)
    TextView btnAdd;

    private List<AddressB> addressList;
    private AddressAdapter addressAdapter;
    private AddressP addressP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressP = new AddressP(this);
        addressP.getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        initList();
    }

    private void initList() {

    }

    @Override
    public void getTabs() {

    }

    @Override
    public void refreshList() {

    }

    @Override
    public void addItem(AddressB item) {

    }

    @Override
    public void addList(List<AddressB> list) {

    }

    @Override
    public void startLoading() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    @Override
    public void finishLoading(@Nullable String s) {
        finishLoading(s);
    }

    @Override
    public void toast(String s) {
        Toast(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
