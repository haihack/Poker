package com.atone.poker.presentation.tab1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.atone.poker.R;
import com.atone.poker.presentation.base.fragments.BackStackFragment;
import com.atone.poker.presentation.handchecking.HandCheckingFragment;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1HostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1HostFragment extends BackStackFragment {

    public static Tab1HostFragment newInstance() {
        Tab1HostFragment fragment = new Tab1HostFragment();
        return fragment;
    }

    private Fragment contentFrgm;

    public Fragment getContentFrgm() {
        return contentFrgm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab1_host, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateContentViews();
    }

    public void addFragment(Fragment fragment, boolean addToBackstack) {
        if (addToBackstack) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.root_host_tab1, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.root_host_tab1, fragment)
                    .commit();
        }
    }

    public void updateContentViews() {
        if (contentFrgm == null) {
            contentFrgm = HandCheckingFragment.newInstance();
            addFragment(contentFrgm, false);
        }
    }

}