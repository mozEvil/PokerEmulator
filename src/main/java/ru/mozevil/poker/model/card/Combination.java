package ru.mozevil.poker.model.card;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Combination implements Comparable<Combination> {

    public static final int HIGH_CARD = 0;
    public static final int PAIR = 1;
    public static final int TWO_PAIRS = 2;
    public static final int THREE_OF_A_KIND = 3;
    public static final int STRAIGHT = 4;
    public static final int FLUSH = 5;
    public static final int FULL_HOUSE = 6;
    public static final int FOUR_OF_A_KIND = 7;
    public static final int STRAIGHT_FLUSH = 8;
    public static final int ROYAL_FLUSH = 9;


    private final List<Card> cards = new ArrayList<>(5);
    private int title = 0;
    private long strength = 0;

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
        strength = getSum(title);
    }

    private long getSum(int title) {
        switch (title){
            case(HIGH_CARD): return getSumHighCard();
            case(PAIR): return getSumPair();
            case(TWO_PAIRS): return getSumTwoPairs();
            case(THREE_OF_A_KIND): return getSumTrips();
            case(STRAIGHT): return getSumStraight();
            case(FLUSH): return getSumFlush();
            case(FULL_HOUSE): return getSumFullHouse();
            case(FOUR_OF_A_KIND): return getSumSquad();
            case(STRAIGHT_FLUSH): return getSumStraightFlush();
            case(ROYAL_FLUSH): return getSumRoyalFlush();
        }
        return 0;
    }

    private long getSumHighCard() {
        // 0 - 1 999 999 999
        long value = 0L;
        value += getCard(0).getRank().ordinal() * 100000000;
        value += getCard(1).getRank().ordinal() * 1000000;
        value += getCard(2).getRank().ordinal() * 10000;
        value += getCard(3).getRank().ordinal() * 100;
        value += getCard(4).getRank().ordinal();

        return value;
    }

    private long getSumPair() {
//        2 000 000 000 - 3 999 999 999
        long value = 2000000000L;

        value += getCard(0).getRank().ordinal() * 100000000;
        value += getCard(2).getRank().ordinal() * 10000;
        value += getCard(3).getRank().ordinal() * 100;
        value += getCard(4).getRank().ordinal();

        return value;
    }

    private long getSumTwoPairs() {
        // 4 000 000 000 - 5 999 999 999
        long value = 4000000000L;

        value += getCard(0).getRank().ordinal() * 100000000;
        value += getCard(2).getRank().ordinal() * 10000;
        value += getCard(4).getRank().ordinal();

        return value;
    }

    private long getSumTrips() {
        // 6 000 000 000 - 7 999 999 999
        long value = 6000000000L;

        value += getCard(0).getRank().ordinal() * 100000000;
        value += getCard(3).getRank().ordinal() * 100;
        value += getCard(4).getRank().ordinal();

        return value;
    }

    private long getSumStraight() {
        // 8 000 000 000 - 9 999 999 999
        long value = 8000000000L;

        value += getCard(0).getRank().ordinal() * 100000000;

        return value;
    }

    private long getSumFlush() {
        // 10 000 000 000 - 11 999 999 999
        long value = 10000000000L;

        value += getCard(0).getRank().ordinal() * 100000000;
        value += getCard(1).getRank().ordinal() * 1000000;
        value += getCard(2).getRank().ordinal() * 10000;
        value += getCard(3).getRank().ordinal() * 100;
        value += getCard(4).getRank().ordinal();

        return value;
    }

    private long getSumFullHouse() {
        // 12 000 000 000 - 13 999 999 999
        long value = 12000000000L;

        value += getCard(0).getRank().ordinal() * 100000000;
        value += getCard(3).getRank().ordinal() * 100;

        return value;
    }

    private long getSumSquad() {
        // 14 000 000 000 - 15 999 999 999
        long value = 14000000000L;

        value += getCard(0).getRank().ordinal() * 100000000;
        value += getCard(4).getRank().ordinal();

        return value;
    }

    private long getSumStraightFlush() {
        // 16 000 000 000 - 17 999 999 999
        long value = 16000000000L;

        value += getCard(0).getRank().ordinal() * 100000000;

        return value;
    }

    private long getSumRoyalFlush() {
        // 18 000 000 000 - 19 999 999 999
        return 19200000000L;
    }


    public void addCard(Card card) {
        cards.add(card);
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getCard(int index) {
//        if(index >= getCount()) return null;
        return cards.get(index);
    }

    public long getStrength() {
        return strength;
    }

    public int getCount() {
        return cards.size();
    }

    @Override
    public String toString() {
        return getTitle() + " (" + getStrength() +") ";
    }

    @Override
    public int compareTo(@NotNull Combination o) {
        return Long.compare(strength, o.strength);
    }
}
