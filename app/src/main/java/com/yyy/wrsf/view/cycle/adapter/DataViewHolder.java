package com.yyy.wrsf.view.cycle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.view.cycle.bean.DataBean;
import com.yyy.wrsf.view.cycle.listener.ViewHolder;
import com.yyy.wrsf.view.cycle.util.ImageLoaderUtil;


public class DataViewHolder implements ViewHolder<DataBean> {
    private ImageView mImageView;
    private TextView mTvDescribe;

    @Override
    public View createView(Context context, int position) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.item_circle, null);
        mImageView = view.findViewById(R.id.banner_image);
        mTvDescribe = view.findViewById(R.id.tv_describe);
        return view;
    }

    @Override
    public void onBind(final Context context, DataBean data, final int position, final int size) {

        final DataBean dataBean = (DataBean) data;
        ImageLoaderUtil.loadImg(mImageView, dataBean.getUrl(), R.mipmap.icon_logo);
        mTvDescribe.setText(dataBean.getDescribe());
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("pos", position + "");
            }
        });
    }
}