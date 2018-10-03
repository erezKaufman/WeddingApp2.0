package com.example.erez0_000.weddingapp.Login_pages;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.erez0_000.weddingapp.Login_pages.HorizontalRecyclerBusinesses.BusinessMinimalactivity;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.db_classes.User;

import java.io.Serializable;
import java.util.ArrayList;

public class BusinessChartFragment extends DialogFragment
        implements BusinessChartRecyclerViewAdapter.CreateOnClickListener {
    private ArrayList<BusinessesInChart> lstBusinesses;
    private RecyclerView recyclerView;
    private OnUpdateCostsListener listener;
    public static BusinessChartFragment newInstance() {


        Bundle args = new Bundle();

        BusinessChartFragment fragment = new BusinessChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_chart_fragment, container, false);
        recyclerView = view.findViewById(R.id.business_chart);
        lstBusinesses = User.thisUser.getBusinessInChart();

        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        BusinessChartRecyclerViewAdapter businessChartRecyclerViewAdapter = new BusinessChartRecyclerViewAdapter(this, lstBusinesses, getActivity());
        recyclerView.setAdapter(businessChartRecyclerViewAdapter);
    }

    @Override
    public void openBusiness(Businesses businesses) {
        Intent i = new Intent(this.getActivity(), BusinessMinimalactivity.class);
        i.putExtra("curBusiness", (Serializable) businesses);
        startActivity(i);
    }

    @Override
    public void updateBalance() {
        listener.updateCost();
    }

    public void setListener(OnUpdateCostsListener listener){
        this.listener = listener;
    }

    public interface OnUpdateCostsListener {
        public void updateCost();
    }


}
