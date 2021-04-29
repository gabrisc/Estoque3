package com.example.estoque3.Activity.MainScreens;

import android.content.Intent;
import android.os.Bundle;
import com.example.estoque3.Activity.AddActivitys.AddProductActivity;
import com.example.estoque3.Activity.AddActivitys.AddSaleActivity;
import com.example.estoque3.R;
import com.example.estoque3.TestActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.estoque3.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }


    public void Create(View view) {
        if (viewPager.getCurrentItem()==0){
            startActivity(new Intent(getApplicationContext(), TestActivity.class));
        }else if (viewPager.getCurrentItem()==1){
            startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
        }else if (viewPager.getCurrentItem()==2){
            startActivity(new Intent(getApplicationContext(), AddSaleActivity.class));
        }
    }
}


