package com.atone.poker.presentation.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MainTabsAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    String[] mDatas;

    public MainTabsAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] mDatas) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mDatas = mDatas;

    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDatas[position];
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}