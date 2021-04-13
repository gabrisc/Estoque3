package com.example.estoque3.Activity.LoginScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.estoque3.Activity.MainScreens.MainActivity;
import com.example.estoque3.R;
import com.example.estoque3.entity.User;
import com.example.estoque3.util.FireBaseConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class SingInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
    }
    public void login(View view){
        EditText textEmail=findViewById(R.id.editEmailSigIn);
        EditText textPassword=findViewById(R.id.editPasswordSigIn);

        if (textEmail.getText() == null){
            Toast toast=Toast. makeText(getApplicationContext(),"O E-mail está vazio",Toast. LENGTH_LONG);
            toast. show();
        }else if (textPassword.getText() == null) {
            Toast toast=Toast. makeText(getApplicationContext(),"A senha está vazia", Toast. LENGTH_LONG);
            toast. show();

        }else {
            SingUpUser(textEmail.getText().toString(),textPassword.getText().toString());
        }
    }

    private void SingUpUser(String email, String password) {
        FireBaseConfig.firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if (task.isSuccessful()){
                        startActivity(new Intent( getApplicationContext(), MainActivity.class));
                    }
                }catch (Exception e){
                    Toast toast=Toast. makeText(getApplicationContext(),e.getMessage(),Toast. LENGTH_LONG);
                    toast. show();
                }
            }
        });
    }


}