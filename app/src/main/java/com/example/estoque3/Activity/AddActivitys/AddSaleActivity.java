package com.example.estoque3.Activity.AddActivitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estoque3.ClosingSaleActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.Client;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.entity.EconomicOperationForSaleVo;
import com.example.estoque3.util.adapters.AdapterClient;
import com.example.estoque3.util.adapters.AdapterEconomicOperationForSales;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import static com.example.estoque3.entity.EconomicOperation.getIdUser;
import static com.example.estoque3.util.FireBaseConfig.firebaseInstance;

public class AddSaleActivity extends AppCompatActivity implements AdapterEconomicOperationForSales.OnEconomicOperationForSaleListener, AdapterClient.OnClientListener {

    private AdapterEconomicOperationForSales adapterProduct;
    private AdapterClient adapterClient;
    private List<EconomicOperation> listProduct= new ArrayList<>();
    private List<Client> clientList= new ArrayList<>();
    public static List<EconomicOperationForSaleVo> economicOperationForSaleVoArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Client clientSelected;
    private AlertDialog alertDialog;
    private Double totalValue;
    private TextView totalValueTextView,textViewOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);
        buttonVisibilityEnable(false);
        floatingVisibilityEnable(true);
        textViewOrder= findViewById(R.id.textViewOrder);
        totalValue = 0.0;
        findAllClients();
        reloadRecyclerClient();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
        economicOperationForSaleVoArrayList.clear();
    }

    @Override
    public void onEconomicOperationForSaleClick(int position) {
        EconomicOperation economicOperationSelect = listProduct.get(position);

        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_quantity,null);
        Button buttonAddQuantity =  mDialogView.findViewById(R.id.buttonDeleteEconomicOperation);
        SeekBar seekBar = mDialogView.findViewById(R.id.seekBarQuantityForSale);
        TextView counter = mDialogView.findViewById(R.id.textViewCounter);
        TextView economicOperationselectName = mDialogView.findViewById(R.id.textViewNameProduct);

        economicOperationselectName.setText(economicOperationSelect.getName());

        seekBar.setProgress(1);
        seekBar.setMax(economicOperationSelect.getQuantity());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                counter.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}});

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(mDialogView).setTitle("Quantidade");
        alertDialog=builder.create();

        buttonAddQuantity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (counter.getText().toString().equals("0")) {
                    Toast.makeText(AddSaleActivity.this, "Selecione uma quantidade", Toast.LENGTH_SHORT).show();

                } else {

                    if (Integer.parseInt(counter.getText().toString()) == economicOperationSelect.getQuantity()) {
                        addEconomicOperation(new EconomicOperationForSaleVo(economicOperationSelect,economicOperationSelect.getQuantity(),clientSelected));
                        listProduct.remove(position);
                        reloadRecyclerEconomicOperation();
                        alertDialog.dismiss();
                        buttonVisibilityEnable(true);

                    } else {
                        int quantityResult = economicOperationSelect.getQuantity() - Integer.parseInt(counter.getText().toString());
                        listProduct.get(position).setQuantity(quantityResult);
                        addEconomicOperation(new EconomicOperationForSaleVo(economicOperationSelect,Integer.parseInt(counter.getText().toString()),clientSelected));
                        reloadRecyclerEconomicOperation();
                        alertDialog.dismiss();
                        buttonVisibilityEnable(true);

                    }
                }
            }});
        alertDialog.show();
    }

    private void floatingVisibilityEnable(Boolean enable){
        FloatingActionButton floatingActionButtonAddNewClient;
        floatingActionButtonAddNewClient= findViewById(R.id.floatingActionButtonAddNewClient);
        if (enable.equals(Boolean.TRUE)){
            floatingActionButtonAddNewClient.setVisibility(View.VISIBLE);
        }else{
            floatingActionButtonAddNewClient.setVisibility(View.INVISIBLE);
        }
    }

    private void addEconomicOperation(EconomicOperationForSaleVo economicOperationForSaleVo){
        if (economicOperationForSaleVoArrayList.isEmpty()){
            economicOperationForSaleVoArrayList.add(economicOperationForSaleVo);
        }else{
            for (EconomicOperationForSaleVo e:economicOperationForSaleVoArrayList){
                if(e.getEconomicOperation().getId().equals(economicOperationForSaleVo.getEconomicOperation().getId())){
                    e.setQuantitySelect(e.getQuantitySelect()+economicOperationForSaleVo.getQuantitySelect());
                }
            }
        }
    }

    public void closingSale(View view){
    startActivity( new Intent(getApplicationContext(), ClosingSaleActivity.class));
    }

    @Override
    public void onClientOperationClick(int position) {
        clientSelected = clientList.get(position);
        floatingVisibilityEnable(false);
        clientList.clear();
        findAllEconomicOperation();
        reloadRecyclerEconomicOperation();
    }


    private void findAllClients(){
        firebaseInstance.getReference()
                .child(getIdUser())
                .child("Clients")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        clientList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            Client client= ds.getValue(Client.class);
                            clientList.add(client);
                        }
                        adapterClient.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        String x = String.valueOf(error);
                    }
                });
    }

    private void findAllEconomicOperation(){
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
    }

    private void reloadRecyclerEconomicOperation(){
        textViewOrder.setText("SELECIONE O QUE SERÁ VENDIDO");
        recyclerView= findViewById(R.id.recyclerViewprodutosavenda);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterProduct = new AdapterEconomicOperationForSales(listProduct,getApplicationContext(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterProduct);
    }

    private void reloadRecyclerClient(){
        textViewOrder.setText("SELECIONE O CLIENTE");
        recyclerView= findViewById(R.id.recyclerViewprodutosavenda);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapterClient = new AdapterClient(clientList,getApplicationContext(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterClient);
    }

    public void CallAddClient(View view){
        startActivity(new Intent(getApplicationContext(),AddClientActivity.class));
    }

    private void buttonVisibilityEnable(Boolean enable){
        FloatingActionButton buttonToConclusion;
        buttonToConclusion = findViewById(R.id.floatingActionButtonCloseSeal);

        if (enable.equals(Boolean.TRUE)){
            buttonToConclusion.setVisibility(View.VISIBLE);
        }else{
            buttonToConclusion.setVisibility(View.INVISIBLE);
        }
    }


}