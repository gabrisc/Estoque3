package com.example.estoque3.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.estoque3.Activity.MainScreens.MainActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.entity.Sale;
import com.example.estoque3.util.adapters.AdapterEconomicOperation;
import com.example.estoque3.util.adapters.AdapterSales;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.estoque3.entity.EconomicOperation.getIdUser;
import static com.example.estoque3.util.FireBaseConfig.firebaseInstance;

public class SalesFragment extends Fragment implements AdapterSales.OnSaleListerner{

    private PageViewModel pageViewModel;
    private static final String ARG_SECTION_NUMBER = "3";
    private AdapterSales adapterSales;

    private List<Sale> saleList = new ArrayList<>();
    private Sale saleSelect;
    private Intent intent;
    private RecyclerView recyclerView;

    public SalesFragment() {}

    public static SalesFragment newInstance(int index) {
        SalesFragment fragment = new SalesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 3;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_sales, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSale);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        adapterSales =  new AdapterSales(saleList,view.getContext(),this::onSaleListenerClick);

        firebaseInstance.getReference()
                .child(getIdUser())
                .child("ProductsAndServices")
                .child("PRODUCTS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                saleList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Sale saleTemp = ds.getValue(Sale.class);
                    saleList.add(saleTemp);

                }
                adapterSales.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String x = String.valueOf(error);
            }
        });
        recyclerView.setAdapter(adapterSales);

        return view;
    }

    @Override
    public void onSaleListenerClick(int position) {

    }
}