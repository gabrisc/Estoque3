package com.example.estoque3.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estoque3.R;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.util.TypeOfProduct;

import java.util.List;

public class AdapterEconomicOperationForSales extends RecyclerView.Adapter<AdapterEconomicOperationForSales.MyViewholder> {
    private List<EconomicOperation> economicOperationForSaleList;
    private Context context;
    private  OnEconomicOperationForSaleListener monEconomicOperationForSaleListener;

    public AdapterEconomicOperationForSales(List<EconomicOperation> economicOperationForSaleList, Context context, OnEconomicOperationForSaleListener onEconomicOperationForSaleListener) {
        this.economicOperationForSaleList = economicOperationForSaleList;
        this.context = context;
        this.monEconomicOperationForSaleListener = onEconomicOperationForSaleListener;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_economic_operation_for_sale,parent,false);
        return new MyViewholder(listItem,monEconomicOperationForSaleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEconomicOperationForSales.MyViewholder holder, int position) {
        EconomicOperation economicOperation = economicOperationForSaleList.get(position);
        if (economicOperation.getType().equals(TypeOfProduct.PRODUTO.toString())){
            holder.name.setText(economicOperation.getName().toUpperCase());
            holder.quantity.setText(String.format(" %d %s ", economicOperation.getQuantity(), economicOperation.getTypeQuantity()));
            holder.sealValue.setText("R$: "+String.valueOf(economicOperation.getSealValue()));holder.name.setText(economicOperation.getName().toUpperCase());
        }else{
            holder.name.setText(economicOperation.getName().toUpperCase());
            holder.sealValue.setText(""+String.valueOf(economicOperation.getSealValue())+" R$");holder.name.setText(economicOperation.getName().toUpperCase());
            holder.quantity.setText(""+String.valueOf(economicOperation.getExpenseValue())+" R$");
        }
    }

    @Override
    public int getItemCount() {
        return economicOperationForSaleList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView sealValue;
        TextView quantity;
        OnEconomicOperationForSaleListener onEconomicOperationForSaleListener;

        public MyViewholder(final View itemView,OnEconomicOperationForSaleListener onEconomicOperationForSaleListener) {
            super(itemView);
            name = itemView.findViewById(R.id.nameItem2);
            sealValue =itemView.findViewById(R.id.TextViewValueOfEconomicOperation);
            quantity = itemView.findViewById(R.id.QtdItem3);
            this.onEconomicOperationForSaleListener= onEconomicOperationForSaleListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEconomicOperationForSaleListener.onEconomicOperationForSaleClick(getAdapterPosition());
        }
    }
    public interface  OnEconomicOperationForSaleListener{
        void onEconomicOperationForSaleClick(int position);
    }
}
