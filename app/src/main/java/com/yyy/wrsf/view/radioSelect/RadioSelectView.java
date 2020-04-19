package com.yyy.wrsf.view.radioSelect;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.application.BaseApplication;
import com.yyy.wrsf.beans.TabB;
import com.yyy.wrsf.enums.ContractStatusEnum;
import com.yyy.wrsf.enums.util.EnumEntity;
import com.yyy.wrsf.enums.util.EnumUtils;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.view.recycle.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RadioSelectView extends RecyclerView {
    private Context context;
    private List<TabB> tabBS;
    private RadioSelectAdapter adapter;
    private int current;
    private OnItemClickListener onItemClickListener;

    public RadioSelectView(@NonNull Context context) {
        this(context, null);
    }

    public RadioSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        initList();
        initFirst();
        initAdapter();
        initRecycle();
    }

    private void initFirst() {
        current = 0;
        if (tabBS.size() > 0) {
            tabBS.get(current).setChecked(true);
        }
    }

    private void initList() {
        tabBS = new ArrayList<>();
        tabBS.add(new TabB(null, BaseApplication.getInstance().getString(R.string.common_all)));
        List<EnumEntity> list = EnumUtils.getEnumList(ContractStatusEnum.values(), ContractStatusEnum::getStatus, ContractStatusEnum::getDesc);
        for (EnumEntity enumEntity : list) {
            if (enumEntity.getId() != ContractStatusEnum.CANCEL.getStatus())
                tabBS.add(new TabB(enumEntity.getId(), enumEntity.getName()));
        }

    }

    private void initAdapter() {
        adapter = new RadioSelectAdapter(context, tabBS);
        adapter.setOnItemClickListener(pos -> {
            current = (pos == current ? current : setCheck(pos));
        });
    }

    private void initRecycle() {
        addItemDecoration(new DividerGridItemDecoration(context));
        setAdapter(adapter);
        setLayoutManager(new GridLayoutManager(context, 4));
    }

    private int setCheck(int pos) {
        tabBS.get(current).setChecked(false);
        adapter.notifyItemChanged(current);
        current = pos;
        tabBS.get(current).setChecked(true);
        adapter.notifyItemChanged(current);
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(pos);
        }
        return pos;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
