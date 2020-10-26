package com.atone.poker.presentation.handchecking;

import com.atone.poker.domain.Error;
import com.atone.poker.domain.Result;

import java.util.ArrayList;

public interface HandChecking {
    interface Presenter {
        void handleResult(ArrayList<Result> result);

        void handleError(ArrayList<Error> error);

        void addHand();

        void requestToCheckHand();

    }

    interface View {
        void showLoading();

        void hideLoading();

        void updateContentViews();

        void displayNewItem(int size);

        void openResultScreen(ArrayList<Result> result);

        void refreshAdapterAtPosition(int pos);

    }
}
