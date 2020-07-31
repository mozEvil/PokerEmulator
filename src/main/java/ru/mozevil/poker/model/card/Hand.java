package ru.mozevil.poker.model.card;

import java.util.Arrays;
import java.util.Collections;

public class Hand {

    public static final Hand NULL_HAND = new Hand(null, null);

    private final Card[] cards = new Card[2];

    public Hand(Card card1, Card card2) {
        cards[0] = card1;
        cards[1] = card2;

        if (card1 != null && card2 != null) {
            Arrays.sort(cards, Collections.reverseOrder());
        }
    }

    public Card getHighCard() {
        return cards[0];
    }

    public Card getLowCard() {
        return cards[1];
    }

    public boolean isPair() {
        if (cards[0] == null || cards[1] == null) return false;

        return cards[0].getRank() == cards[1].getRank();
    }

    public boolean isSuit() {
        if (cards[0] == null || cards[1] == null) return false;

        return cards[0].getSuit() == cards[1].getSuit();
    }

    @Override
    public String toString() {
        if (cards[0] == null || cards[1] == null) return "nullHand";

        return cards[0].toString() + cards[1].toString();
    }
}
