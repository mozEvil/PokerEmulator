package ru.mozevil.poker.model.rules;

import ru.mozevil.poker.model.rules.prizes.PrizeStruct;
import ru.mozevil.poker.model.rules.prizes.Prizes;
import ru.mozevil.poker.model.table.Table;
import ru.mozevil.poker.model.table.dealer.Blinds;

import java.util.HashMap;

public class DefaultRules extends AbstractRules {

    private int startStack;
    private int span; // sec between blinds up
    private HashMap<Integer, Blinds> blinds;

    public DefaultRules(Table table) {
        super(table);
        init();
    }

    private void init() {

        startStack = 10000;
        span = 5;

        blinds = new HashMap<>();
        blinds.put(0, new Blinds(0, 100, 200));
        blinds.put(1, new Blinds(0, 10, 20));
        blinds.put(2, new Blinds(0, 15, 30));
        blinds.put(3, new Blinds(0, 30, 60));
        blinds.put(4, new Blinds(0, 50, 100));
        blinds.put(5, new Blinds(0, 60, 120));
        blinds.put(6, new Blinds(0, 80, 160));
        blinds.put(7, new Blinds(0, 100, 200));
        blinds.put(8, new Blinds(0, 150, 300));
        blinds.put(9, new Blinds(10, 250, 500));
        blinds.put(10, new Blinds(15, 300, 600));
        blinds.put(11, new Blinds(25, 500, 1000));
        blinds.put(12, new Blinds(30, 600, 1200));
        blinds.put(13, new Blinds(40, 800, 1600));
        blinds.put(14, new Blinds(50, 1000, 2000));
        blinds.put(15, new Blinds(100, 1000, 2000));
    }

    @Override
    public Blinds getBlinds(int level) {
        return blinds.get(level);
    }

    @Override
    public int getStartStack() {
        return startStack;
    }

    @Override
    public int getSpan() {
        return span;
    }

    @Override
    public Prizes getPrizes() {
        return new PrizeStruct();
    }
}
