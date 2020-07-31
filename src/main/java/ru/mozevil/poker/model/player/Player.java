package ru.mozevil.poker.model.player;

import ru.mozevil.poker.model.card.Combination;
import ru.mozevil.poker.model.card.Hand;
import ru.mozevil.poker.model.state.Environment;
import ru.mozevil.poker.model.table.dealer.Street;
import ru.mozevil.poker.strategy.Strategy;

import java.util.HashMap;
import java.util.Map;

import static ru.mozevil.poker.model.player.Move.Decision.*;

public class Player {

    public static final int POS0 = 0;
    public static final int POS1 = 1;
    public static final int POS2 = 2;
    public static final int POS3 = 3;
    public static final int POS4 = 4;
    public static final int POS5 = 5;
    public static final int POS6 = 6;
    public static final int POS7 = 7;
    public static final int POS8 = 8;

    private String name;
    private int stack;
    private Map<Street, Integer> chipsInPot;
    private int winSize;
    private Hand hand;
    private int position;
    private Combination combination;

    private volatile Move move;
    private volatile boolean isMoving;

    private Environment environment;
    private Strategy strategy;

    public Player(String name) {
        this.name = name;
        clear();
    }

    public String getName() {
        return name;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public void setWinSize(int value) {
        winSize = value;
    }

    public int getWinSize() {
        return winSize;
    }

    public void transferChipsToStack(int value) {
        stack += value;
    }

    public void transferChipsToPot(Street street) {
        int newValue = getValueChipsInPotOnThisStreet(street) + move.getSize();
        chipsInPot.put(street, newValue);
        stack -= move.getSize();
    }

    public int getValueChipsInPot() {
        int value = 0;
        for (Integer chips : chipsInPot.values()) {
            value += chips;
        }
        return value;
    }

    public int getValueChipsInPotOnThisStreet(Street street) {
        Integer value = chipsInPot.get(street);
        return value == null ? 0 : value;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
//        System.out.println(toString() + " Move: " + move.toString());
        isMoving = false;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Combination getCombination() {
        return combination;
    }

    public void setCombination(Combination combination) {
        this.combination = combination;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String toString() {
//        String h = hand == null ? "" : " " + hand.toString();
//        return name + " (" + stack + ")" + h;
        return name + " (" + stack + ")";
    }

    public void doMove() {
        isMoving = true;
        strategy.doMove(environment);
    }

    public void clear() {
        chipsInPot = new HashMap<>();
        winSize = 0;
        move = new Move();
        isMoving = false;
        hand = null;
        combination = null;
    }

    public boolean isActive() {
        return move.getDecision() != FOLD;
    }

    public boolean isNotMovingYet() {
        return move.getDecision() == UNKNOWN;
    }

    public boolean isFold() {
        return move.getDecision() == FOLD;
    }

    public boolean isCall() {
        return move.getDecision() == CALL;
    }

    public boolean isCheck() {
        return move.getDecision() == CHECK;
    }

    public boolean isAllin() {
        return stack == 0;
    }

    public boolean isRaise() {
        return move.getDecision() == RAISE;
    }

    public boolean isWinner() {
        return winSize > 0;
    }
}
