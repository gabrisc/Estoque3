package com.example.estoque3.Activity.ListActivitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.estoque3.R;
import com.example.estoque3.entity.Client;
import com.example.estoque3.util.adapters.AdapterClient;
import com.example.estoque3.util.adapters.AdapterSales;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.estoque3.entity.EconomicOperation.getIdUser;
import static com.example.estoque3.util.FireBaseConfig.firebaseInstance;

public class ListClientActivity extends AppCompatActivity  implements  AdapterClient.OnClientListener{


    private Intent intent;
    private RecyclerView recyclerView;
    private Client clientSelect;
    private List<Client> clientList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);
        recyclerView = findViewById(R.id.recyclerViewClient);
        AdapterClient adapterClient = new AdapterClient(clientList,getApplicationContext(),this::onClientOperationClick);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        firebaseInstance.getReference()
                .child(getIdUser())
                .child("Clients")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientList.clear();
                for (DataSnapshot ds:snapshot.getChildren()){
                    Client client = ds.getValue(Client.class);
                    clientList.add(client);
                }
                adapterClient.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String x =String.valueOf(error);
            }
        });
        recyclerView.setAdapter(adapterClient);
    }

    @Override
    public void onClientOperationClick(int position) {

    }
}