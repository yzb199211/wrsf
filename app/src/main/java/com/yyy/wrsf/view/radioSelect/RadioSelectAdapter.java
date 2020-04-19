package com.yyy.wrsf.view.radioSelect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.beans.TabB;
import com.yyy.wrsf.interfaces.OnItemClickListener;

import java.util.List;

public class RadioSelectAdapter extends RecyclerView.Adapter<RadioSelectAdapter.VH> {
    private Context context;
    private List<TabB> list;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RadioSelectAdapter(Context context, List<TabB> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_radio_tab, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.textView.setText(list.get(position).getName());
        holder.textView.setTextColor(list.get(position).isChecked() ? context.getColor(R.color.white) : context.getColor(R.color.text_gray2));
        holder.textView.setBackground(list.get(position).isChecked() ? context.getDrawable(R.drawable.bg_radio_check) : context.getDrawable(R.drawable.bg_radio_normal));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView textView;

        public VH(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
