package com.yyy.wrsf.mine.order;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.mine.order.bean.LogBean;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.VH> {
    private Context context;
    private List<LogBean> list;

    public LogAdapter(Context context, List<LogBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_log, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        if (position == 0) {
            holder.lineTop.setVisibility(View.INVISIBLE);
        }
        if (position == list.size() - 1) {
            holder.line.setVisibility(View.INVISIBLE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        if (list.get(position).getType() == 13) {
            holder.tvTitle.setText(context.getString(R.string.status_order_send));
        } else if (list.get(position).getType() == 4){
            holder.tvTitle.setText(context.getString(R.string.status_order_get));
        }else{
            holder.tvTitle.setText(list.get(position).getLogTitle());
        }
        holder.tvDate.setText(list.get(position).getCreateDate());
        holder.line.setLayoutParams(getParams((RelativeLayout.LayoutParams) holder.line.getLayoutParams(), true));
        holder.tvDetail.setText(list.get(position).getContent());
    }

    private RelativeLayout.LayoutParams getParams(RelativeLayout.LayoutParams params, boolean isShow) {
        params.height = context.getResources().getDimensionPixelSize(isShow ? R.dimen.dp_70 : R.dimen.dp_50);
        return params;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private View lineTop;
        private ImageView ivIcon;
        private TextView tvTitle;
        private TextView tvDate;
        private TextView tvDetail;
        private LinearLayout llContent;
        private TextView tvName;
        private TextView tvTel;
        private View line;

        public VH(View v) {
            super(v);
            lineTop = v.findViewById(R.id.line_top);
            line = v.findViewById(R.id.line);
            ivIcon = v.findViewById(R.id.iv_icon);
            tvTitle = v.findViewById(R.id.tv_title);
            tvDate = v.findViewById(R.id.tv_date);
            tvDetail = v.findViewById(R.id.tv_detail);
            llContent = v.findViewById(R.id.ll_content);
            tvName = v.findViewById(R.id.tv_name);
            tvTel = v.findViewById(R.id.tv_tel);
        }
    }
}
