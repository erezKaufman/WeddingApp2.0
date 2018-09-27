package com.example.erez0_000.weddingapp.activities;
//package com.demotxt.myapp.parseJSON.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.todos_section.AddSubTaskFragment;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BusinessesActivity extends AppCompatActivity
        implements CalendarFragmentForrBusiness.ClendarDialogFragmentListner {
    private CaldroidFragment dialogCaldroidFragment;
    private TabLayout tabLayout;
    private ViewPager feedViewPager;
    long[] occupieddates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        final Bundle state = savedInstanceState;
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        getSupportActionBar().hide();

        // Receive data

        String name = getIntent().getExtras().getString("anime_name");
        String description = getIntent().getExtras().getString("anime_description");
        String region = getIntent().getExtras().getString("anime_region");
        String mail = getIntent().getExtras().getString("anime_mail");
        int nb_phone = getIntent().getExtras().getInt("anime_phone");
        String address = getIntent().getExtras().getString("anime_address");
        String image_url = getIntent().getExtras().getString("anime_img");
        occupieddates = getIntent().getExtras().getLongArray("OccupiedDates");
        // ini views

//        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
//        collapsingToolbarLayout.setTitleEnabled(true);

        feedViewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        createTabs(feedViewPager,name,description,region,mail,nb_phone,address,image_url);


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

    private void createTabs(ViewPager feedViewPager, String name, String description, String region, String mail, int nb_phone, String address, String image_url) {
        TabPagerAdapter a = new TabPagerAdapter(getSupportFragmentManager());

//        a.addFragment(TodoFragment.newInstance("1","2"),"");
        InfoFragment infoFragment = InfoFragment.newInstance();
        infoFragment.setValues(name,description,region,mail,nb_phone,address,image_url);
        CalendarFragmentForrBusiness calendarFragment = CalendarFragmentForrBusiness.newInstance();
        calendarFragment.setListner(this,occupieddates);

        a.addFragment(infoFragment,"");
        a.addFragment(calendarFragment,"");

        feedViewPager.setAdapter(a);

        tabLayout.setupWithViewPager(feedViewPager);

        //add the icons to the tabs
//        tabLayout.getTabAt(0).setIcon(R.drawable.todo_icon);
        tabLayout.getTabAt(0).setIcon(R.drawable.info_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.calendar_icon);
    }

    @Override
    public void onDateClick(String message) {

    }
}
