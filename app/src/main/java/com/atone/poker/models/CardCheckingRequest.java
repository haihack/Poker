package com.atone.poker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CardCheckingRequest {

    @SerializedName("cards")
    private List<String> cards = new ArrayList<>();
}
