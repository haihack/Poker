package com.atone.poker.interactor;

import com.atone.poker.data.HandRepository;
import com.atone.poker.domain.CardCheckingRequest;
import com.atone.poker.domain.Result;
import com.atone.poker.framework.ServerHandSourceImp;

import java.util.ArrayList;

public class CheckHand {
    private HandRepository rep;

    public CheckHand(HandRepository rep) {
        this.rep = rep;
    }

    public void check(CardCheckingRequest request, ServerHandSourceImp.Callback callback) {
        rep.checkHand(request, callback);
    }
}
