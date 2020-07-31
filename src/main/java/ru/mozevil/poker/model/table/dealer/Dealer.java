package ru.mozevil.poker.model.table.dealer;


import ru.mozevil.poker.model.card.Combination;
import ru.mozevil.poker.model.card.Deck;
import ru.mozevil.poker.eva.Evaluator;
import ru.mozevil.poker.eva.MozEvaluator;
import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.table.Table;

import java.util.List;

/**
 * Facade for Table
 * */
public class Dealer {
    //todo Facade for Table

    private Table table;
    private List<Player> activePlayers;

    private Combination communityCards;
    private Pot pot;
    private int street;

    private Deck deck;
    private PositionTracker posTracker;
    private Evaluator evaluator;

    public Dealer(Table table) {
        this.table = table;
        communityCards = new Combination();
        pot = new Pot();
        street = Table.PREFLOP;
        deck = new Deck();
        posTracker = new PositionTracker(table.getPlayersArray());
        evaluator = new MozEvaluator();
    }




    private void clear() {
        street = Table.PREFLOP;
        pot.clear();
        communityCards = new Combination();
        deck = new Deck();
    }
}
