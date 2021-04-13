package com.example.estoque3.Activity.LoginScreens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.estoque3.R;
import com.example.estoque3.entity.User;
import com.example.estoque3.util.Base64Custom;
import com.example.estoque3.util.FireBaseConfig;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SingUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
    }

    public void VerefyFields(View view){

        User user= new User();
        EditText textName=  findViewById(R.id.editName);
        EditText textEmail= findViewById(R.id.editEmailCad);
        EditText textPassword= findViewById(R.id.editPasswordCad);

        if(textName.getText() == null){
            Toast toast=Toast. makeText(getApplicationContext(),"O nome está vazio",Toast. LENGTH_LONG);
            toast. show();
        }else if (textEmail.getText() == null){
            Toast toast=Toast. makeText(getApplicationContext(),"O E-mail está vazio",Toast. LENGTH_LONG);
            toast. show();
        }else if (textPassword.getText() == null) {
            Toast toast=Toast. makeText(getApplicationContext(),"A senha está vazia",Toast. LENGTH_LONG);
            toast. show();
        }else {
            user.setName(textName.getText().toString());
            user.setEmail(textEmail.getText().toString());
            user.setPassword(textPassword.getText().toString());

            SingUpUser(user);
        }
    }

    private void SingUpUser(User user) {
        FireBaseConfig.firebaseAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String idUser= Base64Custom.Code64(user.getEmail());
                    user.setId(idUser);
                    user.save();
                    finish();
                }else{
                    try {
                        throw task.getException();
                    }catch (Exception e){
                        Toast toast=Toast. makeText(getApplicationContext(),e.getMessage(),Toast. LENGTH_LONG);
                        toast. show();
                    }
                }
            }
        });}

}