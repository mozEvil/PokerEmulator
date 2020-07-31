package ru.mozevil.poker.model.state;

import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.table.Table;

public class StateFactory {

    public static void createEnvironmentForPlayersOnTheTable(Table table) {
        for (Player player : table.getPlayers()) {
            player.setEnvironment(new Environment(player, table));
        }
    }
}
