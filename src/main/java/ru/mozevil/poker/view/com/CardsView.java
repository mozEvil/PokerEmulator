package ru.mozevil.poker.view.com;

import org.jetbrains.annotations.NotNull;
import ru.mozevil.poker.model.card.Combination;
import ru.mozevil.poker.model.card.Deck;

import javax.swing.*;

import java.awt.*;

import static javax.swing.SpringLayout.*;

public class CardsView extends JPanel {

    private JLabel[] cards = new JLabel[5];

    public CardsView() {
        init();
    }

    private void init() {
        setOpaque(false);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setSize(250, 70);

        ImageIcon deck = Deck.getDeckImage();

        for (int i = 0; i < cards.length; i++) {
            cards[i] = new JLabel(deck);
            add(cards[i]);
        }

        // card0
        layout.putConstraint(WEST, cards[0], 0, WEST, this);
        layout.putConstraint(NORTH, cards[0], 0, NORTH, this);
        layout.putConstraint(EAST, cards[0], 50, WEST, cards[0]); // weight
        layout.putConstraint(SOUTH, cards[0], 70, NORTH, cards[0]); // height

        // card1
        layout.putConstraint(WEST, cards[1], 0, EAST, cards[0]);
        layout.putConstraint(NORTH, cards[1], 0, NORTH, this);
        layout.putConstraint(EAST, cards[1], 50, WEST, cards[1]); // weight
        layout.putConstraint(SOUTH, cards[1], 70, NORTH, cards[1]); // height

        // card2
        layout.putConstraint(WEST, cards[2], 0, EAST, cards[1]);
        layout.putConstraint(NORTH, cards[2], 0, NORTH, this);
        layout.putConstraint(EAST, cards[2], 50, WEST, cards[2]); // weight
        layout.putConstraint(SOUTH, cards[2], 70, NORTH, cards[2]); // height

        // card3
        layout.putConstraint(WEST, cards[3], 0, EAST, cards[2]);
        layout.putConstraint(NORTH, cards[3], 0, NORTH, this);
        layout.putConstraint(EAST, cards[3], 50, WEST, cards[3]); // weight
        layout.putConstraint(SOUTH, cards[3], 70, NORTH, cards[3]); // height

        // card4
        layout.putConstraint(WEST, cards[4], 0, EAST, cards[3]);
        layout.putConstraint(NORTH, cards[4], 0, NORTH, this);
        layout.putConstraint(EAST, cards[4], 50, WEST, cards[4]); // weight
        layout.putConstraint(SOUTH, cards[4], 70, NORTH, cards[4]); // height

    }

    public void setCards(Combination combo) {
        clear();

        if (combo == null) {
            return;
        }

        if (combo.getCount() < 3) {
            return;
        }

        for (int i = 0; i < combo.getCount(); i++) {
            cards[i].setIcon(combo.getCard(i).getImage());
        }
    }

    public void clear() {
        for (int i = 0; i < cards.length; i++) {
            cards[i].setIcon(null);
        }
    }
}
