package com.atone.poker.interactor;

import com.atone.poker.data.HistoryRepository;
import com.atone.poker.domain.Result;

import java.util.ArrayList;

public class SaveHistory {
    private HistoryRepository rep;

    public SaveHistory(HistoryRepository rep) {
        this.rep = rep;
    }

    public void save(ArrayList<Result> results){
         rep.insertHistory(results);
    }
}
