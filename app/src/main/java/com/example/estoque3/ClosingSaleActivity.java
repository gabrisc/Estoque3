package com.example.estoque3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estoque3.Activity.AddActivitys.AddSaleActivity;
import com.example.estoque3.Activity.MainScreens.MainActivity;
import com.example.estoque3.entity.EconomicOperationForSaleVo;
import com.example.estoque3.entity.Sale;
import com.example.estoque3.util.TypeOfProduct;
import com.example.estoque3.util.adapters.AdapterEconomicOperationForSaleVo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.example.estoque3.Activity.AddActivitys.AddSaleActivity.clientSelected;
import static com.example.estoque3.Activity.AddActivitys.AddSaleActivity.economicOperationForSaleVoArrayList;
import static com.example.estoque3.util.FireBaseConfig.firebaseDbReference;

public class ClosingSaleActivity extends AppCompatActivity implements AdapterEconomicOperationForSaleVo.OnEconomicOperationForSaleVo{
    private Spinner spinnerPaymentstype;
    private RecyclerView recyclerView;
    private AlertDialog alertDialog;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private Set<EconomicOperationForSaleVo> set = new HashSet<>();
    private Set<EconomicOperationForSaleVo> set2 = new HashSet<>();
    private Double discount;
    private Sale sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closing_sale);
        recyclerView = findViewById(R.id.RecyclerViewEconomicOperationSelected);
        sale = new Sale(firebaseDbReference.push().getKey(),
                simpleDateFormat.format(System.currentTimeMillis()),
                clientSelected,
                0.0,
                0.0);

        set.addAll(economicOperationForSaleVoArrayList);
        set2.addAll(economicOperationForSaleVoArrayList);

        setClientNameInActivity(sale);
        CalcTotalValue(sale);
        loadList(sale);
        setListPaymentsTypes(sale);
        setDateInActivity();
    }

    public void loadList(Sale sale){
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AdapterEconomicOperationForSaleVo adapterEconomicOperationForSaleVo = new AdapterEconomicOperationForSaleVo(set,getApplicationContext(), this::onEconomicOperationForSaleVoClick);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterEconomicOperationForSaleVo);
    }

    private void setListPaymentsTypes( Sale sale){
        String[] listOfPaymentsType = {"DEBITO","CREDITO","DINHEIRO","CHEQUE","BOLETO","TRANSF. BANCARIA","OUTROS"};
        spinnerPaymentstype = findViewById(R.id.listOfPaymentsType);
        spinnerPaymentstype.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.item_list_spinner, listOfPaymentsType));
        sale.setPaymentType(spinnerPaymentstype.getSelectedItem().toString());
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    private void setDateInActivity() {
        TextView date = findViewById(R.id.TextViewDateOfBuy2);
        date.setText(simpleDateFormat.format(System.currentTimeMillis()));
    }

    public void SaveSeal(View view) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Deseja aplicar um desconto ?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                applyDiscount(sale);
            }
        });
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            closeSeal(sale);
            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    private void closeSeal(Sale sale){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Deseja concluir a venda?\n Total: R$ "+ sale.getTotalValue());
        alertDialog.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveSale(sale);
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

    private void saveSale(Sale sale) {
        List<EconomicOperationForSaleVo> economicOperationForSaleVoList= new ArrayList<>();
        for(Iterator<EconomicOperationForSaleVo> it = set.iterator(); it.hasNext();){
            EconomicOperationForSaleVo e= it.next();
            economicOperationForSaleVoList.add(e);
        }
        sale.setEconomicOperationForSaleVoList(economicOperationForSaleVoList);
        sale.save();
        Toast.makeText(ClosingSaleActivity.this, "Adicionado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onEconomicOperationForSaleVoClick(int position) {
        EconomicOperationForSaleVo economicOperationForSaleVo = set.iterator().next();
        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_delete,null);

        ImageButton buttonDeleteEconomicOperation=mDialogView.findViewById(R.id.buttonDeleteDiscount);
        ImageButton buttonAddQuantity=mDialogView.findViewById(R.id.buttonAddDiscount);
        SeekBar seekBar=mDialogView.findViewById(R.id.seekBarQuantityForSale);
        TextView counter = mDialogView.findViewById(R.id.textViewCounter);
        seekBar.setProgress(Integer.parseInt(String.valueOf(economicOperationForSaleVo.getQuantitySelect())));
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

        buttonAddQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(counter.getText().toString())!=0){
                    economicOperationForSaleVo.setQuantitySelect(Integer.valueOf(counter.getText().toString()));
                    loadList(sale);
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(ClosingSaleActivity.this, "Selecione uma quantidade", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonDeleteEconomicOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set.remove(position);
                if (set.isEmpty()){
                    startActivity(new Intent(getApplicationContext(), AddSaleActivity.class));
                    try { this.finalize(); } catch (Throwable throwable) { throwable.printStackTrace(); }
                }
                loadList(sale);
            }});
        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void CalcTotalValue(Sale sale) {
        Double total = 0.0;
        for(Iterator<EconomicOperationForSaleVo> it = set.iterator(); it.hasNext();){
            EconomicOperationForSaleVo e= it.next();
            if(e.getEconomicOperation().getType().equals(TypeOfProduct.PRODUTO.toString())){
                total += e.getQuantitySelect()*e.getEconomicOperation().getSealValue();
            }else{
                total += e.getEconomicOperation().getSealValue();
            }
        }
        sale.setTotalValue(total);
        TextView finalText = findViewById(R.id.textViewFinalValue);
        finalText.setText("R$:"+total);
    }

    private void setClientNameInActivity(Sale sale) {
        TextView clientName = findViewById(R.id.textViewClientName);
        clientName.setText(sale.getClient().getNome().toUpperCase());
    }

    private void applyDiscount(Sale sale){
        View mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_discount,null);
        ImageButton butttonCancel = mDialogView.findViewById(R.id.buttonDeleteDiscount);
        ImageButton buttonAddDiscount = mDialogView.findViewById(R.id.buttonAddDiscount);
        EditText editText = mDialogView.findViewById(R.id.editTextDiscount);
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(mDialogView).setTitle("DESCONTO");
        alertDialog=builder.create();
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        buttonAddDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sale.setDiscount( Double.valueOf( String.valueOf( editText.getText())));

                if (sale.getDiscount().equals(0)){
                    Toast.makeText(ClosingSaleActivity.this, "Selecione uma quantidade", Toast.LENGTH_SHORT).show();
                }if (sale.getTotalValue().equals(sale.getDiscount())) {
                    Toast.makeText(ClosingSaleActivity.this, "O desconto esta iqual ao total", Toast.LENGTH_SHORT).show();
                }else{
                    sale.setTotalValue(sale.getTotalValue() - Double.parseDouble(editText.getText().toString()));
                    closeSeal(sale);
                }
                alertDialog.dismiss();
            }});
        butttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSeal(sale);
                CalcTotalValue(sale);
            }
        });
        alertDialog.show();
    }
}