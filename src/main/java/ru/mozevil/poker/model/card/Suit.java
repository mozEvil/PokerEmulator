package ru.mozevil.poker.model.card;

import java.io.Serializable;

public enum Suit implements Serializable {
    CLUBS("c"),
    DIAMONDS("d"),
    HEARTS("h"),
    SPADES("s");

    private final String name;

    Suit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
