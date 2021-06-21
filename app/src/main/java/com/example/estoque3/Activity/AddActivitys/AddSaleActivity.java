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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estoque3.ClosingSaleActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.Client;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.entity.EconomicOperationForSaleVo;
import com.example.estoque3.util.TypeOfProduct;
import com.example.estoque3.util.adapters.AdapterClient;
import com.example.estoque3.util.adapters.AdapterEconomicOperationForSales;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.example.estoque3.entity.EconomicOperation.getIdUser;
import static com.example.estoque3.util.FireBaseConfig.firebaseInstance;
import static java.lang.Integer.parseInt;

public class AddSaleActivity extends AppCompatActivity implements AdapterEconomicOperationForSales.OnEconomicOperationForSaleListener, AdapterClient.OnClientListener {

    private AdapterEconomicOperationForSales adapterProduct;
    private AdapterClient adapterClient;
    private List<EconomicOperation> listProduct= new ArrayList<>();
    private List<Client> clientList= new ArrayList<>();
    public final static Set<EconomicOperationForSaleVo> economicOperationForSaleVoArrayList = new HashSet<>();
    public static Client clientSelected;
    private RecyclerView recyclerView;
    private AlertDialog alertDialog;
    private TextView totalValueTextView,textViewOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);
        buttonVisibilityEnable(false);
        visibilidadeDosBotoes(true);
        textViewOrder= findViewById(R.id.textViewOrder);
        findAllClients();
        reloadRecyclerClient();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
        economicOperationForSaleVoArrayList.clear();
        clientSelected=null;
    }

    @Override
    public void onEconomicOperationForSaleClick(int position) {
        EconomicOperation economicOperationSelect = listProduct.get(position);
        if (economicOperationSelect.getType().equals(TypeOfProduct.PRODUTO.toString())){
            callDialogForProduct(economicOperationSelect,position,false);
        }else{
            callDialogForProduct(economicOperationSelect,position,true);
        }
    }

    private void callDialogForProduct(EconomicOperation economicOperationSelect,int position,Boolean isService){
        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete,null);

        TextView textViewMensage = mDialogView.findViewById(R.id.textViewMensage);
        Button buttonAddQuantity =  mDialogView.findViewById(R.id.buttonAddDiscount);
        ImageButton lessB = mDialogView.findViewById(R.id.lessB);
        ImageButton addB = mDialogView.findViewById(R.id.addB);
        EditText counter = mDialogView.findViewById(R.id.textViewCounter);
        counter.setText("0");

        if (isService){
         textViewMensage.setText("Deseja adicionar esse serviço?");
         lessB.setVisibility(View.INVISIBLE);
         addB.setVisibility(View.INVISIBLE);
         counter.setVisibility(View.INVISIBLE);
        }

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.setText(String.format("%d", parseInt(counter.getText().toString()) + 1));
            }});

        lessB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.setText(String.format("%d", parseInt(counter.getText().toString()) - 1));
            }});

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(mDialogView).setTitle("Quantidade");
        alertDialog=builder.create();

        buttonAddQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter.getText().toString().equals("0")) {
                    Toast.makeText(AddSaleActivity.this, "Selecione uma quantidade", Toast.LENGTH_SHORT).show();
                } else {
                    if (Integer.parseInt(counter.getText().toString()) == economicOperationSelect.getQuantity()) {
                        addEconomicOperation(new EconomicOperationForSaleVo(economicOperationSelect,economicOperationSelect.getQuantity()));
                        listProduct.remove(position);
                        reloadRecyclerEconomicOperation();
                        alertDialog.dismiss();
                        buttonVisibilityEnable(true);
                    } else {
                        int quantityResult = economicOperationSelect.getQuantity() - Integer.parseInt(counter.getText().toString());
                        listProduct.get(position).setQuantity(quantityResult);
                        addEconomicOperation(new EconomicOperationForSaleVo(economicOperationSelect,Integer.parseInt(counter.getText().toString())));
                        reloadRecyclerEconomicOperation();
                        alertDialog.dismiss();
                        buttonVisibilityEnable(true);
                    }
                }
            }});
        alertDialog.show();
    }

    private void visibilidadeDosBotoes(Boolean enable){
        FloatingActionButton floatingActionButtonAddNewClient;
        floatingActionButtonAddNewClient= findViewById(R.id.floatingActionButtonAddNewClient);
        if (enable.equals(Boolean.TRUE)){
            floatingActionButtonAddNewClient.setVisibility(View.VISIBLE);
        }else{
            floatingActionButtonAddNewClient.setVisibility(View.INVISIBLE);
        }
    }

    private void addEconomicOperation(EconomicOperationForSaleVo economicOperationForSaleVo){
        boolean find =false;
        for(Iterator<EconomicOperationForSaleVo> it = economicOperationForSaleVoArrayList.iterator(); it.hasNext();){
            EconomicOperationForSaleVo e= it.next();
            if (e.getEconomicOperation().getId().equals(economicOperationForSaleVo.getEconomicOperation().getId())){
                e.setQuantitySelect(economicOperationForSaleVo.getQuantitySelect() + e.getQuantitySelect());
                //economicOperationForSaleVoArrayList.add(e);
                find=true;
            }
        }
        if(!find){
            economicOperationForSaleVoArrayList.add(economicOperationForSaleVo);
        }

    }

    public void fechandoVenda(View view){
        startActivity( new Intent(getApplicationContext(), ClosingSaleActivity.class));
    }

    @Override
    public void onClientOperationClick(int position) {
        clientSelected = clientList.get(position);
        visibilidadeDosBotoes(false);
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