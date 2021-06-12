package com.example.estoque3.Activity.UpdatesActivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import static com.example.estoque3.util.TypeOfQuantity.CAIXAS;
import static com.example.estoque3.util.TypeOfQuantity.KG;
import static com.example.estoque3.util.TypeOfQuantity.UNIDADES;
import static java.lang.Integer.parseInt;

public class UpdateProductActivity extends AppCompatActivity {

    private EconomicOperation economicOperation;
    private TextView date,quantityText;
    private EditText name,expense,seal,counter;
    private Spinner typeOfQuantity;
    private ImageButton AddButtonUpdate,LessButtonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        economicOperation = findEconomicOperationSelected();

        date = findViewById(R.id.textViewDate);
        name = findViewById(R.id.editTextNameProductForEdit);
        counter = findViewById(R.id.valueOfSeekUpdate);
        typeOfQuantity = findViewById(R.id.spinnerUnidadeDeMedidaUpdate);
        expense = findViewById(R.id.editTextExpenseValueforEdit);
        seal = findViewById(R.id.editTextSellValueForEdit);
        AddButtonUpdate = findViewById(R.id.addButtonUpdate);
        LessButtonUpdate = findViewById(R.id.lessButtonUptade);
        quantityText = findViewById(R.id.textViewQuant);

        date.setText(economicOperation.getDate());
        name.setText(economicOperation.getName());
        counter.setText(String.format("%d", parseInt(String.valueOf(economicOperation.getQuantity()))));
        expense.setText(String.format("%s", economicOperation.getExpenseValue()));
        seal.setText(String.format("%s", economicOperation.getSealValue()));

        TypeOfQuantity[] listOfMed = {UNIDADES, CAIXAS, KG};
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.item_list_spinner,listOfMed);
        typeOfQuantity.setAdapter(adapter);


        if(economicOperation.getType().equals(TypeOfProduct.SERVIÇO.toString())){
            typeOfQuantity.setVisibility(View.INVISIBLE);
            counter.setVisibility(View.INVISIBLE);
            AddButtonUpdate.setVisibility(View.INVISIBLE);
            LessButtonUpdate.setVisibility(View.INVISIBLE);
            quantityText.setVisibility(View.INVISIBLE);
        }

        AddButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.setText(String.format("%d", parseInt(counter.getText().toString()) + 1));
            }});
        LessButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.setText(String.format("%d", parseInt(counter.getText().toString()) - 1));
            }});
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
        economicOperationSelect.setTypeQuantity("typeQuantity");
        return economicOperationSelect;
    }

    public void validateFields(View v){
            if (name.getText().toString().isEmpty()){
                Toast toast=Toast. makeText(getApplicationContext(),"Entre com um nome",Toast. LENGTH_SHORT);
                toast. show();
            }
            if(expense.getText().toString().isEmpty()){
                Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de compra",Toast. LENGTH_SHORT);
                toast. show();
            }
            if(seal.getText().toString().isEmpty()){
                Toast toast=Toast. makeText(getApplicationContext(),"Entre com o valor de venda",Toast. LENGTH_SHORT);
                toast. show();
            }
            if (economicOperation.getType().equals(TypeOfProduct.PRODUTO.toString()) && Integer.parseInt(counter.getText().toString())==0){
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
        economicOperation.setTypeQuantity(typeOfQuantity.getSelectedItem().toString());

        if(economicOperation.getType().equals(TypeOfProduct.PRODUTO.toString())){
            economicOperation.setQuantity(Integer.parseInt(counter.getText().toString()));
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