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
import com.atone.poker.widgets.CustomFragmentHeader;
import com.atone.poker.widgets.SimpleDividerItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

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
    private long from = 1;
    public static final int LIMIT = 10;
    private Realm realm;
    private HistoryAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable
            Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        try {

            refreshLayout.setRefreshHeader(new CustomFragmentHeader(getActivity()));
            refreshLayout.setOnRefreshListener(refreshLayout -> {
                refreshLayout.finishRefresh(1000/*,false*/);
                onRefreshing();
            });
            refreshLayout.setOnLoadMoreListener(refreshLayout -> getData());


            realm = Realm.getDefaultInstance();
//        RealmResults<Result> realmResult = realm.where(Result.class).findAll();
//        List<Result> history = realm.copyFromRealm(realmResult);


            rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

            adapter = new HistoryAdapter(history);
            rvHistory.setAdapter(adapter);

            //firsttime

            onRefreshing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onRefreshing() {
        history.clear();
        getData();
    }

    private LiveRealmResults<Result> _10firstRecords;
    private LiveRealmResults<Result> nextRecord;

    public void getData() {
//        if (history.size() < LIMIT) {
            _10firstRecords = new LiveRealmResults<>(
                    realm.where(Result.class)
                            .sort("id", Sort.DESCENDING)
//                            .limit(LIMIT)
                            .findAllAsync());
            _10firstRecords.observe(this, results -> {
                history.clear();
                history.addAll(results);
                adapter.notifyDataSetChanged();
            });
//        } else {
            //https://stackoverflow.com/questions/54652781/how-to-select-a-range-of-items-from-realm-database
//            from = history.get(history.size() - 1).getId() - 1;
//            long to = from - LIMIT;
//            nextRecord = new LiveRealmResults<>(
//                    realm.where(Result.class)
//                            .sort("id", Sort.DESCENDING)
//                            .between("id", to, from)
//                            .lessThan("id", 1000)
//                            .findAllAsync());
//            _10firstRecords.observe(this, results -> {
//                history.addAll(results);
//                adapter.notifyDataSetChanged();
//
//            });
////        }
        refreshLayout.finishLoadMore(1000);
    }
}