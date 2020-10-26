package com.atone.poker.framework;

import com.atone.poker.data.HandSource;
import com.atone.poker.domain.CardCheckingRequest;
import com.atone.poker.domain.CardCheckingResponse;
import com.atone.poker.framework.network.RestClient;

import retrofit2.Call;
import retrofit2.Response;

public class ServerHandSourceImp implements HandSource {

    public interface Callback {
        void onResponse(Response<CardCheckingResponse> results);

        void onFailure(Throwable t);
    }

    @Override
    public void check(CardCheckingRequest card, Callback callback) {
        RestClient.getApiService().requestToCheckCards(card).enqueue(new retrofit2.Callback<CardCheckingResponse>() {
            @Override
            public void onResponse(Call<CardCheckingResponse> call, Response<CardCheckingResponse> response) {
                if (callback != null) {
                    callback.onResponse(response);
                }
            }

            @Override
            public void onFailure(Call<CardCheckingResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }
}
