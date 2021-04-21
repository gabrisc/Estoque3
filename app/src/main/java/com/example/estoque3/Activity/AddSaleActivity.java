package com.example.estoque3.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.estoque3.R;
import com.example.estoque3.entity.Clients;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.entity.Sale;
import com.example.estoque3.ui.main.PageViewModel;
import com.example.estoque3.util.adapters.AdapterEconomicOperation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.estoque3.entity.EconomicOperation.getIdUser;
import static com.example.estoque3.util.FireBaseConfig.firebaseDbReference;
import static com.example.estoque3.util.FireBaseConfig.firebaseInstance;

public class AddSaleActivity extends AppCompatActivity implements AdapterEconomicOperation.OnEconomicOperationListerner {

    private PageViewModel pageViewModel;
    private static final String ARG_SECTION_NUMBER = "2";
    private AdapterEconomicOperation adapterProduct;
    private List<EconomicOperation> listProduct= new ArrayList<>();
    private Sale SaleSelect;
    private Intent intent;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private List<Clients> clientsList= new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        recyclerView= findViewById(R.id.recyclerViewprodutosavenda);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapterProduct= new AdapterEconomicOperation(listProduct,this,this);
        firebaseInstance.getReference()
                .child(getIdUser())
                .child("EconomicOperations")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listProduct.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    EconomicOperation economicOperation= ds.getValue(EconomicOperation.class);
                    listProduct.add(economicOperation);
                }
                adapterProduct.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String x = String.valueOf(error);
            }
        });
        String[] clientsArray = {"cliente 1","cliente 2","cliente 3","cliente 4","cliente 5"};


        recyclerView.setAdapter(adapterProduct);
        TextView data= findViewById(R.id.textViewDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        data.setText(simpleDateFormat.format(System.currentTimeMillis()));

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<Clients> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,clientsList);
        //arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);


    }


    @Override
    public void onEconomicOperationClick(int position) {

    }

}