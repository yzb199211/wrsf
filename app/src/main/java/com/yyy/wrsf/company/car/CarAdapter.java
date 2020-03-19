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

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.VH> {
    Context context;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
}
