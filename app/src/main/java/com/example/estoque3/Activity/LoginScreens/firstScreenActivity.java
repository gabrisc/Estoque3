package com.example.estoque3.Activity.LoginScreens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.estoque3.Activity.MainScreens.MainActivity;
import com.example.estoque3.R;
import com.example.estoque3.util.FireBaseConfig;

public class firstScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
    }



    protected void onStart(){
        super.onStart();
        verifyLogin();
    }

    private void verifyLogin() {
        if (FireBaseConfig.firebaseAuth.getCurrentUser()!=null){
            openMenu();
        }
    }

    private void openMenu(){startActivity(new Intent(this, MainActivity.class));}

    public void bt_singIn(View view){
        startActivity(new Intent(this, SingInActivity.class));
    }

    public void bt_singUp(View view){
        startActivity(new Intent(this, SingUpActivity.class));
    }



}