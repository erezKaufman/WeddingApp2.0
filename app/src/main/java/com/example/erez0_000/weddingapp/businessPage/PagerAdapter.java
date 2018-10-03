package com.example.erez0_000.weddingapp.businessPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InfoFragment infoFragment = InfoFragment.newInstance();
//                infoFragment.setValues(name,description,region,mail,nb_phone,address,image_url);
                return infoFragment;
            case 1:
                CalendarFragmentForBusiness calendarFragment = CalendarFragmentForBusiness.newInstance();
//                calendarFragment.setListner(this,occupieddates);
                return calendarFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}