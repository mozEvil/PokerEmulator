package ru.mozevil.poker.model.rules.prizes;

public interface Prizes {

    double getPrizeForPlace(int place);

    double getTotalPrizePot();

    int getTotalPlaceCount();
}
