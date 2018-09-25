package com.example.erez0_000.weddingapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.erez0_000.weddingapp.R;

public class InfoFragment extends Fragment {

    public InfoFragment(){}

    private TextView tv_name, tv_region, tv_mail, tv_address;
    private ImageView img;
    private String name, description, region, mail,  address,  image_url;
    private int nb_phone;
    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    public void setValues(String name, String description, String region, String mail, int nb_phone,
                          String address, String image_url){
        this.name = name;
        this.mail = mail;
//        tv_description.setText(description);
        this.address = address;
        this.region = region;
        this.description = description;
        this.nb_phone = nb_phone;
        this.image_url = image_url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.business_info_fragment, container, false);
        tv_name = view.findViewById(R.id.aa_anime_name);
        tv_region = view.findViewById(R.id.aa_region);
        tv_mail = view.findViewById(R.id.aa_mail);
//        TextView tv_description = findViewById(R.id.aa_description);
        tv_address = view.findViewById(R.id.aa_address);
        img = view.findViewById(R.id.aa_thumbnail);


        tv_name.setText(name);
        tv_mail.setText(mail);
//        tv_description.setText(description);
        tv_address.setText(address);
        tv_region.setText(region);
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);
        return view;
        //TODO add business description here, maybe add photos
    }
}
