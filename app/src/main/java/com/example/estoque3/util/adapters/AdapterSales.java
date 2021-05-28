package com.example.estoque3.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estoque3.R;
import com.example.estoque3.entity.Sale;

import java.util.List;

public class AdapterSales extends RecyclerView.Adapter<AdapterSales.MyViewHolder> {
    private List<Sale> saleList;
    private Context context;
    private OnSaleListerner monSaleListener;

    public AdapterSales(List<Sale> saleList, Context context, OnSaleListerner monSaleListener) {
        this.saleList = saleList;
        this.context = context;
        this.monSaleListener = monSaleListener;
    }

    @NonNull
    @Override
    public AdapterSales.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ListItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(ListItem,monSaleListener) ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSales.MyViewHolder holder, int position) {
        Sale sale = saleList.get(position);
        holder.name.setText(sale.getClient().getNome());
    }

    @Override
    public int getItemCount() {
        return saleList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        OnSaleListerner onSaleListerner;
        public MyViewHolder(@NonNull View itemView,OnSaleListerner onSaleListerner) {
            super(itemView);
            name = itemView.findViewById(R.id.TextViewValueOfEconomicOperation);
            this.onSaleListerner = onSaleListerner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface OnSaleListerner{
        void onSaleListenerClick(int position);
    }
}
