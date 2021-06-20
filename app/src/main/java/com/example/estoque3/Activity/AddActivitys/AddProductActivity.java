package com.example.estoque3.Activity.AddActivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.estoque3.Activity.MainScreens.MainActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.util.TypeOfQuantity;
import com.example.estoque3.util.TypeOfProduct;

import java.text.SimpleDateFormat;

import static com.example.estoque3.util.FireBaseConfig.firebaseDbReference;
import static com.example.estoque3.util.TypeOfProduct.PRODUTO;
import static com.example.estoque3.util.TypeOfProduct.SERVIÇO;
import static com.example.estoque3.util.TypeOfQuantity.CAIXAS;
import static com.example.estoque3.util.TypeOfQuantity.KG;
import static com.example.estoque3.util.TypeOfQuantity.UND;
import static java.lang.Integer.parseInt;


public class AddProductActivity extends AppCompatActivity {
    private Spinner ProductType,spinnerUnidadeDeMedida;
    private EditText counter;
    private TextView textViewQuantidade;
    public TypeOfProduct typeOfProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        counter = findViewById(R.id.counter);
        ProductType = findViewById(R.id.spinner4);
        spinnerUnidadeDeMedida= findViewById(R.id.spinnerUnidadeDeMedida);
        textViewQuantidade = findViewById(R.id.textViewQuantidade);

        TypeOfProduct[] listOfPaymentsType = {PRODUTO, SERVIÇO};
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.item_list_spinner, listOfPaymentsType);

        TypeOfQuantity[] listOfMed = {UND, CAIXAS, KG};
        ArrayAdapter adapter =new ArrayAdapter(getApplicationContext(),R.layout.item_list_spinner,listOfMed);


        spinnerUnidadeDeMedida.setAdapter(adapter);
        ProductType.setAdapter(arrayAdapter);

        ProductType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (ProductType.getSelectedItem().equals(SERVIÇO.toString())) {
                    spinnerUnidadeDeMedida.setEnabled(false);
                    spinnerUnidadeDeMedida.setVisibility(View.INVISIBLE);
                    counter.setText("0");
                    counter.setVisibility(View.INVISIBLE);
                    textViewQuantidade.setVisibility(View.INVISIBLE);

                }else{
                    spinnerUnidadeDeMedida.setEnabled(true);
                    spinnerUnidadeDeMedida.setVisibility(View.VISIBLE);
                    counter.setVisibility(View.VISIBLE);
                    textViewQuantidade.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}});
        counter.setText("0");
    }

    public void ValidFields(View view){
        EditText productName = findViewById(R.id.editTextNameProduct);
        EditText buyValue = findViewById(R.id.editTextBuyValue);
        EditText sellValue = findViewById(R.id.editTextSellValue);

        if (productName.getText().toString().equals(null) ){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com um nome",Toast. LENGTH_SHORT);
            toast. show();
        }

        if(buyValue.getText().toString().equals(null)){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de compra do produto",Toast. LENGTH_SHORT);
            toast. show();

        }

        if (sellValue.getText().toString().equals(null)){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de venda do produto",Toast. LENGTH_SHORT);
            toast. show();
        }

        if(ProductType.getSelectedItem()!=SERVIÇO && parseInt(counter.getText().toString())==0){
                Toast toast=Toast. makeText(getApplicationContext(),"Entre com a quantidade de produtos",Toast. LENGTH_SHORT);
                toast. show();
        }

        if (ProductType.getSelectedItem()==null){
            Toast toast=Toast. makeText(getApplicationContext(),"Escolha um tipo",Toast. LENGTH_SHORT);
            toast. show();
        }
        if (ProductType.getSelectedItem()== PRODUTO){
            if (Integer.parseInt(counter.getText().toString())==0){
                Toast toast=Toast. makeText(getApplicationContext(),"Adicione a quantidade",Toast. LENGTH_SHORT);
                toast. show();
            }else{
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
                saveProduct(new EconomicOperation(productName.getText().toString(),Double.parseDouble(sellValue.getText().toString()),
                        Double.parseDouble(buyValue.getText().toString()),ProductType.getSelectedItem().toString(),
                        Integer.parseInt(counter.getText().toString()),simpleDateFormat.format(System.currentTimeMillis()),
                        calcContributionValue(Double.parseDouble(sellValue.getText().toString()),Double.parseDouble(buyValue.getText().toString())),
                        spinnerUnidadeDeMedida.getSelectedItem().toString()));
            }
        }
        if (ProductType.getSelectedItem()==SERVIÇO){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            saveProduct(new EconomicOperation(productName.getText().toString(),
                    Double.parseDouble(sellValue.getText().toString()),
                    Double.parseDouble(buyValue.getText().toString()),
                    ProductType.getSelectedItem().toString(),
                    simpleDateFormat.format(System.currentTimeMillis()),
                    calcContributionValue(Double.parseDouble(sellValue.getText().toString()),Double.parseDouble(buyValue.getText().toString()))
            ));
        }
    }

    private double calcContributionValue(double sellValue,double buyValue) {
        return sellValue-buyValue;
    }

    private void saveProduct(EconomicOperation economicOperation) {
        economicOperation.setId(firebaseDbReference.push().getKey());
        economicOperation.save();
        Toast.makeText(AddProductActivity.this, "Adicionado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void cancelRegistrer (View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}