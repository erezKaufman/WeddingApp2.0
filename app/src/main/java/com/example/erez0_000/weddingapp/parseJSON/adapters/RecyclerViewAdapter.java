package com.example.erez0_000.weddingapp.parseJSON.adapters;
//package com.example.erez0_000.weddingapp.todos_section;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.erez0_000.weddingapp.activities.BusinessesActivity;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.example.erez0_000.weddingapp.R;


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
        view = inflater.inflate(R.layout.display_list_business_raw,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(mContext, BusinessesActivity.class);
                i.putExtra("anime_name",mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("anime_description",mData.get(viewHolder.getAdapterPosition()).getDescription());
                i.putExtra("anime_region",mData.get(viewHolder.getAdapterPosition()).getRegion());
                i.putExtra("anime_mail",mData.get(viewHolder.getAdapterPosition()).getMail());
                i.putExtra("anime_phone",mData.get(viewHolder.getAdapterPosition()).getPhone());
                i.putExtra("anime_address",mData.get(viewHolder.getAdapterPosition()).getAddress());
                i.putExtra("anime_img",mData.get(viewHolder.getAdapterPosition()).getImage());

                i.putExtra("anime_bid",mData.get(viewHolder.getAdapterPosition()).getImage());
                i.putExtra("anime_winter_price",mData.get(viewHolder.getAdapterPosition()).getWinter_price());
                i.putExtra("anime_summer_price",mData.get(viewHolder.getAdapterPosition()).getSummer_price());
                i.putExtra("business_type",mData.get(viewHolder.getAdapterPosition()).getBusiness_type());
                i.putExtra("handikaped",mData.get(viewHolder.getAdapterPosition()).isHandikaped());
                i.putExtra("OccupiedDates",mData.get(viewHolder.getAdapterPosition()).getOccupiedDates());
//                i.putExtra("kosher",mData.get(viewHolder.getAdapterPosition()).isKosher());


                mContext.startActivity(i);

            }
        });




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_address.setText(mData.get(position).getAddress());
        holder.tv_region.setText(mData.get(position).getRegion());
        holder.tv_mail.setText(mData.get(position).getMail());

        // Load Image from the internet and set it into Imageview using Glide

        Glide.with(mContext).load(mData.get(position).getImage()).apply(option).into(holder.img_thumbnail);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_name ;
            TextView tv_address;
            TextView tv_region;
            TextView tv_mail;
            ImageView img_thumbnail;
            LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            tv_name = itemView.findViewById(R.id.anime_name);
            tv_mail = itemView.findViewById(R.id.mail);
            tv_address = itemView.findViewById(R.id.address);
            tv_region = itemView.findViewById(R.id.region);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);



        }
    }

}
