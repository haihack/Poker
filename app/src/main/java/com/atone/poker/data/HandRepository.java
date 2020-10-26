package com.atone.poker.data;

import com.atone.poker.domain.CardCheckingRequest;
import com.atone.poker.framework.ServerHandSourceImp;

public class HandRepository {
    private HandSource source;

    public HandRepository(HandSource source) {
        this.source = source;
    }

    public void checkHand(CardCheckingRequest request, ServerHandSourceImp.Callback callback) {
        source.check(request, callback);
    }
}
