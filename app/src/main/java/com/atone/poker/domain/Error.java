package com.atone.poker.domain;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Error   {

    @SerializedName("card")
    private String hand;
    @SerializedName("msg")
    private String message;


}
