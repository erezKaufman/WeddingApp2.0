package com.example.erez0_000.weddingapp.Login_pages.HorizontalRecyclerBusinesses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Businesses;

public class BusinessMinimalactivity extends AppCompatActivity {
    private Businesses curBusiness;
    private TextView tv_name, tv_region, tv_address, tv_description;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_minimalactivity);
        final Bundle state = savedInstanceState;

        getSupportActionBar().hide();

        // Receive data
        curBusiness = (Businesses) getIntent().getSerializableExtra("curBusiness");
        String name = curBusiness.getName();
        String description = curBusiness.getDescription();
        String region = curBusiness.getRegion();
        String address = curBusiness.getAddress();
        String image_url = curBusiness.getImage();

        tv_name = findViewById(R.id.aa_anime_name);
        tv_region = findViewById(R.id.aa_region);
        tv_description = findViewById(R.id.business_info);
        tv_address = findViewById(R.id.aa_address);
        img = findViewById(R.id.aa_thumbnail);


        tv_name.setText(name);
        tv_address.setText(address);
        tv_region.setText(region);
        tv_description.setText(description);
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);


    }

}
