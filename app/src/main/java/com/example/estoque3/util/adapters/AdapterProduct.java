package com.example.estoque3.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estoque3.R;
import com.example.estoque3.entity.Product;
import com.example.estoque3.entity.Service;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.MyViewHolder> {
    private List<Product> produtosList;
    private List<Service> serviceList;
    private Context context;
    private OnProductListerner monProductListerner;


    public AdapterProduct(List<Product> produtosList, Context context, OnProductListerner onProductListerner) {
        this.produtosList = produtosList;
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
    public void onBindViewHolder(@NonNull AdapterProduct.MyViewHolder holder, int position) {
        Product product = produtosList.get(position);
        holder.nome.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return produtosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nome;
        OnProductListerner onProductListerner;

        public MyViewHolder(final View itemView,OnProductListerner onProdutoListerner) {
            super(itemView);
            nome = itemView.findViewById(R.id.nameItem);
            this.onProductListerner=onProdutoListerner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProductListerner.onProductClick(getAdapterPosition());
        }
    }
    public interface OnProductListerner{
        void onProductClick(int position);
    }
}
