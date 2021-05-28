package com.example.estoque3.Activity.UpdatesActivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.estoque3.Activity.AddActivitys.AddProductActivity;
import com.example.estoque3.Activity.MainScreens.MainActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.util.TypeOfProduct;

import static com.example.estoque3.util.ConvertsUtil.stringToInteger;
import static com.example.estoque3.util.FireBaseConfig.firebaseDbReference;

public class UpdateProductActivity extends AppCompatActivity {

    private EconomicOperation economicOperation;
    private TextView date,progressChangedValue;
    private EditText name,expense,seal;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        economicOperation = findEconomicOperationSelected();

        date = findViewById(R.id.textViewDate);
        name = findViewById(R.id.editTextNameProductForEdit);
        expense = findViewById(R.id.editTextExpenseValueforEdit);
        seal = findViewById(R.id.editTextSellValueForEdit);
        seekBar = findViewById(R.id.seekBarQuantityEdit);
        progressChangedValue = findViewById(R.id.valueOfSeek);

        date.setText(economicOperation.getDate());
        name.setText(economicOperation.getName());
        expense.setText(String.format("%s", economicOperation.getExpenseValue()));
        seal.setText(String.format("%s", economicOperation.getSealValue()));
        if(economicOperation.getType().equals(TypeOfProduct.SERVIÇO.toString())){
            seekBar.setEnabled(false);
        }else{
            progressChangedValue.setText(String.valueOf(economicOperation.getQuantity()));
            seekBar.setProgress(economicOperation.getQuantity());

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    progressChangedValue.setText(String.valueOf(progress));
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar){
                    progressChangedValue.setText(String.valueOf(economicOperation.getQuantity()));
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar){}
            });

        }
    }

    private EconomicOperation findEconomicOperationSelected() {
        EconomicOperation economicOperationSelect = new EconomicOperation();
        Bundle bundle = getIntent().getExtras();
        economicOperationSelect.setId(bundle.getString("id"));
        economicOperationSelect.setName(bundle.getString("Name"));
        economicOperationSelect.setType(bundle.getString("type"));
        economicOperationSelect.setSealValue(bundle.getDouble("SealValue"));
        economicOperationSelect.setExpenseValue(bundle.getDouble("ExpenseValue"));
        economicOperationSelect.setContributionValue(bundle.getDouble("ContributionValue"));
        economicOperationSelect.setQuantity(bundle.getInt("Quantity"));
        economicOperationSelect.setDate(bundle.getString("Date"));
        return economicOperationSelect;
    }

    public void validateFields(View v){
            if (name.getText().toString().isEmpty()){
                Toast toast=Toast. makeText(getApplicationContext(),"Entre com um nome",Toast. LENGTH_SHORT);
                toast. show();
            }else if(expense.getText().toString().isEmpty()){
                Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de compra",Toast. LENGTH_SHORT);
                toast. show();
            }else if(seal.getText().toString().isEmpty()){
                Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de venda",Toast. LENGTH_SHORT);
                toast. show();
            }else if (economicOperation.getType().equals(TypeOfProduct.PRODUTO.toString()) && Integer.parseInt(progressChangedValue.getText().toString())==0){
                    Toast toast=Toast. makeText(getApplicationContext(),"Entre com uma quantidade",Toast. LENGTH_SHORT);
                    toast. show();
            }else{
            updateEconomicOperation();
            }

}

    private void saveProduct(EconomicOperation economicOperation) {
        economicOperation.save();
    }

    private void updateEconomicOperation(){
        economicOperation.setName(name.getText().toString());
        economicOperation.setSealValue(Double.parseDouble(seal.getText().toString()));
        economicOperation.setExpenseValue(Double.parseDouble(expense.getText().toString()));

        if(economicOperation.getType().equals(TypeOfProduct.PRODUTO.toString())){
            economicOperation.setQuantity(Integer.parseInt(progressChangedValue.getText().toString()));
        }

        economicOperation.setDate(date.getText().toString());
        economicOperation.setContributionValue(calcContributionValue(economicOperation.getSealValue(),economicOperation.getExpenseValue()));

        saveProduct(economicOperation);
        Toast.makeText(this, "Alterado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void deleteEconomicOperation(View view){
        economicOperation.delete();
        Toast toast=Toast. makeText(this,"Deletado",Toast. LENGTH_SHORT);
        toast.show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private double calcContributionValue(double sellValue,double buyValue) {
        return sellValue-buyValue;
    }

}