package com.yyy.wrsf.company.car;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.company.driver.DriverAdapter;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.CarModel;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.VH> {
    Context context;
    List<CarModel> list;
    OnItemClickListener onItemClickListener;

    public CarAdapter(Context context, List<CarModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvName.setText(list.get(position).getCarName());
        holder.tvLisence.setText(list.get(position).getCarCode());
        holder.tvStatus.setText(list.get(position).getCarStatusName());
        holder.tvType.setText(list.get(position).getCarTypeName());
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
        private TextView tvType;
        private TextView tvLisence;
        private TextView tvStatus;
        private TextView tvEdit;

        public VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvType = v.findViewById(R.id.tv_type);
            tvLisence = v.findViewById(R.id.tv_lisence);
            tvStatus = v.findViewById(R.id.tv_status);
            tvEdit = v.findViewById(R.id.tv_edit);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
