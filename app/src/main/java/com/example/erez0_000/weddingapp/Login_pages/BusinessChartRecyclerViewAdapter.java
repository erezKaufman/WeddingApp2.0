package com.example.erez0_000.weddingapp.Login_pages;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Businesses;

import java.util.ArrayList;
import java.util.List;


public class BusinessChartRecyclerViewAdapter extends
        RecyclerView.Adapter<BusinessChartRecyclerViewAdapter.ChartViewHolder> {
    private ArrayList<BusinessesInChart> businessList;
    private RequestOptions option;
    CreateOnClickListener listener;

    public BusinessChartRecyclerViewAdapter(CreateOnClickListener listener,ArrayList<BusinessesInChart> buslist){
        businessList = new ArrayList<>();
        if (buslist != null){
            businessList = buslist;
         }else

        this.listener = listener;
    }

    @NonNull
    @Override
    public ChartViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.business_chart_recycler_view, parent, false);
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        return new ChartViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        final BusinessesInChart business = businessList.get(position);
        holder.tv_name.setText(business.getCurBusiness().getName());
        holder.tv_address.setText(business.getCurBusiness().getAddress());
        holder.tv_region.setText(business.getCurBusiness().getRegion());
        holder.minPrice.setText(String.format("%d",business.getMinPrice()));
        holder.maxPrice.setText(String.format("%d",business.getMaxPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.openBusiness(business.getCurBusiness());
            }
        });
        // Load Image from the internet and set it into Imageview using Glide
        Glide.with(holder.img_thumbnail.getContext())
                .load(business.getCurBusiness().getImage()).apply(option).into(holder.img_thumbnail);



    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }


    public class ChartViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_address;
        TextView tv_region;
        TextView minPrice;
        TextView maxPrice;
        ImageView img_thumbnail;
        LinearLayout view_container;

        public ChartViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            tv_name = itemView.findViewById(R.id.anime_name);
            tv_address = itemView.findViewById(R.id.address);
            tv_region = itemView.findViewById(R.id.region);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);
            maxPrice = itemView.findViewById(R.id.min_price);
            minPrice = itemView.findViewById(R.id.max_price);

        }
    }


    public interface CreateOnClickListener {
        public void openBusiness(Businesses businesses);
    }
}
