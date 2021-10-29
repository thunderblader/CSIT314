package com.example.csit314.prescribe.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.csit314.R;
import com.example.csit314.prescribe.Prescription;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private ArrayList<Prescription> alist;
    public SectionsPagerAdapter(Context context, FragmentManager fm, ArrayList<Prescription> alist) {
        super(fm);
        mContext = context;
        this.alist = new ArrayList<Prescription>();
        this.alist = alist;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1);
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = new PastPrescriptionFragment();
                break;
            case 1:
                fragment = new NewPrescriptionFragment();
                break;

        }
        if (fragment != null)
        {
            Bundle bundle = new Bundle();
            fragment.setArguments(bundle);
            bundle.putParcelableArrayList("ArrayList",(ArrayList<? extends Parcelable>) alist);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}