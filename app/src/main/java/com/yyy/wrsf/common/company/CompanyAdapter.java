package com.yyy.wrsf.common.company;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.beans.ship.ShipCompanyB;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.beans.company.CompanyB;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.VH> {
    private Context context;
    private List<ShipCompanyB> list;
    private OnItemClickListener onItemClickListener;

    public CompanyAdapter(Context context, List<ShipCompanyB> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company, parent, false);
        return new CompanyAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ((TextView) holder.itemView).setText(list.get(position).getTransCompanyName());
        holder.itemView.setOnClickListener((View view) -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvCompany;
        private TextView tvSend;
        private TextView tvSendTv;
        private TextView tvReceive;

        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
