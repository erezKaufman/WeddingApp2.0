package com.example.erez0_000.weddingapp.Login_pages;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.parseJSON.adapters.RecyclerViewAdapter;

import java.util.List;

public class BusinessChartRecyclerViewAdapter extends
        RecyclerView.Adapter<BusinessChartRecyclerViewAdapter.ChartViewHolder>{

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ChartViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName;

        public ChartViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.tv_id);

        }
    }



    public static interface CreateOnClickListner{

    }
}
