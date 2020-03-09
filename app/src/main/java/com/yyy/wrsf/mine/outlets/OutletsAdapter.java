package com.yyy.wrsf.mine.outlets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.OnDialListener;
import com.yyy.wrsf.utils.StringUtil;

import java.util.List;

public class OutletsAdapter<T extends OutletItemListener> extends RecyclerView.Adapter<OutletsAdapter<T>.VH> {
    Context context;
    List<T> list;
    OnDialListener onDialListener;

    public void setOnDialListener(OnDialListener onDialListener) {
        this.onDialListener = onDialListener;
    }

    public OutletsAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_outlets_search, parent, false);
        return new VH(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        OutletItem item = list.get(position).getOutlet();
        if (item != null) {
            holder.tvTitle.setText(item.getTitle());
            holder.tvAddress.setText(item.getAddress());
            holder.tvDetail.setText(item.getDetail());
            holder.tvPhone.setText(item.getPhone());
            holder.tvPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDialListener != null || StringUtil.isNotEmpty(item.getPhone())) {
                        onDialListener.onDial(item.getPhone());
                    }
                }
            });
        }
        holder.setIsRecyclable(false);
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvAddress;
        private TextView tvDetail;
        private TextView tvPhone;

        public VH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_title);
            tvAddress = v.findViewById(R.id.tv_address);
            tvDetail = v.findViewById(R.id.tv_describe);
            tvPhone = v.findViewById(R.id.tv_phone);
        }
    }
}
