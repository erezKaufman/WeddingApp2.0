package com.example.erez0_000.weddingapp.businessPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;

import java.util.ArrayList;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_PAGES_IN_TABS = 2;

    private ArrayList<Pair<Fragment, String>> tabbs = new ArrayList<>(2);

    /**
     * @param fm fragment manager type, manages the fragments
     */
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        tabbs.add(Pair.create(fragment, title));
    }


    @Override
    public int getCount() {
        return NUMBER_OF_PAGES_IN_TABS;
    }

    @Override
    public Fragment getItem(int position) {
        return tabbs.get(position).first;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabbs.get(position).second;
    }
}
