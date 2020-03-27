package com.yyy.wrsf.mine.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyy.wrsf.R;
import com.yyy.wrsf.enums.ContractStatusEnum;
import com.yyy.wrsf.model.OrderModel;
import com.yyy.wrsf.model.ship.ShippingModel;
import com.yyy.wrsf.utils.DateUtil;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH> {
    private Context context;
    private List<OrderModel> list;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_customer, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tvOrderNo.setText(context.getString(R.string.order_no) + "：" + list.get(position).getContractNo());
        holder.tvOrderType.setText(ContractStatusEnum.getDescByStatus(list.get(position).getContractStatus()));
//        holder.tvCompany.setText(list.get(position));
        holder.tvDateLast.setText(DateUtil.getDate(list.get(position).getUpdateDate(), 2));
        holder.tvSendArea.setText(list.get(position).getSendAdd());
        holder.tvSendPerson.setText(list.get(position).getSendName());
        holder.tvReceiveArea.setText(list.get(position).getRecAdd());
        holder.tvReceivePerson.setText(list.get(position).getRecName());
        holder.tvOrderGoods.setText(list.get(position).getGoodsName());
        holder.tvOrderCost.setText(context.getString(R.string.order_cost)+"：¥"+list.get(position).getContractTotal());
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

}
