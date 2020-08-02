package ru.mozevil.poker.model.table;

import ru.mozevil.poker.model.player.PlayerFactory;
import ru.mozevil.poker.model.state.StateFactory;

public class TableFactory {

    public static Table createTable() {

        Table table = new Table();

        table.setSleepTime(400);
        table.setGodMode(true);

        table.addPlayer(PlayerFactory.createHero());
//        table.addPlayer(PlayerFactory.createBotFifty());
        table.addPlayer(PlayerFactory.createBotFifty());
        table.addPlayer(PlayerFactory.createBotFifty());
        table.addPlayer(PlayerFactory.createBotFifty());
        table.addPlayer(PlayerFactory.createBotFifty());
        table.addPlayer(PlayerFactory.createBotFifty());
        table.addPlayer(PlayerFactory.createBotFifty());
        table.addPlayer(PlayerFactory.createBotFifty());
        table.addPlayer(PlayerFactory.createBotFifty());

        StateFactory.createEnvironmentForPlayersOnTheTable(table);

        return table;
    }


    public static Table createTableAllHeroes() {

        Table table = new Table();

        table.setSleepTime(400);
        table.setGodMode(true);
        table.setDebugMode(true);

        table.addPlayer(PlayerFactory.createHero());
        table.addPlayer(PlayerFactory.createHero());
        table.addPlayer(PlayerFactory.createHero());

        StateFactory.createEnvironmentForPlayersOnTheTable(table);

        return table;
    }
}
