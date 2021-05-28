package com.example.estoque3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estoque3.Activity.AddActivitys.AddSaleActivity;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.entity.EconomicOperationForSaleVo;
import com.example.estoque3.entity.Sale;
import com.example.estoque3.util.adapters.AdapterEconomicOperationForSales;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.example.estoque3.Activity.AddActivitys.AddSaleActivity.economicOperationForSaleVoArrayList;

public class ClosingSaleActivity extends AppCompatActivity implements AdapterEconomicOperationForSales.OnEconomicOperationForSaleListener{

    private Spinner spinnerPaymentstype;
    private RecyclerView recyclerView;
    private List<EconomicOperation> listProductSelect = new ArrayList<>();
    private List<Integer> quantitySelectArrayList = new ArrayList<>();
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closing_sale);
        setClientNameInActivity();
        setListProductSelect();
        CalcTotalValue();
        recyclerView = findViewById(R.id.RecyclerViewEconomicOperationSelected);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AdapterEconomicOperationForSales adapterEconomicOperationForSales = new AdapterEconomicOperationForSales(listProductSelect,getApplicationContext(),this::onEconomicOperationForSaleClick);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterEconomicOperationForSales);

        String[] listOfPaymentsType = {"DEBITO","CREDITO","DINHEIRO","CHEQUE","BOLETO","TRANSF. BANCARIA","OUTROS"};
        spinnerPaymentstype = findViewById(R.id.listOfPaymentsType);
        spinnerPaymentstype.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.item_list_spinner, listOfPaymentsType)
        );

        setDateInActivity();
    }
    private void setListProductSelect(){
        for (EconomicOperationForSaleVo economicOperationForSaleVo:economicOperationForSaleVoArrayList){
            listProductSelect.add(economicOperationForSaleVo.getEconomicOperation());
            quantitySelectArrayList.add(economicOperationForSaleVo.getQuantitySelect());
        }

    }

    private void setDateInActivity() {
        TextView date = findViewById(R.id.TextViewDateOfBuy2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(simpleDateFormat.format(System.currentTimeMillis()));
    }
    private void setClientNameInActivity() {
        TextView clientName = findViewById(R.id.textViewClientName);
        clientName.setText(economicOperationForSaleVoArrayList.get(0).getClient().getNome());
    }

    private void CalcTotalValue() {
        Double total= 0.0;
        int i=0;
        for(EconomicOperation e:listProductSelect){
            for(Integer quantity:quantitySelectArrayList){
                total+=quantity*e.getSealValue();
            }
        }
        TextView finalText = findViewById(R.id.textViewFinalValue);
        finalText.setText("R$: "+total);
    }

    @Override
    public void onEconomicOperationForSaleClick(int position){
        EconomicOperation economicOperationSelect = listProductSelect.get(position);

        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete,null);
        Button buttonDeleteEconomicOperation =  mDialogView.findViewById(R.id.buttonDeleteEconomicOperation);
        Button buttonAddQuantity = mDialogView.findViewById(R.id.buttonAddQuantity);
        SeekBar seekBar = mDialogView.findViewById(R.id.seekBarQuantityForSale);
        TextView counter = mDialogView.findViewById(R.id.textViewCounter);
        TextView economicOperationselectName = mDialogView.findViewById(R.id.textViewNameProduct);
        seekBar.setProgress(Integer.parseInt(quantitySelectArrayList.get(position).toString()));

        economicOperationselectName.setText(economicOperationSelect.getName());
        seekBar.setMax(economicOperationSelect.getQuantity());

        counter.setText(quantitySelectArrayList.get(position).toString());
        seekBar.setProgress(Integer.parseInt(quantitySelectArrayList.get(position).toString()));

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

        buttonAddQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(counter.getText().toString())!=0){
                    quantitySelectArrayList.add(position,Integer.valueOf(counter.getText().toString()));
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(ClosingSaleActivity.this, "Selecione uma quantidade", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDeleteEconomicOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            listProductSelect.remove(position);
            if (listProductSelect.isEmpty()){
                startActivity(new Intent(getApplicationContext(), AddSaleActivity.class));
                try {
                    this.finalize();
                } catch (Throwable throwable) { throwable.printStackTrace(); }
            }
            }
        });
        alertDialog.show();
    }

    public void SaveSeal(View view) {
        Sale sale= new Sale();


        applyDiscount();
    }

    private void applyDiscount(){

    }
}