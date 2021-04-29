package com.example.estoque3.Activity.AddActivitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.estoque3.Activity.ListActivitys.ListClientActivity;
import com.example.estoque3.Activity.MainScreens.MainActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.Client;

import static com.example.estoque3.util.FireBaseConfig.firebaseDbReference;

public class AddClientActivity extends AppCompatActivity {

    private EditText telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
         telefone = findViewById(R.id.editTextPhone);
         telefone.setTextSize(11);
    }


    public void cancelRegistrer (View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        this.finish();
    }
    public void listClients(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void ValidFields(View view){
        EditText name = findViewById(R.id.editTextClientName);
        EditText email= findViewById(R.id.editTextEmailAddress);


        if (name.getText().equals(null)){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o nome",Toast. LENGTH_SHORT);
            toast. show();
        }else if (email.getText().equals(null) || telefone.getText().equals(null)){
            Toast toast=Toast. makeText(getApplicationContext(),"Entre com o email ou telefone",Toast. LENGTH_SHORT);
            toast. show();
        }else{
            saveClient(new Client(firebaseDbReference.push().getKey(),name.getText().toString(),email.getText().toString(),telefone.getText().toString()));
        }


}

    private void saveClient(Client client) {

        Toast toast=Toast. makeText(getApplicationContext(),client.save(),Toast. LENGTH_SHORT);
        toast. show();
        startActivity(new Intent(getApplicationContext(), AddSaleActivity.class));
    }
    public void callList(View view){
        startActivity(new Intent(getApplicationContext(), ListClientActivity.class));
    }

}