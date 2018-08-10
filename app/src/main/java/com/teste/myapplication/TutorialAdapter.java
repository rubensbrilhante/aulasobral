package com.teste.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TutorialAdapter extends FragmentPagerAdapter {

    public static final int SIZE = 3;

    public TutorialAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new FirstTutorialPageFragment();
            case 1:
                return new SecondTutorialPageFragment();
            case 2:
                return new ThirdTutorialPageFragment();
            default:
                throw new RuntimeException("expecting only 3 pages");
        }
    }

    @Override
    public int getCount() {
        return SIZE;
    }
}
