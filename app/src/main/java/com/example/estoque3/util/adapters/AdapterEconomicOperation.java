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

import java.util.List;

public class AdapterEconomicOperation extends RecyclerView.Adapter<AdapterEconomicOperation.MyViewHolder> {
    private List<EconomicOperation> economicOperationList;
    private Context context;
    private OnEconomicOperationListerner monProductListerner;


    public AdapterEconomicOperation(List<EconomicOperation> economicOperationList, Context context, OnEconomicOperationListerner onProductListerner) {
        this.economicOperationList = economicOperationList;
        this.context = context;
        this.monProductListerner = onProductListerner;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ListItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(ListItem,monProductListerner);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEconomicOperation.MyViewHolder holder, int position) {
        EconomicOperation economicOperation = economicOperationList.get(position);
        holder.nome.setText(economicOperation.getName());
    }

    @Override
    public int getItemCount() {
        return economicOperationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nome;
        OnEconomicOperationListerner onEconomicOperationListerner;

        public MyViewHolder(final View itemView, OnEconomicOperationListerner onEconomicOperationListerner) {
            super(itemView);
            nome = itemView.findViewById(R.id.nameItem);
            this.onEconomicOperationListerner =onEconomicOperationListerner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEconomicOperationListerner.onEconomicOperationClick(getAdapterPosition());
        }
    }
    public interface OnEconomicOperationListerner {
        void onEconomicOperationClick(int position);
    }
}
