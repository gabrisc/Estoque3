package com.example.estoque3.Activity.Establishment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.estoque3.R;
import com.example.estoque3.ui.main.PageViewModel;

public class FloatingCapitalFragment extends Fragment {

    private static final String ARG_PARAM2 = "4";
    private PageViewModel pageViewModel;

    public FloatingCapitalFragment() { }

    public static FloatingCapitalFragment newInstance( int index) {
        FloatingCapitalFragment fragment = new FloatingCapitalFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM2, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel=new ViewModelProvider(this).get(PageViewModel.class);
        int index =3;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_PARAM2);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_floating_capital, container, false);
    }
}