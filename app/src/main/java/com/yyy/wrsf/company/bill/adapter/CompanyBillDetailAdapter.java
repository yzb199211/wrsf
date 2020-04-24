package com.yyy.wrsf.company.bill.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CompanyBillDetailAdapter extends RecyclerView.Adapter<CompanyBillDetailAdapter.VH> {
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvOrderNo;
        private TextView tvDate;
        private TextView tvReceiver;
        private TextView tvNum;
        private TextView tvWeight;
        private TextView tvSize;
        private TextView tvDeliver;
        private TextView tvUnpaid;
        private TextView tvTotal;

        public VH(@NonNull View itemView) {
            super(itemView);

        }
    }
}
