package ru.mozevil.poker.model.card;

import java.io.Serializable;

public enum Rank implements Serializable {

    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("T"),
    JACK("J"),
    QUEEN("Q"),
    KING("K"),
    ACE("A");

    private final String name;

    Rank(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Rank getRankByName(String name) {
        for (Rank rank : Rank.values()) {
            if (rank.getName().equals(name)) return rank;
        }
        return null;
    }

    public Rank nextRank() {
        int firstIndex = 0;
        int lastIndex = Rank.values().length - 1;
        int nextIndex = this.ordinal() + 1;

        if (nextIndex > lastIndex) {
            return Rank.values()[firstIndex];
        }
        return Rank.values()[nextIndex];
    }

    public Rank previousRank() {
        int firstIndex = 0;
        int lastIndex = Rank.values().length - 1;
        int previousIndex = this.ordinal() - 1;

        if (previousIndex < firstIndex) {
            return Rank.values()[lastIndex];
        }
        return Rank.values()[previousIndex];
    }

    @Override
    public String toString() {
        return getName();
    }
}
