package com.atone.poker.presentation.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atone.poker.R;
import com.atone.poker.data.HistoryRepository;
import com.atone.poker.data.HistorySource;
import com.atone.poker.framework.RealmHistorySourceImp;
import com.atone.poker.interactor.GetHistory;
import com.atone.poker.presentation.base.widgets.CustomFragmentHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends Fragment implements History.View {

    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private HistoryPresenter presenter = new HistoryPresenter(this);

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

    private HistoryAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable
            Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateContentViews();
    }

    @Override
    public void updateContentViews() {
        try {
            refreshLayout.setRefreshHeader(new CustomFragmentHeader(getActivity()));
            refreshLayout.setOnRefreshListener(refreshLayout -> {
                refreshLayout.finishRefresh(1000/*,false*/);
                presenter.refreshData(HistoryFragment.this);
            });
            refreshLayout.setOnLoadMoreListener(refreshLayout -> {
                refreshLayout.finishLoadMore(1000);
                presenter.getHistoryFromDb(this);
            });

            rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

            adapter = new HistoryAdapter(presenter.getAdapterData());
            rvHistory.setAdapter(adapter);
            presenter.getHistoryFromDb(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshRV() {
        adapter.notifyDataSetChanged();
    }
}