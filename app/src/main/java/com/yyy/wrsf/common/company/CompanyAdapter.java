package com.yyy.wrsf.common.company;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.model.CompanyModel;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.VH> {
    private Context context;
    private List<CompanyModel> list;
    private OnCompanySelectListener onCompanySelectListener;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company, parent, false);
        return new CompanyAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ((TextView) holder.itemView).setText(list.get(position).getCompanyName());
        holder.itemView.setOnClickListener((View view) -> {
            if (onCompanySelectListener != null) {
                onCompanySelectListener.onSelect(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setOnCompanySelectListener(OnCompanySelectListener onCompanySelectListener) {
        this.onCompanySelectListener = onCompanySelectListener;
    }
}
