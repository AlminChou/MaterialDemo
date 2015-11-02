package com.almin.materiademo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Almin on 2015/11/1.
 */
public class MyAdapter extends FragmentStatePagerAdapter {
    private static final int PAGE_COUNT = 4;
    private int mCount;

    public MyAdapter(FragmentManager fm,int count) {
        super(fm);
        mCount = count;
    }

    @Override
    public Fragment getItem(int position) {
        return new RecyclerViewDemoFragment();
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.format("Tab %d",position);
    }
}
