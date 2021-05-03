package com.example.estoque3.Activity.Establishment.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.estoque3.R;
import com.example.estoque3.ui.main.PageViewModel;


public class ProfitabilityFragment extends Fragment {

    private static final String ARG_PARAM1 = "5";
    private PageViewModel pageViewModel;

    public ProfitabilityFragment() {
    }

    public static ProfitabilityFragment newInstance(int param1) {
        ProfitabilityFragment fragment = new ProfitabilityFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index =5;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_PARAM1);
        }
        pageViewModel.setIndex(index);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profitability, container, false);
    }
}