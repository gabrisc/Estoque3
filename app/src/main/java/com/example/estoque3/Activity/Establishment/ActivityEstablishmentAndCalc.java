package com.example.estoque3.Activity.Establishment;

import android.os.Bundle;

import com.example.estoque3.R;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.estoque3.Activity.Establishment.ui.main.SectionsPagerAdapterEstablishment;

public class ActivityEstablishmentAndCalc extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_and_calc);
        SectionsPagerAdapterEstablishment sectionsPagerAdapter = new SectionsPagerAdapterEstablishment(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager2);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    public void Create(View v){
        if(viewPager.getCurrentItem() == 0){

        }else if(viewPager.getCurrentItem() == 1){

        }else if(viewPager.getCurrentItem() == 2){

        }
    }
}