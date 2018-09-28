package com.example.erez0_000.weddingapp.Login_pages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.parseJSON.adapters.RecyclerViewAdapter;

import java.util.List;

public class BusinessChartRecyclerViewAdapter extends
        RecyclerView.Adapter<BusinessChartRecyclerViewAdapter.CreateOnClickListner>{


    @NonNull
    @Override
    public CreateOnClickListner onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CreateOnClickListner holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface CreateOnClickListner{

    }
}
