package com.example.estoque3.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estoque3.R;
import com.example.estoque3.entity.Service;

import java.util.List;

public class AdapterService extends RecyclerView.Adapter<AdapterService.MyViewHolder> {
    private List<Service> serviceList;
    private Context context;
    private OnServiceListerner monServiceListener;

    public AdapterService(List<Service> serviceList, Context context, OnServiceListerner monServiceListener) {
        this.serviceList = serviceList;
        this.context = context;
        this.monServiceListener = monServiceListener;
    }

    @NonNull
    @Override
    public AdapterService.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View listItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
    return new MyViewHolder(listItem,monServiceListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterService.MyViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.nome.setText(service.getName());
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nome;
        OnServiceListerner onServiceListerner;

        public MyViewHolder(@NonNull View itemView,OnServiceListerner onServiceListerner) {
            super(itemView);
            nome= itemView.findViewById(R.id.nameItem);
            this.onServiceListerner=onServiceListerner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onServiceListerner.OnServiceClick(getAdapterPosition());
        }
    }
    public interface OnServiceListerner{
        void OnServiceClick(int position);
    }
}


