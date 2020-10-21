package com.atone.poker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atone.poker.R;
import com.atone.poker.adapters.HistoryAdapter;
import com.atone.poker.models.LiveRealmResults;
import com.atone.poker.models.Result;
import com.atone.poker.widgets.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class HistoryFragment extends Fragment {

    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hand_history, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    private List<Result> history = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable
            Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            Realm realm = Realm.getDefaultInstance();
//        RealmResults<Result> realmResult = realm.where(Result.class).findAll();
//        List<Result> history = realm.copyFromRealm(realmResult);

            LiveRealmResults<Result> resultLiveRealmResults = new LiveRealmResults<>(
                    realm.where(Result.class)
                            .sort("id", Sort.DESCENDING)
                            .findAllAsync());

            rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

            HistoryAdapter adapter = new HistoryAdapter(history);
            rvHistory.setAdapter(adapter);

            resultLiveRealmResults.observe(this, results -> {
                history.clear();
                history.addAll(results);
                adapter.notifyDataSetChanged();

            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}