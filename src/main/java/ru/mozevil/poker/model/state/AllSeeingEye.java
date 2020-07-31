package ru.mozevil.poker.model.state;

import ru.mozevil.poker.model.card.Combination;
import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.table.Table;
import ru.mozevil.poker.model.table.dealer.Street;

public class AllSeeingEye {

    private Table table;

    public AllSeeingEye(Table table) {
        this.table = table;
    }

    public int getPotSize() {
        return table.getPotSize();
    }

    public boolean isPreflop() {
        return table.getStreet() == Street.ANTE || table.getStreet() == Street.PREFLOP;
    }

    public Combination getTableCards() {
        return table.getCommunityCards();
    }

    public int getDealerIndex() {
        return table.getPosTracker().getButtonIndex();
    }

    public int[] getPlayersBets() {
        int[] bets = new int[Table.MAX_PLAYERS_ON_THE_TABLE];
        for (int i = 0; i < bets.length; i++) {
            Player p = table.getPlayersArray()[i];
            if (p == null) {
                bets[i] = 0;
            } else {
                bets[i] = p.getValueChipsInPotOnThisStreet(table.getStreet());
            }
        }
        return bets;
    }

    public Player[] getAllPlayers() {
        return table.getPlayersArray();
    }
}
