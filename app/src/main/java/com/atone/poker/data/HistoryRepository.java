package com.atone.poker.data;

import androidx.lifecycle.LifecycleOwner;

import com.atone.poker.domain.Result;

import java.util.ArrayList;

public class HistoryRepository {
    private HistorySource source;

    public HistoryRepository(HistorySource source) {
        this.source = source;
    }

    public void insertHistory(ArrayList<Result> results) {
        source.insert(results);
    }

    public void readHistory(LifecycleOwner owner, HistorySource.Callback callback) {
         source.read(owner, callback);
    }
}
