package com.yyy.wrsf.company.driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.beans.DriverB;

import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.VH> {
    Context context;
    List<DriverB> list;
    OnItemClickListener onItemClickListener;

    public DriverAdapter(Context context, List<DriverB> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_driver, parent, false);
        return new DriverAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvName.setText(list.get(position).getDriverName());
        holder.tvTel.setText(list.get(position).getDriverTel());
//        holder.tvLisence.setText(list.get(position).getDriverTypeName());
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvTel;
        private TextView tvLisence;
        private TextView tvEdit;

        public VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvTel = v.findViewById(R.id.tv_tel);
//            tvLisence = v.findViewById(R.id.tv_lisence);
            tvEdit = v.findViewById(R.id.tv_edit);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

