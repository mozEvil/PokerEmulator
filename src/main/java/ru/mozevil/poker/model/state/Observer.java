package ru.mozevil.poker.model.state;

import org.jetbrains.annotations.NotNull;
import ru.mozevil.poker.model.card.Combination;
import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.table.dealer.Blinds;
import ru.mozevil.poker.model.table.dealer.Street;
import ru.mozevil.poker.model.table.Table;

public class Observer {

    private Table table;

    public Observer(@NotNull Table table) {
        this.table = table;
    }

    public int getPotSize() {
        return table.getPotSize();
    }

    public Combination getTableCards() {
        return table.getCommunityCards();
    }

    public Blinds getBlinds() {
        return table.getBlinds();
    }

    public Street getStreet() {
        return table.getStreet();
    }

    public int getDealerIndex() {
        return table.getPosTracker().getButtonIndex();
    }

    public Opponent[] getAllOpponents() {
        Opponent[] opponents = new Opponent[Table.MAX_PLAYERS_ON_THE_TABLE];
        for (int i = 0; i < opponents.length; i++) {
            Player p = table.getPlayersArray()[i];
            if (p == null) {
                opponents[i] = null;
            } else {
                opponents[i] = new Opponent(p);
            }
        }
        return opponents;
    }

    public int[] getPlayersBets() {
        int[] bets = new int[Table.MAX_PLAYERS_ON_THE_TABLE];
        for (int i = 0; i < bets.length; i++) {
            Player p = table.getPlayersArray()[i];
            if (p == null) {
                bets[i] = 0;
            } else {
                bets[i] = p.getValueChipsInPotOnThisStreet(getStreet());
            }
        }
        return bets;
    }

    public boolean isPreflop() {
        return getStreet() == Street.PREFLOP ||  getStreet() == Street.ANTE;
    }
}
