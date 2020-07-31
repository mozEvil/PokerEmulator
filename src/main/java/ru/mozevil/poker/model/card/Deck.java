package ru.mozevil.poker.model.card;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Deck {

//    private static ImageIcon deckImage = new ImageIcon("@../images/deck/deck.png");
    private static ImageIcon deckImage = new ImageIcon("src/main/resources/images/deck/deck.png");

    private LinkedList<Card> cards;

    public Deck() {
        cards = new LinkedList<>(Arrays.asList(Card.values()));
        Collections.shuffle(cards);
    }

    public Card popCard() {
        return cards.removeFirst();
    }

    public static ImageIcon getDeckImage() {
        return deckImage;
    }

    public static Card getCard(int id) {
        if (id > 0 && id <= 52) {
            return Arrays.stream(Card.values()).filter(card -> card.getId() == id).findFirst().orElse(null);
        }
        return null;
    }
}
