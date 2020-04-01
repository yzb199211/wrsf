package com.yyy.wrsf.mine.notice;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.NoticeB;
import com.yyy.wrsf.utils.DateUtil;

import java.util.List;

public class NoticeAdpter extends RecyclerView.Adapter<NoticeAdpter.VH> {
    Context context;
    List<NoticeB> list;
    OnItemClickListener onItemClickListener;

    public NoticeAdpter(Context context, List<NoticeB> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notice, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.setIsRecyclable(false);
        holder.tvName.setText(list.get(position).getCreateName());
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvTime.setText(TextUtils.isEmpty(list.get(position).getCreateDate()) ? "" : DateUtil.isToday(list.get(position).getCreateDate()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvName;
        private TextView tvTime;
        private ImageView ivLogo;

        public VH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_title);
            tvName = v.findViewById(R.id.tv_name);
            ivLogo = v.findViewById(R.id.iv_logo);
            tvTime = v.findViewById(R.id.tv_time);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
