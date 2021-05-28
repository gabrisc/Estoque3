package com.example.estoque3.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.estoque3.Activity.UpdatesActivitys.UpdateClientActivity;
import com.example.estoque3.Activity.UpdatesActivitys.UpdateProductActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.Client;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.util.adapters.AdapterClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.estoque3.entity.EconomicOperation.getIdUser;
import static com.example.estoque3.util.FireBaseConfig.firebaseInstance;

public class ClientFragment extends Fragment implements AdapterClient.OnClientListener{

    private static final String ARG_SECTION_NUMBER = "3";
    private PageViewModel pageViewModel;
    private Intent intent;
    private RecyclerView recyclerView;
    private List<Client> clientList= new ArrayList<>();
    private int positionEconomicOperationSelect;


    public static ClientFragment newInstance(int index) {
        ClientFragment fragment = new ClientFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index =4;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_client, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewClients);
        AdapterClient adapterClient = new AdapterClient(clientList,view.getContext(),this::onClientOperationClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
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

        return view;
    }

    @Override
    public void onClientOperationClick(int position) {
        Client client = clientList.get(position);
        positionEconomicOperationSelect =position;
        intent= new Intent(getContext().getApplicationContext(), UpdateClientActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",client.getId());
        bundle.putString("email",client.getEmail());
        bundle.putString("name",client.getNome());
        bundle.putString("phoneNumber",client.getTelefone());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}