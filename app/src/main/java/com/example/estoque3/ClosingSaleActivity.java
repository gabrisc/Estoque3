package com.example.estoque3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estoque3.Activity.AddActivitys.AddSaleActivity;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.entity.EconomicOperationForSaleVo;
import com.example.estoque3.entity.Sale;
import com.example.estoque3.util.adapters.AdapterEconomicOperationForSaleVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.example.estoque3.Activity.AddActivitys.AddSaleActivity.clientSelected;
import static com.example.estoque3.Activity.AddActivitys.AddSaleActivity.economicOperationForSaleVoArrayList;
import static com.example.estoque3.util.FireBaseConfig.firebaseDbReference;

public class ClosingSaleActivity extends AppCompatActivity implements AdapterEconomicOperationForSaleVo.OnEconomicOperationForSaleVo{
    private Spinner spinnerPaymentstype;
    private RecyclerView recyclerView;
    private AlertDialog alertDialog;
    private TextView date;
    private SimpleDateFormat simpleDateFormat;
    private Double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closing_sale);
        setClientNameInActivity();
        CalcTotalValue();
        recyclerView = findViewById(R.id.RecyclerViewEconomicOperationSelected);
        setListPaymentsTypes();
        setDateInActivity();
        recarregarLista();
    }

    public void recarregarLista(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AdapterEconomicOperationForSaleVo adapterEconomicOperationForSaleVo = new AdapterEconomicOperationForSaleVo(economicOperationForSaleVoArrayList,getApplicationContext(), this::onEconomicOperationForSaleVoClick);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterEconomicOperationForSaleVo);
    }

    private void setListPaymentsTypes(){
        String[] listOfPaymentsType = {"DEBITO","CREDITO","DINHEIRO","CHEQUE","BOLETO","TRANSF. BANCARIA","OUTROS"};
        spinnerPaymentstype = findViewById(R.id.listOfPaymentsType);
        spinnerPaymentstype.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.item_list_spinner, listOfPaymentsType));
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    private void setDateInActivity() {
        date = findViewById(R.id.TextViewDateOfBuy2);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        date.setText(simpleDateFormat.format(System.currentTimeMillis()));
    }

    public void SaveSeal(View view) {
        Sale sale= new Sale();
        sale.setPaymentType(spinnerPaymentstype.getSelectedItem().toString());
        sale.setClient(clientSelected);
        sale.setDate(simpleDateFormat.format(System.currentTimeMillis()));
        sale.setId(firebaseDbReference.push().getKey());
        List<EconomicOperationForSaleVo> economicOperationForSaleVos= new ArrayList<>();
        for(int e=0; e ==economicOperationForSaleVoArrayList.size();e++){
            economicOperationForSaleVos = Collections.singletonList(economicOperationForSaleVoArrayList.iterator().next());
        }
        sale.setEconomicOperationForSaleVos(economicOperationForSaleVos);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Aplicar desconto?");

        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                applyDiscount();
            }
        });

        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            closeSeal();
            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    private void closeSeal(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Concluir Venda");
        alertDialog.setMessage("Total: R$ ");
        alertDialog.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.setNegativeButton("não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    private void applyDiscount(){
        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_discount,null);
        Button butttonCancel =  mDialogView.findViewById(R.id.buttonDeleteDiscount);
        Button buttonAddDiscount = mDialogView.findViewById(R.id.buttonAddDiscount);
        EditText editText = mDialogView.findViewById(R.id.editTextDiscount);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(mDialogView).setTitle("DESCONTO");
        alertDialog=builder.create();

        butttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonAddDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSeal();
                alertDialog.dismiss();
                Toast.makeText(ClosingSaleActivity.this, "Selecione uma quantidade", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onEconomicOperationForSaleVoClick(int position) {
        EconomicOperationForSaleVo economicOperationForSaleVo = economicOperationForSaleVoArrayList.iterator().next();
        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete,null);

        Button buttonDeleteEconomicOperation=mDialogView.findViewById(R.id.buttonDeleteDiscount);
        Button buttonAddQuantity=mDialogView.findViewById(R.id.buttonAddDiscount);
        SeekBar seekBar=mDialogView.findViewById(R.id.seekBarQuantityForSale);
        TextView counter = mDialogView.findViewById(R.id.textViewCounter);
        TextView economicOperationselectName = mDialogView.findViewById(R.id.textViewNameProduct);

        seekBar.setProgress(Integer.parseInt(String.valueOf(economicOperationForSaleVo.getQuantitySelect())));
        economicOperationselectName.setText(economicOperationForSaleVo.getEconomicOperation().getName());
        seekBar.setMax(economicOperationForSaleVo.getEconomicOperation().getQuantity());
        counter.setText(String.valueOf(economicOperationForSaleVo.getQuantitySelect()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                counter.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}});

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(mDialogView).setTitle("Quantidade");
        alertDialog=builder.create();

        buttonDeleteEconomicOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(counter.getText().toString())!=0){
                    economicOperationForSaleVo.setQuantitySelect(Integer.valueOf(counter.getText().toString()));
                    recarregarLista();
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(ClosingSaleActivity.this, "Selecione uma quantidade", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonAddQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                economicOperationForSaleVoArrayList.remove(position);
                if (economicOperationForSaleVoArrayList.isEmpty()){
                    startActivity(new Intent(getApplicationContext(), AddSaleActivity.class));
                    try {
                        this.finalize();
                    } catch (Throwable throwable) { throwable.printStackTrace(); }
                }
                recarregarLista();
            }
        });
        alertDialog.show();
    }

    private void CalcTotalValue() {
        for(Iterator<EconomicOperationForSaleVo> it = economicOperationForSaleVoArrayList.iterator(); it.hasNext();){
            EconomicOperationForSaleVo e= it.next();
            total += e.getQuantitySelect()*e.getEconomicOperation().getSealValue();
        }
        TextView finalText = findViewById(R.id.textViewFinalValue);
        finalText.setText("R$: "+total);
    }

    private void setClientNameInActivity() {
        TextView clientName = findViewById(R.id.textViewClientName);
        clientName.setText(clientSelected.getNome().toUpperCase());
    }
}