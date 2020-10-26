package com.atone.poker.data;

import androidx.lifecycle.LifecycleOwner;

import com.atone.poker.domain.Result;

import java.util.ArrayList;
import java.util.List;

public interface HistorySource {
    void insert(ArrayList<Result> results);

    void read(LifecycleOwner owner, Callback callback);

    void delete();

    interface Callback {
        void onDatabaseChanged(List<Result> results);
    }
}


