package ru.mozevil.poker.view;

import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.table.Table;

import java.util.concurrent.Flow.Subscriber;

public interface View extends Subscriber<Table> {

    void showEnvironmentFor(Player player);

    void refresh(Table table);
}