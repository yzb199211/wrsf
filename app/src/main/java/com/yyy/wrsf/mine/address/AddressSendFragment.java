package com.yyy.wrsf.mine.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.yyy.wrsf.R;
import com.yyy.wrsf.base.BaseFragment;
import com.yyy.wrsf.beans.address.AddressB;
import com.yyy.wrsf.dialog.JudgeDialog;
import com.yyy.wrsf.dialog.LoadingDialog;
import com.yyy.wrsf.mine.address.persenter.AddressP;
import com.yyy.wrsf.mine.address.view.IAddressV;
import com.yyy.wrsf.utils.CodeUtil;
import com.yyy.wrsf.utils.net.address.AddressUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddressSendFragment extends BaseFragment implements IAddressV {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_add)
    TextView btnAdd;

    private List<AddressB> addressList = new ArrayList<>();
    private AddressAdapter addressAdapter;
    private AddressP addressP;
    private JudgeDialog judgeDialog;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        initAdapter();
    }

    private void initAdapter() {
        addressAdapter = new AddressAdapter(getActivity(), addressList);
        addressAdapter.setOnEditListener(pos -> {
            startActivityForResult(new Intent()
                            .setClass(getActivity(), AddressDetailSendActivity.class)
                            .putExtra("data", new Gson().toJson(addressList.get(pos)))
                            .putExtra("pos", pos)
                            .putExtra("code", CodeUtil.MODIFY)
                    , CodeUtil.MODIFY);
        });
        addressAdapter.setOnDeleteListener(pos -> {
            confirmDelete(pos);
//            addressP.delete(addressList.get(pos).getRecNo(), pos);
        });
        recyclerView.setAdapter(addressAdapter);
    }

    private void confirmDelete(int pos) {
        if (judgeDialog == null) {
            judgeDialog = new JudgeDialog(getActivity());
            judgeDialog.setContent(getString(R.string.common_dialog_delete));
        }
        judgeDialog.setOnCloseListener(new JudgeDialog.OnCloseListener() {
            @Override
            public void onClick(boolean confirm) {
                if (confirm) {
                    addressP.delete(addressList.get(pos).getRecNo(), pos);
                }
            }
        });
        judgeDialog.show();

    }

    @Override
    public void getTabs() {
    }

    @Override
    public void refreshList() {
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void addItem(AddressB item) {

    }

    @Override
    public void addList(List<AddressB> list) {
        addressList.addAll(list);
    }

    @Override
    public String getUrl() {
        return AddressUrl.getAddressSendList;
    }

    @Override
    public String deleteUrl() {
        return AddressUrl.deleteAddressSend;
    }

    @Override
    public void delete(int pos) {
        addressList.remove(pos);
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void startLoading() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    @Override
    public void finishLoading(@Nullable String s) {
        LoadingFinish(s);
    }

    @Override
    public void toast(String s) {
        Toast(s);
    }

    @Override
    public void onDestroy() {
        addressP.detachView();
        super.onDestroy();
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        startActivityForResult(new Intent()
                        .setClass(getActivity(), AddressDetailSendActivity.class)
                        .putExtra("code", CodeUtil.ADD)
                , CodeUtil.ADD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CodeUtil.ADD) {
            addressList.clear();
            refreshList();
            addressP.getData();
        } else if (resultCode == CodeUtil.MODIFY) {
            if (data != null) {
                int pos = data.getIntExtra("pos", -1);
                if (pos > -1 && pos < addressList.size()) {
                    AddressB item = new Gson().fromJson(data.getStringExtra("data"), AddressB.class);
                    if (addressList.get(pos).getIsDefault() != 1 && item.getIsDefault() == 1) {
                        resetDefault();
                    }
                    addressList.set(pos, item);
//                    addresses.get(pos) = ;
                    refreshList();
                }
            }
        }
    }
    private void resetDefault() {
        for (AddressB item : addressList) {
            if (item.getIsDefault() == 1) {
                item.setIsDefault(0);
                return;
            }
        }
    }
}
