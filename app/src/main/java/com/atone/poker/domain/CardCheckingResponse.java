package com.atone.poker.domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;

@Data
public class CardCheckingResponse {

    @SerializedName("result")
    private ArrayList<Result> result = null;
    @SerializedName("error")
    private ArrayList<Error> error = null;
}
