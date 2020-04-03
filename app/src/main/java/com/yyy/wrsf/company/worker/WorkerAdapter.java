package com.yyy.wrsf.company.worker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.beans.WorkerB;
import com.yyy.wrsf.interfaces.OnItemClickListener;

import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.VH> {
    private Context context;
    private List<WorkerB> list;
    private OnItemClickListener onItemClickListener;

    public WorkerAdapter(Context context, List<WorkerB> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_worker, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvName.setText(list.get(position).getMemberName());
        holder.tvTel.setText(list.get(position).getMemberTel());
        holder.tvAdmin.setText(list.get(position).isAdmin() ? context.getString(R.string.worker_admin) : context.getString(R.string.worker_normal));
        holder.tvAdmin.setTextColor(list.get(position).isAdmin() ? context.getColor(R.color.order_yellow) : context.getColor(R.color.text_gray));
        holder.tvType.setText(list.get(position).getStopYesno() == 0 ? context.getString(R.string.common_normal) : context.getString(R.string.common_stop));
        holder.tvType.setTextColor(list.get(position).getStopYesno() == 0 ? context.getColor(R.color.text_green) : context.getColor(R.color.text_red));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvType;
        private TextView tvTel;
        private TextView tvAdmin;

        public VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvAdmin = v.findViewById(R.id.tv_admin);
            tvTel = v.findViewById(R.id.tv_tel);
            tvType = v.findViewById(R.id.tv_type);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
