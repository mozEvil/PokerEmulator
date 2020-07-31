package ru.mozevil.poker.model.player;

import ru.mozevil.poker.strategy.FiftyFifty;
import ru.mozevil.poker.strategy.SnG_9max;
import ru.mozevil.poker.strategy.ViewHandleInput;

public class PlayerFactory {

    private static int count = 0;

    public static Player createHero() {
        var hero = new Player("Hero");
        hero.setStrategy(new ViewHandleInput());

        return hero;
    }

    public static Player createBot() {
        var bot = new Player("Bot_" + ++count);
        bot.setStrategy(new SnG_9max());

        return bot;
    }

    public static Player createBotFifty() {
        var bot = new Player("Bot_" + ++count);
        bot.setStrategy(new FiftyFifty());

        return bot;
    }

}
