package ru.mozevil.poker.model.card;

import java.util.List;

public class Range {
    //todo


    public void exclude(Card card) {
        //todo

    }

    public void exclude(Hand hand) {
        exclude(hand.getHighCard());
        exclude(hand.getLowCard());
    }

    public void exclude(Combination combo) {
        for (Card card : combo.getCards()) {
            exclude(card);
        }
    }

    public List<Hand> getAllHands() {
        //todo
        return null;
    }
}
