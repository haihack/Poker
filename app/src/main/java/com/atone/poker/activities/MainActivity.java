package com.atone.poker.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.atone.poker.R;
import com.atone.poker.adapters.MainTabsAdapter;
import com.atone.poker.fragments.HandCheckingFragment;
import com.atone.poker.fragments.HistoryFragment;
import com.atone.poker.models.TabEntity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.atone.poker.utilities.CommonUtils.hideKeyboard;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);

            setSupportActionBar(toolbar);

            initData();

            ArrayList<CustomTabEntity> tabData = new ArrayList<>(2);
            for (int i = 0; i < 2; i++) {
                tabData.add(new TabEntity(tabNames[i], tabSelectedIcons[i], tabUnselectedIcons[i]));
            }

            List<Fragment> fragments = new ArrayList<>();
            fragments.add(HandCheckingFragment.newInstance());
            fragments.add(HistoryFragment.newInstance());
            tabLayout.setTabData(tabData);

            MainTabsAdapter tabAdapter = new MainTabsAdapter(getSupportFragmentManager(), fragments, tabNames);
            vpager.setAdapter(tabAdapter);

            tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelect(int position) {
                    vpager.setCurrentItem(position);
                }

                @Override
                public void onTabReselect(int position) {

                }
            });

            vpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    tabLayout.setCurrentTab(position);
                    hideKeyboard(MainActivity.this);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            vpager.setCurrentItem(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        tabNames = new String[]{"Poker", "履歴"};
        tabSelectedIcons = new int[]{
                R.drawable.bg_tab_circle_selected,
                R.drawable.bg_tab_circle_selected,};
        tabUnselectedIcons = new int[]{
                R.drawable.bg_tab_circle_unselected,
                R.drawable.bg_tab_circle_unselected};
    }
}