package com.yyy.wrsf.mine.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.beans.person.PersonBillB;
import com.yyy.wrsf.interfaces.OnBillApplyListener;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.interfaces.OnPayListener;

import java.util.List;

public class BillMonthAdapter extends RecyclerView.Adapter<BillMonthAdapter.VH> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnPayListener onPayListener;
    private OnBillApplyListener onBillApplyListener;
    private List<PersonBillB> list;

    public BillMonthAdapter(Context context, List<PersonBillB> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill_month, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvCompany.setText(list.get(position).getTransCompanyName());
        holder.tvNum.setText(context.getString(R.string.common_total) + list.get(position).getOrderCount() + context.getString(R.string.unit_order));
        holder.tvTotal.setText(list.get(position).getContractTotal() + "");
        holder.tvPaid.setText(list.get(position).getPaid() + "");
        holder.tvUnpay.setText(list.get(position).getUnpaid() + "");
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
        holder.tvPay.setOnClickListener(view -> {
            if (onPayListener != null) {
                onPayListener.onPay(position);
            }
        });
        holder.tvBill.setOnClickListener(view -> {
            if (onBillApplyListener != null) {
                onBillApplyListener.onbillAply(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvCompany;
        private TextView tvNum;
        private TextView tvTotal;
        private TextView tvPaid;
        private TextView tvUnpay;
        private TextView tvBill;
        private TextView tvPay;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvCompany = itemView.findViewById(R.id.tv_company);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvPaid = itemView.findViewById(R.id.tv_paid);
            tvUnpay = itemView.findViewById(R.id.tv_unpay);
            tvBill = itemView.findViewById(R.id.tv_bill);
            tvPay = itemView.findViewById(R.id.tv_pay);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnPayListener(OnPayListener onPayListener) {
        this.onPayListener = onPayListener;
    }

    public void setOnBillApplyListener(OnBillApplyListener onBillApplyListener) {
        this.onBillApplyListener = onBillApplyListener;
    }
}
