package com.example.erez0_000.weddingapp.parseJSON.adapters;
//package com.example.erez0_000.weddingapp.todos_section;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.erez0_000.weddingapp.activities.BusinessesActivity;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.R;


import java.io.Serializable;
import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Businesses> mData ;
    RequestOptions option;



    public RecyclerViewAdapter(Context mContext, List<Businesses> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
//        view = inflater.inflate(R.layout.display_list_business_raw,parent,false) ;
        view = LayoutInflater.from(mContext).inflate(R.layout.display_list_business_raw,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(mContext, BusinessesActivity.class);
                i.putExtra("curBusiness",(Serializable)mData.get(viewHolder.getAdapterPosition()));


                mContext.startActivity(i);

            }
        });



        viewHolder.itemView.setBackgroundResource(R.color.transp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.tv_name.setText(takeFirstnChars(mData.get(position).getName()));
        holder.tv_address.setText(mData.get(position).getAddress());
        holder.tv_region.setText(mData.get(position).getRegion());
        holder.tv_type.setText(mData.get(position).getBusiness_type());
        holder.img_thumbnail.setImageAlpha(200);
        // Load Image from the internet and set it into Imageview using Glide

        Glide.with(mContext).load(mData.get(position).getImage()).apply(option).into(holder.img_thumbnail);


    }

    private String takeFirstnChars(String name) {
        return name.substring(0, Math.min(name.length(), 31));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_name ;
            TextView tv_address;
            TextView tv_region;
            TextView tv_type;
            ImageView img_thumbnail;
            CardView view_container;

        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            tv_name = itemView.findViewById(R.id.anime_name);
            tv_type = itemView.findViewById(R.id.type);
            tv_address = itemView.findViewById(R.id.address);
            tv_region = itemView.findViewById(R.id.region);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);



        }
    }

}
