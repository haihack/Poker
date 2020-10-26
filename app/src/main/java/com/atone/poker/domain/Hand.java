package com.atone.poker.domain;


import lombok.Data;

@Data
public class Hand {

    private Card[] cards = new Card[5];

    public Hand() {
        cards[0] = new Card();
        cards[1] = new Card();
        cards[2] = new Card();
        cards[3] = new Card();
        cards[4] = new Card();
    }

    public String getFullHand() {
        String ret = "";
        for (int i = 0; i < 4; i++) {
            ret += cards[i].getName() + " ";
        }
        ret += cards[4].getName();
        return ret;
    }
}
