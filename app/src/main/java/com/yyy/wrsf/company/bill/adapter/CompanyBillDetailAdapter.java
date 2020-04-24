package com.yyy.wrsf.company.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.beans.company.bill.CompanyBillDetailB;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.utils.DateUtil;

import java.util.List;

public class CompanyBillDetailAdapter extends RecyclerView.Adapter<CompanyBillDetailAdapter.VH> {
    private Context context;
    private List<CompanyBillDetailB> list;
    private OnItemClickListener onItemClickListener;

    public CompanyBillDetailAdapter(Context context, List<CompanyBillDetailB> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company_bill_detail, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvOrderNo.setText(context.getString(R.string.company_bill_orderNo) + list.get(position).getContractNo());
        holder.tvDate.setText(context.getString(R.string.company_bill_date) + DateUtil.getDate(list.get(position).getCreateDate(), 2));
        holder.tvReceiver.setText(context.getString(R.string.company_bill_receiver) + list.get(position).getRecName());
        holder.tvDeliver.setText(context.getString(R.string.company_bill_deliver) + list.get(position).getDeliverTypeName());
        holder.tvNum.setText(context.getString(R.string.company_bill_num) + list.get(position).getPackNumber() + context.getString(R.string.unit_num));
        holder.tvWeight.setText(context.getString(R.string.company_bill_weight) + list.get(position).getWeight() + context.getString(R.string.unit_weight));
        holder.tvSize.setText(context.getString(R.string.company_bill_size) + list.get(position).getSize() + context.getString(R.string.unit_size));
        holder.tvUnpaid.setText(context.getString(R.string.bill_rec_unpay) + ":" + context.getString(R.string.common_rmb) + list.get(position).getUnpaid());
        holder.tvTotal.setText(context.getString(R.string.bill_rec_total) + ":" + context.getString(R.string.common_rmb) + list.get(position).getContractTotal());
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvOrderNo;
        private TextView tvDate;
        private TextView tvReceiver;
        private TextView tvDeliver;
        private TextView tvNum;
        private TextView tvWeight;
        private TextView tvSize;
        private TextView tvUnpaid;
        private TextView tvTotal;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvOrderNo = itemView.findViewById(R.id.tv_order);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvReceiver = itemView.findViewById(R.id.tv_name);
            tvDeliver = itemView.findViewById(R.id.tv_deliver);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvWeight = itemView.findViewById(R.id.tv_weight);
            tvSize = itemView.findViewById(R.id.tv_size);
            tvUnpaid = itemView.findViewById(R.id.tv_unpaid);
            tvTotal = itemView.findViewById(R.id.tv_total);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
