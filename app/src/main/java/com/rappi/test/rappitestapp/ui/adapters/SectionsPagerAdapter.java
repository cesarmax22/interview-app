package com.rappi.test.rappitestapp.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rappi.test.rappitestapp.model.beans.MovieCategory;
import com.rappi.test.rappitestapp.ui.fragments.MoviePlaceholderFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    private final MovieCategory[] tabs = MovieCategory.values();

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return MoviePlaceholderFragment.newInstance(tabs[position]);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(tabs[position].getResourceId());
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}