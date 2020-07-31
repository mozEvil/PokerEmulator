package ru.mozevil.poker.model.rules;

import ru.mozevil.poker.model.rules.prizes.Prizes;
import ru.mozevil.poker.model.table.dealer.Blinds;

public interface Rules extends Runnable {

    Blinds getBlinds(int level);

    int getStartStack();

    int getSpan();

    Prizes getPrizes();
}
