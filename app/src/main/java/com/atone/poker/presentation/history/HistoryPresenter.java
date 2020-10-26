package com.atone.poker.presentation.history;

import androidx.lifecycle.LifecycleOwner;

import com.atone.poker.data.HistoryRepository;
import com.atone.poker.data.HistorySource;
import com.atone.poker.domain.Result;
import com.atone.poker.framework.RealmHistorySourceImp;
import com.atone.poker.interactor.GetHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryPresenter implements History.Presenter {

    public HistoryPresenter(History.View view) {
        this.view = view;
    }

    private History.View view;
    private GetHistory getHistory = new GetHistory(new HistoryRepository(new RealmHistorySourceImp()));
    private ArrayList<Result> history = new ArrayList<>();

    public ArrayList<Result> getAdapterData() {
        history = new ArrayList<>();
        return history;
    }

    @Override
    public void getHistoryFromDb(LifecycleOwner owner) {
        getHistory.get(owner, new HistorySource.Callback() {
            @Override
            public void onDatabaseChanged(List<Result> results) {
                history.clear();
                history.addAll(results);
                view.refreshRV();
            }
        });
    }

    @Override
    public void refreshData(LifecycleOwner owner) {
        history.clear();
        getHistoryFromDb(owner);
    }
}
