package com.example.estoque3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.estoque3.Activity.Establishment.ActivityEstablishmentAndCalc;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void nextScreen(View view){
            startActivity(new Intent(getApplicationContext(), ActivityEstablishmentAndCalc.class));
        }

}
