package com.atone.poker.data;

import com.atone.poker.domain.CardCheckingRequest;
import com.atone.poker.framework.ServerHandSourceImp;

public interface HandSource {
    void check(CardCheckingRequest card, ServerHandSourceImp.Callback callback);
}
