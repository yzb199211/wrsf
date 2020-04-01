package com.yyy.wrsf.company.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.enums.ContractStatusEnum;
import com.yyy.wrsf.interfaces.OnCancleListener;
import com.yyy.wrsf.interfaces.OnConfirmListener;
import com.yyy.wrsf.interfaces.OnItemClickListener;
import com.yyy.wrsf.beans.OrderBean;
import com.yyy.wrsf.utils.DateUtil;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH> {
    private Context context;
    private List<OrderBean> list;
    private OnItemClickListener onItemClickListener;
    private OnCancleListener onCancleListener;
    private OnConfirmListener onConfirmListener;

    public OrderAdapter(Context context, List<OrderBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_company, parent, false);
        return new OrderAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvOrderNo.setText(context.getString(R.string.order_no) + "：" + list.get(position).getContractNo());
        holder.tvOrderType.setText(ContractStatusEnum.getDescByStatus(list.get(position).getContractStatus()));
        holder.tvCompany.setText(list.get(position).getCompanyName());
        holder.tvDateLast.setText(DateUtil.getDate(list.get(position).getUpdateDate(), 2));
        holder.tvSendArea.setText(list.get(position).getSendAdd());
        holder.tvSendPerson.setText(list.get(position).getSendName());
        holder.tvReceiveArea.setText(list.get(position).getRecAdd());
        holder.tvReceivePerson.setText(list.get(position).getRecName());
        holder.tvOrderGoods.setText(list.get(position).getGoodsName());
        holder.tvOrderCost.setText(context.getString(R.string.order_cost) + "：¥" + list.get(position).getContractTotal());
        if (list.get(position).getContractStatus() > 3 || list.get(position).getContractStatus() == -1) {
            holder.tvOrderCancle.setVisibility(View.INVISIBLE);
        }
        if (list.get(position).getPayStatus() != 1) {
            holder.tvPay.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) onItemClickListener.onItemClick(position);
        });
        holder.tvPay.setOnClickListener(view -> {
            if (onConfirmListener!=null) onConfirmListener.onConfirm(position);
        });
        holder.tvOrderCancle.setOnClickListener(view -> {
            if (onCancleListener!=null) onCancleListener.onCancle(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView tvOrderNo;
        private TextView tvOrderType;
        private TextView tvCompany;
        private TextView tvDateLast;
        private TextView tvSendArea;
        private TextView tvSendPerson;
        private TextView tvReceiveArea;
        private TextView tvReceivePerson;
        private TextView tvOrderGoods;
        private TextView tvOrderCost;
        private TextView tvPay;
        private TextView tvOrderCancle;

        public VH(View v) {
            super(v);
            tvOrderNo = v.findViewById(R.id.tv_order_no);
            tvOrderType = v.findViewById(R.id.tv_order_type);
            tvCompany = v.findViewById(R.id.tv_company);
            tvDateLast = v.findViewById(R.id.tv_date_last);
            tvSendArea = v.findViewById(R.id.tv_send_area);
            tvSendPerson = v.findViewById(R.id.tv_send_person);
            tvReceiveArea = v.findViewById(R.id.tv_receive_area);
            tvReceivePerson = v.findViewById(R.id.tv_receive_person);
            tvOrderGoods = v.findViewById(R.id.tv_order_goods);
            tvOrderCost = v.findViewById(R.id.tv_order_cost);
            tvPay = v.findViewById(R.id.tv_pay);
            tvOrderCancle = v.findViewById(R.id.tv_order_cancle);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnCancleListener(OnCancleListener onCancleListener) {
        this.onCancleListener = onCancleListener;
    }

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }
}
