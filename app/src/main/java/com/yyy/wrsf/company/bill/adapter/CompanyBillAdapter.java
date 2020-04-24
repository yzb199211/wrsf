package com.yyy.wrsf.company.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.beans.company.bill.CompanyBillB;
import com.yyy.wrsf.interfaces.OnItemClickListener;

import java.util.List;

public class CompanyBillAdapter extends RecyclerView.Adapter<CompanyBillAdapter.VH> {
    private Context context;
    private List<CompanyBillB> list;
    private OnItemClickListener onItemClickListener;

    public CompanyBillAdapter(Context context, List<CompanyBillB> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company_bill, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvName.setText(list.get(position).getCustomer());
        holder.tvTotal.setText(context.getString(R.string.common_rmb) + list.get(position).getContractTotal());
        holder.tvNum.setText(list.get(position).getOrderCount() + "");
        holder.tvUnpay.setText(context.getString(R.string.common_rmb) + list.get(position).getUnpaid());
        holder.tvType.setText(list.get(position).getCustomerTypeName());
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvTotal;
        private TextView tvNum;
        private TextView tvUnpay;
        private TextView tvType;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvUnpay = itemView.findViewById(R.id.tv_unpay);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvType = itemView.findViewById(R.id.tv_customer_type);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
