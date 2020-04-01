package com.yyy.wrsf.company.month;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.beans.month.CustomerMonthB;
import com.yyy.wrsf.view.cycle.util.ImageLoaderUtil;

import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.VH> {
    private Context context;
    private List<CustomerMonthB> list;
    private OnItemClickListener onItemClickListener;

    public MonthAdapter(Context context, List<CustomerMonthB> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_month, parent, false);
        return new MonthAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvName.setText(list.get(position).getCompanyName());
        holder.tvTel.setText(list.get(position).getContractTel());
        ImageLoaderUtil.loadImg(holder.ivLogo, "");
        if (list.get(position).getYskTotal() != null) {
            holder.tvTotal.setText(context.getString(R.string.month_total) + list.get(position).getYskTotal().toString());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvTel;
        private TextView tvTotal;
        private ImageView ivLogo;

        public VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvTel = v.findViewById(R.id.tv_tel);
            tvTotal = v.findViewById(R.id.tv_total);
            ivLogo = v.findViewById(R.id.iv_logo);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
