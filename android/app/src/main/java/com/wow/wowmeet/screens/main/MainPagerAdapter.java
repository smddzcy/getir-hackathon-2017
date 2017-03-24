package com.wow.wowmeet.screens.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.wow.wowmeet.screens.main.list.ListFragment;

/**
 * Created by mahmutkaraca on 3/24/17.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private ListFragment listFragment;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            if(listFragment == null) {
                listFragment = ListFragment.newInstance();
            }
            return listFragment;
        }
        return listFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
