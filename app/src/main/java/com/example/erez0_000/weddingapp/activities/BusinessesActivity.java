package com.example.erez0_000.weddingapp.activities;
//package com.demotxt.myapp.parseJSON.activities;

import android.support.design.widget.TabLayout;
import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.erez0_000.weddingapp.R;
import com.example.erez0_000.weddingapp.businessPage.CalendarFragmentForBusiness;
import com.example.erez0_000.weddingapp.businessPage.InfoFragment;
import com.example.erez0_000.weddingapp.businessPage.SetAppointmentFragment;
import com.example.erez0_000.weddingapp.businessPage.TabPagerAdapter;
import com.example.erez0_000.weddingapp.db_classes.Businesses;
import com.roomorama.caldroid.CaldroidFragment;

public class BusinessesActivity extends AppCompatActivity
        implements CalendarFragmentForBusiness.ClendarDialogFragmentListner {
    private CaldroidFragment dialogCaldroidFragment;
    private TabLayout tabLayout;
    private ViewPager feedViewPager;
    private long[] occupieddates;
    private String[] phoneNumbers;
    private String mail;
    private Businesses curBusiness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        final Bundle state = savedInstanceState;

        getSupportActionBar().hide();

        // Receive data
        curBusiness = (Businesses) getIntent().getSerializableExtra("curBusiness");
        String name = curBusiness.getName();
        String description = curBusiness.getDescription();
        String region = curBusiness.getRegion();
        mail = curBusiness.getMail();
        phoneNumbers = curBusiness.getPhone();
        String address = curBusiness.getAddress();
        String image_url = curBusiness.getImage();
        occupieddates = curBusiness.getOccupiedDates();
        // ini views


        feedViewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        createTabs(feedViewPager,name,description,region,mail,phoneNumbers,address,image_url);






    }

    private void createTabs(ViewPager feedViewPager, String name, String description, String region, String mail, String[] nb_phone, String address, String image_url) {
        TabPagerAdapter a = new TabPagerAdapter(getSupportFragmentManager());

        InfoFragment infoFragment = InfoFragment.newInstance();
        infoFragment.setValues(name,description,region,mail,nb_phone,address,image_url);
        CalendarFragmentForBusiness calendarFragment = CalendarFragmentForBusiness.newInstance();
        calendarFragment.setListner(this,occupieddates);

        a.addFragment(infoFragment,"");
        a.addFragment(calendarFragment,"");

        feedViewPager.setAdapter(a);

        tabLayout.setupWithViewPager(feedViewPager);

        //add the icons to the tabs
        tabLayout.getTabAt(0).setIcon(R.drawable.info_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.calendar_icon);
    }

    @Override
    public void onDateClick(String dateString,boolean isWinter) {
        FragmentManager ft = getFragmentManager();
        SetAppointmentFragment appointmentFrag = SetAppointmentFragment.newInstance();
        appointmentFrag.setBusinessContact(curBusiness,dateString,isWinter);
        appointmentFrag.show(ft ,null);
    }
}
