package com.yyy.wrsf.company.outlets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.model.OutletB;

import java.util.List;

public class OutletAdapter extends RecyclerView.Adapter<OutletAdapter.VH> {
    private Context context;
    private List<OutletB> list;
    OnItemClickListener onItemClickListener;

    public OutletAdapter(Context context, List<OutletB> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_outlets, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvName.setText(list.get(position).getShopName());
        holder.tvAddress.setText(list.get(position).getShopName());
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
        private TextView tvAddress;
        private TextView tvStatus;
        private TextView tvEdit;

        public VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvAddress = v.findViewById(R.id.tv_address);
            tvStatus = v.findViewById(R.id.tv_status);
            tvEdit = v.findViewById(R.id.tv_edit);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
