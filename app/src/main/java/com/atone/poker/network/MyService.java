package com.atone.poker.network;

import com.atone.poker.models.CardCheckingRequest;
import com.atone.poker.models.CardCheckingResponse;

import org.json.JSONStringer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MyService {

    @Headers({
            "Content-Type: application/json"
    })
    @POST("v1/cards/check")
    Call<CardCheckingResponse> requestToCheckCards(@Body JSONStringer data);

    @POST("v1/cards/check")
    Call<CardCheckingResponse> requestToCheckCards(@Body CardCheckingRequest data);
}