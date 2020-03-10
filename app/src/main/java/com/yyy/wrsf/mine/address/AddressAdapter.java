package com.yyy.wrsf.mine.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.interfaces.OnDeleteListener;
import com.yyy.wrsf.interfaces.OnEditListener;
import com.yyy.wrsf.model.AddressModel;
import com.yyy.wrsf.utils.StringUtil;
import com.yyy.wrsf.view.button.ButtonWithImg;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.VH> {
    private Context context;
    private List<AddressModel> list;
    private OnDeleteListener onDeleteListener;
    private OnEditListener onEditListener;

    public AddressAdapter(Context context, List<AddressModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.setIsRecyclable(false);
        AddressModel item = list.get(position);
        holder.tvName.setText(StringUtil.formatString(item.getContractPerson()));
        holder.tvPhone.setText(StringUtil.formatString(item.getContractTel()));
        holder.tvAddress.setText(item.getWholeAddress());
        holder.bwiEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEditListener != null) {
                    onEditListener.onEdit(position);
                }
            }
        });
        holder.bwiDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListener != null) {
                    onDeleteListener.onDelete(position);
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
        private TextView tvPhone;
        private TextView tvAddress;
        private TextView tvDefault;
        private ButtonWithImg bwiDelete;
        private ButtonWithImg bwiEdit;

        public VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_name);
            tvPhone = v.findViewById(R.id.tv_phone);
            tvAddress = v.findViewById(R.id.tv_address);
            tvDefault = v.findViewById(R.id.tv_default);
            bwiDelete = v.findViewById(R.id.bwi_delete);
            bwiEdit = v.findViewById(R.id.bwi_edit);
        }
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public void setOnEditListener(OnEditListener onEditListener) {
        this.onEditListener = onEditListener;
    }
}
