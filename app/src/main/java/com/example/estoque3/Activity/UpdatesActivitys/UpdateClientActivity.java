package com.example.estoque3.Activity.UpdatesActivitys;

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
import com.example.estoque3.entity.Client;
import com.example.estoque3.entity.EconomicOperation;
import com.example.estoque3.util.TypeOfProduct;

import java.text.SimpleDateFormat;


public class UpdateClientActivity extends AppCompatActivity {

    private Client clientSelect;
    private TextView date;
    private EditText name, email, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client2);

        clientSelect = findClientSelected();


        date = findViewById(R.id.TextViewDateClient2);
        name = findViewById(R.id.editTextClientName);
        email = findViewById(R.id.editTextEmailAddress);
        phoneNumber = findViewById(R.id.editTextPhone);

        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd/MM/yyyy");

        date.setText(simpleDateFormat.format(System.currentTimeMillis()));
        name.setText(clientSelect.getNome());
        email.setText(clientSelect.getEmail());
        phoneNumber.setText(clientSelect.getTelefone());
        date.setText(clientSelect.getDate());

    }


    private Client findClientSelected() {
        Client client = new Client();
        Bundle bundle = getIntent().getExtras();

        client.setId(bundle.getString("id"));
        client.setNome(bundle.getString("name"));
        client.setEmail(bundle.getString("email"));
        client.setTelefone(bundle.getString("phoneNumber"));
        client.setDate(bundle.getString("date"));

        return client;
    }

    public void validateFields(View v){
        if (name.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Não deixe o nome vazio",Toast. LENGTH_SHORT);
            toast. show();
        }else if(email.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Não deixe o email vazio",Toast. LENGTH_SHORT);
            toast. show();
        }else if(phoneNumber.getText().toString().isEmpty()){
            Toast toast=Toast. makeText(getApplicationContext(),"Não deixe o telefone vazio",Toast. LENGTH_SHORT);
            toast. show();
        } else{
            updateClient();
        }

    }

    private void saveClient(Client client) {
        client.save();
    }

    private void updateClient(){

        clientSelect.setNome(name.getText().toString());
        clientSelect.setTelefone(phoneNumber.getText().toString());
        clientSelect.setEmail(email.getText().toString());
        clientSelect.setDate(date.getText().toString());

        saveClient(clientSelect);
        Toast.makeText(this, "Alterado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void deleteEconomicOperation(View view){
        clientSelect.delete();
        Toast toast=Toast. makeText(this,"Deletado",Toast. LENGTH_SHORT);
        toast.show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private double calcContributionValue(double sellValue,double buyValue) {
        return sellValue-buyValue;
    }
}