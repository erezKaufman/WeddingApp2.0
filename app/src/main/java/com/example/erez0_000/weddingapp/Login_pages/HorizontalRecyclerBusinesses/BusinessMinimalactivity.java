package com.example.erez0_000.weddingapp.Login_pages.HorizontalRecyclerBusinesses;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.db_classes.Businesses;

public class BusinessMinimalactivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager feedViewPager;
    private String[] phoneNumbers;
    private String mail;
    private Businesses curBusiness;
    private TextView tv_name, tv_region, tv_mail, tv_address,tv_description;
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
//        tv_mail = findViewById(R.id.aa_mail);
        tv_description = findViewById(R.id.business_info);
        tv_address = findViewById(R.id.aa_address);
        img = findViewById(R.id.aa_thumbnail);


        tv_name.setText(name);
//        tv_mail.setText(mail);
        tv_address.setText(address);
        tv_region.setText(region);
        tv_description.setText(description);
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);        // ini views

//        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
//        collapsingToolbarLayout.setTitleEnabled(true);

//        feedViewPager = (ViewPager) findViewById(R.id.view_pager);
//        tabLayout = (TabLayout) findViewById(R.id.tabs);

//        createTabs(feedViewPager,name,description,region,mail,phoneNumbers,address,image_url);


        // START - WORKING ON CALENDAR
//        findViewById(R.id.lookAtDates).setOnClickListener(new View.OnClickListener() {
//            final CaldroidListener listener = new CaldroidListener() {
//
//                @Override
//                public void onSelectDate(Date date, View view) {
//                    Toast.makeText(getApplicationContext(), formatter.format(date),
//                            Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onChangeMonth(int month, int year) {
//                    String text = "month: " + month + " year: " + year;
//                    Toast.makeText(getApplicationContext(), text,
//                            Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onLongClickDate(Date date, View view) {
//                    Toast.makeText(getApplicationContext(),
//                            "Long click " + formatter.format(date),
//                            Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCaldroidViewCreated() {
//                }
//
//            };
//
//            @Override
//            public void onClick(View v) {
//                dialogCaldroidFragment = new CaldroidFragment();
//                dialogCaldroidFragment.setCaldroidListener(listener);
//
//                // If activity is recovered from rotation
//                final String dialogTag = "CALDROID_DIALOG_FRAGMENT";
//                if (state != null) {
//                    dialogCaldroidFragment.restoreDialogStatesFromKey(
//                            getSupportFragmentManager(), state,
//                            "DIALOG_CALDROID_SAVED_STATE", dialogTag);
//                    Bundle args = dialogCaldroidFragment.getArguments();
//                    if (args == null) {
//                        args = new Bundle();
//                        dialogCaldroidFragment.setArguments(args);
//                    }
//                } else {
//                    // Setup arguments
//                    Bundle bundle = new Bundle();
//                    // Setup dialogTitle
//                    dialogCaldroidFragment.setArguments(bundle);
//                }
//
//                dialogCaldroidFragment.show(getSupportFragmentManager(),
//                        dialogTag);
//            }
//        });
        // END- WORKING ON CALENDAR


        // setting values to each view


//        collapsingToolbarLayout.setTitle(name);


    }

//    private void createTabs(ViewPager feedViewPager, String name, String description, String region, String mail, String[] nb_phone, String address, String image_url) {
//        TabPagerAdapter a = new TabPagerAdapter(getSupportFragmentManager());
//
////        a.addFragment(TodoFragment.newInstance("1","2"),"");
//        InfoFragment infoFragment = InfoFragment.newInstance();
//        infoFragment.setValues(name,description,region,mail,nb_phone,address,image_url);
//        CalendarFragmentForBusiness calendarFragment = CalendarFragmentForBusiness.newInstance();
//        calendarFragment.setListner(this,occupieddates);
//
//        a.addFragment(infoFragment,"");
//        a.addFragment(calendarFragment,"");
//
//        feedViewPager.setAdapter(a);
//
//        tabLayout.setupWithViewPager(feedViewPager);
//
//        //add the icons to the tabs
////        tabLayout.getTabAt(0).setIcon(R.drawable.todo_icon);
//        tabLayout.getTabAt(0).setIcon(R.drawable.info_icon);
//        tabLayout.getTabAt(1).setIcon(R.drawable.calendar_icon);
//    }

//    @Override
//    public void onDateClick(String dateString,boolean isWinter) {
//        FragmentManager ft = getSupportFragmentManager();
//        SetAppointmentFragment appointmentFrag = SetAppointmentFragment.newInstance();
//        appointmentFrag.setBusinessContact(curBusiness,dateString,isWinter);
//        appointmentFrag.show(ft ,null);
//    }
}
