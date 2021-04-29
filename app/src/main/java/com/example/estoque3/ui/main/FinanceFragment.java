package com.example.estoque3.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.estoque3.R;

public class FinanceFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "4";
    private PageViewModel pageViewModel;

    public static FinanceFragment newInstance(int index) {
        FinanceFragment fragment = new FinanceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER,index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index =4;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_finance, container, false);
    }
}