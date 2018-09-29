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
import android.widget.Toast;

import com.example.erez0_000.weddingapp.Login_pages.HorizontalRecyclerBusinesses.BusinessMinimalactivity;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.activities.DisplayBusinessListActivity;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.db_classes.Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessChartFragment extends DialogFragment
        implements BusinessChartRecyclerViewAdapter.CreateOnClickListener {
    private ArrayList<BusinessesInChart> lstBusinesses;
    private RecyclerView recyclerView;

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
        initRecyclerView();
        // TODO: 29/09/2018 get user's businessInChart and init the reciclerView
//        Database db = Database.getInstance();
//        Map<String,String> emptyMap  = Collections.emptyMap();
//        db.getBusinesses(emptyMap, new Callback<List<Businesses>>() {
//            List<Businesses> b = null;
//            @Override
//            public void onResponse(Call<List<Businesses>> call, Response<List<Businesses>> response) {
//                b = response.body();
//                for (Businesses item : b){
//                    lstBusinesses.add(new BusinessesInChart(item,250,450));
//                }
//        initRecyclerView();
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Businesses>> call, Throwable t) {
////                Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();
//                System.out.println(t.getCause());
////            Toast.makeText(DisplayBusinessListActivity.this,
////                    "מתנצלים, יש כרגע בעיות התחברות עם השרת. אנא נסו מאוחר יותר",Toast.LENGTH_LONG).show();
//            }
//        });
        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
//        lin.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//
        BusinessChartRecyclerViewAdapter businessChartRecyclerViewAdapter = new BusinessChartRecyclerViewAdapter(this, lstBusinesses);
//
        recyclerView.setAdapter(businessChartRecyclerViewAdapter);
    }

    @Override
    public void openBusiness(Businesses businesses) {
        Intent i = new Intent(this.getActivity(), BusinessMinimalactivity.class);
        i.putExtra("curBusiness", (Serializable) businesses);


        startActivity(i);
    }

    public void insertBusinesses(ArrayList<BusinessesInChart> businses) {
        lstBusinesses = new ArrayList<>();


//        lstBusinesses = businses;

    }
}
