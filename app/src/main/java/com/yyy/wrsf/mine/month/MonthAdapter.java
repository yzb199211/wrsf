package com.yyy.wrsf.mine.month;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.beans.month.MonthB;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.utils.StringUtil;

import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.VH> {
    private Context context;
    private List<MonthB> list;
    private OnItemClickListener onItemClickListener;

    public MonthAdapter(Context context, List<MonthB> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_month_apply, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvTransCompany.setText(list.get(position).getTransCompanyName());
        holder.tvStatus.setText(StringUtil.getMonthApplyStatus(list.get(position).getCheckFinish()));
        holder.tvStatus.setTextColor(StringUtil.getMonthApplyStatusColor(list.get(position).getCheckFinish()));
        holder.tvCompany.setText(list.get(position).getCompanyName());
        holder.tvPerson.setText(list.get(position).getPerson());
        holder.tvTel.setText(list.get(position).getContractTel());
        holder.tvBusLic.setText(list.get(position).getZhiZhao());
        holder.tvEdit.setOnClickListener(view -> {
            if (onItemClickListener != null && list.get(position).getCheckFinish()==0) onItemClickListener.onItemClick(position);
//            if (onItemClickListener != null) onItemClickListener.onItemClick(position);
        });
        holder.tvEdit.setVisibility(list.get(position).getCheckFinish()==0?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvTransCompany;
        private TextView tvStatus;
        private TextView tvCompany;
        private TextView tvPerson;
        private TextView tvTel;
        private TextView tvBusLic;
        private TextView tvEdit;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvTransCompany = itemView.findViewById(R.id.tv_trans_company);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvCompany = itemView.findViewById(R.id.tv_company);
            tvPerson = itemView.findViewById(R.id.tv_person);
            tvTel = itemView.findViewById(R.id.tv_tel);
            tvBusLic = itemView.findViewById(R.id.tv_business_license);
            tvEdit = itemView.findViewById(R.id.tv_edit);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
