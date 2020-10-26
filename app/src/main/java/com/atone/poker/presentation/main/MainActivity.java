package com.atone.poker.presentation.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.atone.poker.R;
import com.atone.poker.domain.Result;
import com.atone.poker.domain.TabEntity;
import com.atone.poker.presentation.base.fragments.BackStackFragment;
import com.atone.poker.presentation.history.HistoryFragment;
import com.atone.poker.presentation.resultshowing.ResultFragment;
import com.atone.poker.presentation.tab1.Tab1HostFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.atone.poker.presentation.base.common.Constants.MAIN_TAB_NAME;
import static com.atone.poker.presentation.base.common.Constants.SCREEN_NAME;
import static com.atone.poker.presentation.base.utilities.CommonUtils.hideKeyboard;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    CommonTabLayout tabLayout;
    @BindView(R.id.vpager)
    ViewPager vpager;
    @BindView(R.id.toolbar)
    com.google.android.material.appbar.MaterialToolbar toolbar;

    private String[] tabNames;
    private int[] tabSelectedIcons;
    private int[] tabUnselectedIcons;
    private MainTabsAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);

            setSupportActionBar(toolbar);
            initData();

            setContentViews();

            setTabLayoutListener();
            setViewPagerListener();

            vpager.setCurrentItem(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setContentViews() {
        ArrayList<CustomTabEntity> tabData = new ArrayList<>(2);
        for (int i = 0; i < 2; i++) {
            tabData.add(new TabEntity(tabNames[i], tabSelectedIcons[i], tabUnselectedIcons[i]));
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(Tab1HostFragment.newInstance());
        fragments.add(HistoryFragment.newInstance());
        tabLayout.setTabData(tabData);

        tabAdapter = new MainTabsAdapter(getSupportFragmentManager(), fragments, tabNames);
        vpager.setAdapter(tabAdapter);
    }

    private void setViewPagerListener() {
        vpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
                hideKeyboard(MainActivity.this);

                switchToolbarView(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setTabLayoutListener() {
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    public void openResultScreen(ArrayList<Result> result) {
        ResultFragment fragment = ResultFragment.newInstance(result);
        ((Tab1HostFragment) tabAdapter.getItem(0)).addFragment(fragment, true);
    }

    private void initData() {
        tabNames = MAIN_TAB_NAME;
        tabSelectedIcons = new int[]{
                R.drawable.bg_tab_circle_selected,
                R.drawable.bg_tab_circle_selected,};
        tabUnselectedIcons = new int[]{
                R.drawable.bg_tab_circle_unselected,
                R.drawable.bg_tab_circle_unselected};
    }

    public void switchToolbarView(int pos) {
        if (pos == 0) {
            Fragment contentFrag = ((Tab1HostFragment) tabAdapter.getItem(pos)).getContentFrgm();
            if (BackStackFragment.isBackStackFragmentEmpty(getSupportFragmentManager())) {
                getSupportActionBar().setTitle(MAIN_TAB_NAME[0]);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
            } else {
                getSupportActionBar().setTitle(SCREEN_NAME[2]);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }

        } else {
            getSupportActionBar().setTitle(MAIN_TAB_NAME[1]);
//            //show back button on toolbar
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

    }

    @Override
    public void onBackPressed() {
        if (!BackStackFragment.handleBackPressed(getSupportFragmentManager())) {
            super.onBackPressed();
        } else {
            getSupportActionBar().setTitle(MAIN_TAB_NAME[0]);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}