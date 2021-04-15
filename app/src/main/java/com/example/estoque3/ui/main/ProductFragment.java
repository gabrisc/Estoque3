 package com.example.estoque3.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.estoque3.Activity.UpdateProductActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.entity.Product;
import com.example.estoque3.entity.Service;
import com.example.estoque3.util.adapters.AdapterProduct;
import com.example.estoque3.util.adapters.AdapterService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import static com.example.estoque3.entity.Product.getIdUser;
import static com.example.estoque3.util.FireBaseConfig.firebaseInstance;


public class ProductFragment extends Fragment implements AdapterProduct.OnProductListerner{

    private PageViewModel pageViewModel;
    private static final String ARG_SECTION_NUMBER = "2";
    private AdapterProduct adapterProduct;
    private List<Product> listProduct= new ArrayList<>();
    private Product productselect;
    private Intent intent;
    private RecyclerView recyclerView;
    private RadioGroup radioGroup;
    private RadioButton radioProduct,radioService,radioTodos;
    private AdapterService adapterService;
    private List<Service> serviceList= new ArrayList<>();

    public ProductFragment() {}

    public static ProductFragment newInstance(int index) {
        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 2;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        radioProduct = view.findViewById(R.id.radioButton4);
        radioService = view.findViewById(R.id.radioButton2);
        radioGroup = view.findViewById(R.id.radioGroup);
        recyclerView = view.findViewById(R.id.recyclerViewprod);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        adapterService =  new AdapterService(serviceList, view.getContext(),this::onProductClick);
        adapterProduct= new AdapterProduct(listProduct,view.getContext(), this::onProductClick);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (radioProduct.getId() == checkedId){

                firebaseInstance.getReference()
                        .child(getIdUser())
                        .child("ProductsAndServices")
                        .child("PRODUCTS").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listProduct.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Product productTemp = ds.getValue(Product.class);
                            listProduct.add(productTemp);

                        }
                        adapterProduct.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String x = String.valueOf(error);
                    }
                });
                recyclerView.setAdapter(adapterProduct);
            }else if (radioService.getId() == checkedId){
                firebaseInstance.getReference()
                        .child(getIdUser())
                        .child("ProductsAndServices")
                        .child("SERVICES").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        serviceList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Service service = ds.getValue(Service.class);
                            serviceList.add(service);
                        }
                        adapterService.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String x = String.valueOf(error);
                    }
                });
                recyclerView.setAdapter(adapterService);
            }}});
        return view;
    }

    @Override
    public void onProductClick(int position) {
        productselect = listProduct.get(position);
        intent = new Intent(getContext().getApplicationContext(), UpdateProductActivity.class);
        intent.putExtra("id", productselect.getId());
        intent.putExtra("nome",productselect.getName());
        intent.putExtra("precovenda",String.valueOf(productselect.getSealValue()));
        intent.putExtra("precocompra",String.valueOf(productselect.getExpenseValue()));
        intent.putExtra("quantidade",String.valueOf(productselect.getQuantity()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext().getApplicationContext());
        builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(intent);
            }
        }).setNegativeButton("Apagar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                productselect.remove();
            }
        }).setMessage("Nome: "+productselect.getName()+"\n"+
                "Quantidade estocada: "+productselect.getQuantity()+"\n"+
                "Preço de compra: "+productselect.getExpenseValue()+" R$"+"\n"+
                "Valor de Venda: "+productselect.getSealValue()+" R$"+"\n");

        builder.create();
        builder.show();
    }
}