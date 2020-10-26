package com.atone.poker.interactor;

import androidx.lifecycle.LifecycleOwner;

import com.atone.poker.data.HistoryRepository;
import com.atone.poker.data.HistorySource;

public class GetHistory {
    private HistoryRepository rep;

    public GetHistory(HistoryRepository rep) {
        this.rep = rep;
    }

    public void get(LifecycleOwner owner, HistorySource.Callback callback){
         rep.readHistory(owner, callback);
    }

}
