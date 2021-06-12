package com.example.estoque3.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estoque3.R;
import com.example.estoque3.entity.EconomicOperationForSaleVo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdapterEconomicOperationForSaleVo extends RecyclerView.Adapter<AdapterEconomicOperationForSaleVo.MyViewholder> {
    private List<EconomicOperationForSaleVo> economicOperationForSaleVos=  new ArrayList<>();
    private Context context;
    private OnEconomicOperationForSaleVo mOnEconomicOperationForSaleVo;

    public AdapterEconomicOperationForSaleVo(Set<EconomicOperationForSaleVo> e, Context context, OnEconomicOperationForSaleVo mOnEconomicOperationForSaleVo) {
        economicOperationForSaleVos.addAll(e);
        this.context = context;
        this.mOnEconomicOperationForSaleVo = mOnEconomicOperationForSaleVo;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_economic_operation_for_sale,parent,false);
    return new MyViewholder(view, mOnEconomicOperationForSaleVo);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
    EconomicOperationForSaleVo economicOperationForSaleVo = economicOperationForSaleVos.get(position);
    holder.name.setText(economicOperationForSaleVo.getEconomicOperation().getName().toUpperCase());
    holder.quantity.setText(""+economicOperationForSaleVo.getQuantitySelect());

    }

    @Override
    public int getItemCount() {
        return economicOperationForSaleVos.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView quantity;
        OnEconomicOperationForSaleVo onEconomicOperationForSaleVo;
        public MyViewholder(@NonNull View itemView,OnEconomicOperationForSaleVo onEconomicOperationForSaleVo) {
            super(itemView);
            name = itemView.findViewById(R.id.nameItem2);
            quantity = itemView.findViewById(R.id.QtdItem3);
            this.onEconomicOperationForSaleVo = onEconomicOperationForSaleVo;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEconomicOperationForSaleVo.onEconomicOperationForSaleVoClick(getAdapterPosition());
        }
    }
    public interface OnEconomicOperationForSaleVo{
        void onEconomicOperationForSaleVoClick(int position);
    }
}
