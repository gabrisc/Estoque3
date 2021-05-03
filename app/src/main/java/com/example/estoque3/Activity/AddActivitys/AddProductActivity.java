package com.example.estoque3.Activity.AddActivitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.estoque3.Activity.MainScreens.MainActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.util.TypeOfProduct;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import static com.example.estoque3.util.ConvertsUtil.stringToInteger;
import static com.example.estoque3.util.FireBaseConfig.firebaseDbReference;


public class AddProductActivity extends AppCompatActivity {

    private TextView counter;
    private MaterialButtonToggleGroup toggleGroup;
    private String type;
    public TypeOfProduct typeOfProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        SeekBar quantity = findViewById(R.id.seekBarQuantity);
        counter = findViewById(R.id.counter);
        toggleGroup = findViewById(R.id.ToggleGroup);

        quantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                counter.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
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
        }else if(type!=String.valueOf(typeOfProduct.SERVIÇO) && Integer.parseInt(counter.getText().toString())==0){
                Toast toast=Toast. makeText(getApplicationContext(),"Entre com a quantidade de produtos",Toast. LENGTH_SHORT);
                toast. show();
        }else if (type==null){
            Toast toast=Toast. makeText(getApplicationContext(),"Escolha um tipo",Toast. LENGTH_SHORT);
            toast. show();
        }else{
            saveProduct(new EconomicOperation(productName.getText().toString(),
                    Double.parseDouble(sellValue.getText().toString()),
                    Double.parseDouble(buyValue.getText().toString()),
                    type,
                    stringToInteger(counter.getText().toString()),
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
        this.finish();
    }
}