package com.atone.poker.presentation.handchecking;

import com.atone.poker.data.HandRepository;
import com.atone.poker.data.HistoryRepository;
import com.atone.poker.domain.CardCheckingRequest;
import com.atone.poker.domain.CardCheckingResponse;
import com.atone.poker.domain.Error;
import com.atone.poker.domain.Hand;
import com.atone.poker.domain.Result;
import com.atone.poker.framework.RealmHistorySourceImp;
import com.atone.poker.framework.ServerHandSourceImp;
import com.atone.poker.presentation.base.utilities.AppLogger;
import com.atone.poker.interactor.CheckHand;
import com.atone.poker.interactor.SaveHistory;

import java.util.ArrayList;

import retrofit2.Response;

public class HandCheckingPresenter implements HandChecking.Presenter {

    public HandCheckingPresenter(HandChecking.View view) {
        this.view = view;
    }

    private ArrayList<Hand> handList;
    private HandChecking.View view;
    private SaveHistory saveHistory = new SaveHistory(new HistoryRepository(new RealmHistorySourceImp()));
    private CheckHand checkHand = new CheckHand(new HandRepository(new ServerHandSourceImp()));

    @Override
    public void addHand() {
        handList.add(new Hand());
        view.displayNewItem(handList.size());
    }

    public ArrayList<Hand> getAdapterData() {
        handList = new ArrayList<>();
        handList.add(new Hand());
        return handList;
    }

    @Override
    public void requestToCheckHand() {
        CardCheckingRequest card = new CardCheckingRequest();
        for (int i = 0; i < handList.size(); i++) {
            card.getCards().add(handList.get(i).getFullHand());
        }

        checkHand.check(card, new ServerHandSourceImp.Callback() {
            @Override
            public void onResponse(Response<CardCheckingResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getError() == null) {
                            ArrayList<Result> result = response.body().getResult();
                            handleResult(result);
                        } else {
                            ArrayList<Error> error = response.body().getError();
                            handleError(error);
                        }
                        view.hideLoading();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                try {
                    AppLogger.e(t.getLocalizedMessage());
                    view.hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void handleResult(ArrayList<Result> result) {
        try {
            view.openResultScreen(result);
            saveHistory.save(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleError(ArrayList<Error> error) {
        try {
            for (int i = 0; i < error.size(); i++) {
                for (int j = 0; j < handList.size(); j++) {
                    if (error.get(i).getHand().equalsIgnoreCase(handList.get(j).getFullHand())) {
                        String errorCardMessage = error.get(i).getMessage();
                        if (errorCardMessage.contains("重複")) {
                            for (int k = 0; k < 5; k++) {
                                for (int l = k + 1; l < 5; l++) {
                                    if (handList.get(j).getCards()[k].getName()
                                            .equals(handList.get(j).getCards()[l].getName())) {
                                        handList.get(j).getCards()[k].setError(errorCardMessage);
                                        handList.get(j).getCards()[l].setError(errorCardMessage);
                                    }
                                }
                            }
                        } else {
                            int errorCardIndex = Integer.parseInt(errorCardMessage.substring(0, 1)) - 1;
                            handList.get(j).getCards()[errorCardIndex].setError(errorCardMessage);
                        }
                        view.refreshAdapterAtPosition(j);
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
