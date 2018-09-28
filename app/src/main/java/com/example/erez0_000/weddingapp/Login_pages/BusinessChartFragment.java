package com.example.erez0_000.weddingapp.Login_pages;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.parseJSON.adapters.RecyclerViewAdapter;

import java.util.List;

public class BusinessChartFragment extends DialogFragment {
    private List<Businesses> lstBusinesses;
    private RecyclerView recyclerView ;
    private RecyclerViewAdapter grecyclerViewAdapter;

    public static BusinessChartFragment newInstance() {

        Bundle args = new Bundle();

        BusinessChartFragment fragment = new BusinessChartFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
