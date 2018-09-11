package com.demotxt.myapp.parseJSON.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demotxt.myapp.parseJSON.R ;

public class BusinessesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        // hide the default actionbar
        getSupportActionBar().hide();

        // Recieve data

        String name  = getIntent().getExtras().getString("anime_name");
        String description = getIntent().getExtras().getString("anime_description");
        String region = getIntent().getExtras().getString("anime_region") ;
        String mail = getIntent().getExtras().getString("anime_mail");
        int nb_phone = getIntent().getExtras().getInt("anime_phone") ;
        String address = getIntent().getExtras().getString("anime_address") ;
        String image_url = getIntent().getExtras().getString("anime_img") ;

        // ini views

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_name = findViewById(R.id.aa_anime_name);
        TextView tv_region = findViewById(R.id.aa_region);
        TextView tv_mail = findViewById(R.id.aa_mail) ;
        TextView tv_description = findViewById(R.id.aa_description);
        TextView tv_address  = findViewById(R.id.aa_address) ;
        ImageView img = findViewById(R.id.aa_thumbnail);

        // setting values to each view

        tv_name.setText(name);
        tv_mail.setText(mail);
        tv_description.setText(description);
        tv_address.setText(address);
        tv_region.setText(region);

        collapsingToolbarLayout.setTitle(name);


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);





    }
}
