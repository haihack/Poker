package com.atone.poker.presentation.history;

import androidx.lifecycle.LifecycleOwner;

import com.atone.poker.domain.Error;
import com.atone.poker.domain.Result;

import java.util.ArrayList;

public interface History {
    interface Presenter {
        void getHistoryFromDb(LifecycleOwner owner);

        void refreshData(LifecycleOwner owner);
    }

    interface View {
        void updateContentViews();

        void refreshRV();
    }
}
