package com.yyy.wrsf.view.cycle.adapter;


import com.yyy.wrsf.view.cycle.listener.ViewHolder;

public interface HolderCreator<VH extends ViewHolder> {
    /**
     * 创建ViewHolder
     */
    VH createViewHolder();
}