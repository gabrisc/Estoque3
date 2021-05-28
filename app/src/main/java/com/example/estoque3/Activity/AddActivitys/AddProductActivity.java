package com.example.estoque3.Activity.AddActivitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.estoque3.Activity.MainScreens.MainActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.util.TypeOfProduct;

import java.text.SimpleDateFormat;

import static com.example.estoque3.util.ConvertsUtil.stringToInteger;
import static com.example.estoque3.util.FireBaseConfig.firebaseDbReference;


public class AddProductActivity extends AppCompatActivity {
    private Spinner ProductType;
    private TextView counter;
    public TypeOfProduct typeOfProduct;
    public TextView date;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        SeekBar quantity = findViewById(R.id.seekBarQuantity);
        counter = findViewById(R.id.counter);
        ProductType = findViewById(R.id.spinner4);

        String[] listOfPaymentsType = {TypeOfProduct.PRODUTO.toString(), TypeOfProduct.SERVIÇO.toString()};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.item_list_spinner, listOfPaymentsType);

        ProductType.setAdapter(arrayAdapter);
        ProductType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (ProductType.getSelectedItem().equals(TypeOfProduct.SERVIÇO.toString())) {
                    quantity.setEnabled(false);
                    counter.setText("0");
                }else{
                    quantity.setEnabled(true);
                    quantity.setProgress(0);
                    quantity.animate().start();
                    counter.setText("0");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                counter.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }




    public void ValidFields(View view){
        EditText productName = findViewById(R.id.editTextNameProduct);
        EditText buyValue = findViewById(R.id.editTextBuyValue);
        EditText sellValue = findViewById(R.id.editTextSellValue);


        if (productName.getText().toString().equals(null) ){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com um nome",Toast. LENGTH_SHORT);
            toast. show();
        } else if(buyValue.getText().toString().equals(null)){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de compra do produto",Toast. LENGTH_SHORT);
            toast. show();

        }else if (sellValue.getText().toString().equals(null)){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de venda do produto",Toast. LENGTH_SHORT);
            toast. show();
        }else if(ProductType.getSelectedItem()!=String.valueOf(typeOfProduct.SERVIÇO) && Integer.parseInt(counter.getText().toString())==0){
                Toast toast=Toast. makeText(getApplicationContext(),"Entre com a quantidade de produtos",Toast. LENGTH_SHORT);
                toast. show();
        }else if (ProductType.getSelectedItem()==null){
            Toast toast=Toast. makeText(getApplicationContext(),"Escolha um tipo",Toast. LENGTH_SHORT);
            toast. show();
        }else{
            saveProduct(new EconomicOperation(productName.getText().toString(),
                    Double.parseDouble(sellValue.getText().toString()),
                    Double.parseDouble(buyValue.getText().toString()),
                    ProductType.getSelectedItem().toString(),
                    stringToInteger(counter.getText().toString()),
                    simpleDateFormat.format(System.currentTimeMillis()),
                    calcContributionValue(Double.parseDouble(sellValue.getText().toString()),Double.parseDouble(buyValue.getText().toString()))));
        }
    }

    private double calcContributionValue(double sellValue,double buyValue) {
        return sellValue-buyValue;
    }

    private void saveProduct(EconomicOperation economicOperation) {
        economicOperation.setId(firebaseDbReference.push().getKey());
        Toast.makeText(AddProductActivity.this, economicOperation.save(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void cancelRegistrer (View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }
}