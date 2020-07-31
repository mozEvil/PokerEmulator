package ru.mozevil.poker.model.state;

import org.jetbrains.annotations.NotNull;
import ru.mozevil.poker.model.card.Range;
import ru.mozevil.poker.model.player.Move;
import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.table.dealer.Street;
import ru.mozevil.poker.statistic.Statistic;


public class Opponent {

    private Player player;

    private Range range;
    private Statistic statistic;

    public Opponent(@NotNull Player player) {
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }

    public int getStack() {
        return player.getStack();
    }

    public int getValueChipsInPot() {
        return player.getValueChipsInPot();
    }

    public int getValueChipsInPotOnThisStreet(Street street) {
        return player.getValueChipsInPotOnThisStreet(street);
    }

    public Move getMove() {
        return player.getMove();
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public boolean isAllin() {
        return player.isAllin();
    }

    public boolean isAgressor() {
        return player.isRaise();
    }

    public boolean isActive() {
        return player.isActive();
    }

    public boolean IsMoving() {
        return player.isMoving();
    }

    public boolean isWinner() {
        return player.isWinner();
    }

    public int getWinSize() {
        return player.getWinSize();
    }
}
