package postpc.project.erez0_000.weddingapp.activities;
//package com.demotxt.myapp.parseJSON.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import postpc.project.erez0_000.weddingapp.businessPage.CalendarFragmentForBusiness;
import postpc.project.erez0_000.weddingapp.businessPage.InfoFragment;
import postpc.project.erez0_000.weddingapp.businessPage.SetAppointmentFragment;
import postpc.project.erez0_000.weddingapp.businessPage.TabPagerAdapter;
import postpc.project.erez0_000.weddingapp.db_classes.Businesses;
import postpc.project.erez0_000.weddingapp.db_classes.User;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class BusinessesActivity extends AppCompatActivity
        implements CalendarFragmentForBusiness.ClendarDialogFragmentListner {
    private TabLayout tabLayout;
    private ViewPager feedViewPager;
    private long[] occupieddates;
    private String[] phoneNumbers;
    private String mail;
    private Businesses curBusiness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(postpc.project.erez0_000.weddingapp.R.layout.activity_business);
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


        feedViewPager = (ViewPager) findViewById(postpc.project.erez0_000.weddingapp.R.id.view_pager);
        tabLayout = (TabLayout) findViewById(postpc.project.erez0_000.weddingapp.R.id.tabs);

        createTabs(feedViewPager,name,description,region,mail,phoneNumbers,address,image_url);

        showManuel();




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
        tabLayout.getTabAt(0).setIcon(postpc.project.erez0_000.weddingapp.R.drawable.info_icon);
        tabLayout.getTabAt(1).setIcon(postpc.project.erez0_000.weddingapp.R.drawable.calendar_icon);
        View mainTab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(0);
    }

    @Override
    public void onDateClick(String dateString,boolean isWinter) {
        FragmentManager ft = getFragmentManager();
        SetAppointmentFragment appointmentFrag = SetAppointmentFragment.newInstance();
        appointmentFrag.setBusinessContact(curBusiness,dateString,isWinter);
        appointmentFrag.show(ft ,null);
    }

    /**
     * the method opens the guide animation in the user's first run in the page
     */
    private void showManuel() {
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, User.thisUser+"Business");


        sequence.setConfig(config);

        sequence.addSequenceItem(getImageViewFromTabLayout(0),
                "זהו כפתור גישה למידע על העסק", "קיבלתי");
        sequence.addSequenceItem(getImageViewFromTabLayout(1),
                "בכפתור זה תוכל לגשת ללוח השנה של בעל העסק ולראות תאריכים זמינים, ליצור קשר, ולקבוע פגישה", "קיבלתי");

        sequence.start();

    }


    private View getImageViewFromTabLayout(int position) {

        return ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(position);
    }
}
