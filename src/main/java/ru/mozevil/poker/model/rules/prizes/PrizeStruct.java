package ru.mozevil.poker.model.rules.prizes;

import java.util.HashMap;

public class PrizeStruct implements Prizes {
    
    private double totalPrizePot;
    private HashMap<Integer, Double> prizeMap; // key - place, value - percent of totalPrizePot

    public PrizeStruct() {
        init();
    }

    private void init() {
        totalPrizePot = 100;

        prizeMap = new HashMap<>();
        prizeMap.put(1, 50.0);
        prizeMap.put(2, 30.0);
        prizeMap.put(3, 20.0);
    }

    @Override
    public double getPrizeForPlace(int place) {

        if (place < 1 || place > getTotalPlaceCount()) return 0;

        return  totalPrizePot * prizeMap.get(place) / 100;
    }

    @Override
    public double getTotalPrizePot() {
        return totalPrizePot;
    }

    @Override
    public int getTotalPlaceCount() {
        return prizeMap.size();
    }
}
