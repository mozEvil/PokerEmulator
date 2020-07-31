package ru.mozevil.poker.model;

import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.rules.DefaultRules;
import ru.mozevil.poker.model.rules.Rules;
import ru.mozevil.poker.model.table.Table;
import ru.mozevil.poker.model.table.TableFactory;
import ru.mozevil.poker.view.PokerView;
import ru.mozevil.poker.view.View;

public class GameFactory {



    public static Game createGame() {

        Table table = TableFactory.createTable();

        Rules rules = new DefaultRules(table);

        Player hero = table.getPlayersArray()[0];
        View view = new PokerView(hero);
//        View view = null;

        Game game = new Game();
        game.setTable(table);
        game.setView(view);
        game.setRules(rules);

        return game;
    }
}
