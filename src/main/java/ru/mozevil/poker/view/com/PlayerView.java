package ru.mozevil.poker.view.com;

import ru.mozevil.poker.model.card.Deck;
import ru.mozevil.poker.model.card.Hand;
import ru.mozevil.poker.model.state.Environment;
import ru.mozevil.poker.model.state.Opponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static javax.swing.SpringLayout.*;

public class PlayerView extends JPanel {

    private JLabel name;
    private JLabel stack;

    private JLabel card1;
    private JLabel card2;

    private Color colorActive = new Color(0, 0, 0);
    private Color colorMoving = new Color(14, 233, 215);
    private Color colorWinner = new Color(233, 215, 19);
    private Color colorNull = new Color(0, 0, 0, 120);
    private Color colorTextActive = new Color(255, 255, 255);
    private Color colorTextMoving = new Color(0, 0, 0);

    private Border activeBorder = BorderFactory.createLineBorder(Color.orange, 3);

    public PlayerView() {
        init();
    }

    private void init() {
        setOpaque(false);

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setSize(100, 100);

        card1 = new JLabel();
        card2 = new JLabel();

        name = new JLabel("");
        stack = new JLabel("");

        setColor(colorNull);

        add(card1);
        add(card2);
        add(name);
        add(stack);

        // card1
        layout.putConstraint(WEST, card1, 0, WEST, this);
        layout.putConstraint(NORTH, card1, 0, NORTH, this);
        layout.putConstraint(EAST, card1, 50, WEST, card1); // weight = 50
        layout.putConstraint(SOUTH, card1, 70, NORTH, card1); // height = 70
        // card2
        layout.putConstraint(WEST, card2, 0, EAST, card1);
        layout.putConstraint(NORTH, card2, 0, NORTH, this);
        layout.putConstraint(EAST, card2, 50, WEST, card2); // weight
        layout.putConstraint(SOUTH, card2, 70, NORTH, card2); // height
        // name
        layout.putConstraint(WEST, name, 0, WEST, this);
        layout.putConstraint(NORTH, name, 1, SOUTH, card1);
        layout.putConstraint(EAST, name, 100, WEST, name); // weight
        layout.putConstraint(SOUTH, name, 15, NORTH, name); // height
        // stack
        layout.putConstraint(WEST, stack, 0, WEST, this);
        layout.putConstraint(NORTH, stack, -1, SOUTH, name);
        layout.putConstraint(EAST, stack, 100, WEST, stack); // weight
        layout.putConstraint(SOUTH, stack, 15, NORTH, stack); // height

        Dimension cardSize = new Dimension(50, 70);
        Dimension textSize = new Dimension(100, 15);
        Font fontStack = new Font("Verdana", Font.BOLD, 11);
        Font fontName = new Font("Verdana", Font.PLAIN, 11);


//        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        card1.setSize(cardSize);
        card1.setMaximumSize(cardSize);
        card1.setHorizontalAlignment(JLabel.CENTER);

        card2.setSize(cardSize);
        card2.setMaximumSize(cardSize);
        card2.setHorizontalAlignment(JLabel.CENTER);

        name.setSize(textSize);
        name.setMaximumSize(textSize);
        name.setHorizontalAlignment(JLabel.CENTER);
//        name.setBorder(solidBorder);
        name.setFont(fontName);
        name.setOpaque(true);
//        name.setBackground(colorTextMoving);
//        name.setForeground(colorTextActive);

        stack.setSize(textSize);
        stack.setMaximumSize(textSize);
        stack.setHorizontalAlignment(JLabel.CENTER);
//        stack.setBorder(solidBorder);
        stack.setFont(fontStack);
        stack.setOpaque(true);
//        stack.setBackground(colorTextMoving);
//        stack.setForeground(colorTextActive);

    }

    private void setColor(Color color) {
        name.setBackground(color);
        stack.setBackground(color);
    }

    private void setTextColor(Color color) {
        name.setForeground(color);
        stack.setForeground(color);
    }

    private void setPlayerName(String name) {
        this.name.setText(name);
    }

    private void setStack(int size) {
        stack.setText("" + size);
    }

    private void setHand(Hand hand) {
        if (hand == null) {
            setEmptyHand();
        } else {
            card1.setIcon(hand.getHighCard().getImage());
            card2.setIcon(hand.getLowCard().getImage());
            setColor(colorActive);
            setTextColor(colorTextActive);
        }
    }

    private void setUnknownHand() {
        card1.setIcon(Deck.getDeckImage());
        card2.setIcon(Deck.getDeckImage());
        setColor(colorActive);
        setTextColor(colorTextActive);
    }

    public void setEmptyHand() {
        card1.setIcon(null);
        card2.setIcon(null);
        setColor(colorActive);
        setTextColor(colorTextActive);
    }

    public void setNullPlayer() {
        setEmptyHand();
        setPlayerName("");
        stack.setText("");
        setColor(colorNull);
    }

    public void updateHero(Environment env) {
        setPlayerName(env.getHeroName());
        setStack(env.getHeroStack());

        if (env.heroIsActive()) {
            setHand(env.getHeroHand());
        } else {
            setEmptyHand();
        }

        if (env.heroIsMoving()) {
            setColor(colorMoving);
            setTextColor(colorTextMoving);
        }

        if (env.heroIsWinner()) {
            setColor(colorWinner);
            setTextColor(colorTextMoving);
        }
    }

    public void updateOpponent(Opponent op) {
        if (op == null) {
            setNullPlayer();
        } else {
            setPlayerName(op.getName());
            setStack(op.getStack());

            if (op.isActive()) {
                setUnknownHand();
            } else {
                setEmptyHand();
            }

            if (op.IsMoving()) {
                setColor(colorMoving);
                setTextColor(colorTextMoving);
            }

            if (op.isWinner()) {
                setColor(colorWinner);
                setTextColor(colorTextMoving);
            }
        }
    }
}
