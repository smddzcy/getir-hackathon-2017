package com.wow.wowmeet.screens.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wow.wowmeet.partials.list.ListFragment;
import com.wow.wowmeet.screens.main.map.MapFragment;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    ListFragment listFragment;
    MapFragment mapFragment;

    public MainPagerAdapter(FragmentManager fm, ListFragment lf, MapFragment mf) {
        super(fm);
        listFragment = lf;
        mapFragment = mf;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            if(listFragment == null) {
                listFragment = ListFragment.newInstance();
            }
            return listFragment;
        } else if(position == 1) {
            if(mapFragment == null) {
                mapFragment = MapFragment.newInstance();
            }
            return mapFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position == 0) ? "List" : "Map";
    }

    @Override
    public int getCount() {
        return 2;
    }
}
