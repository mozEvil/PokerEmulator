package ru.mozevil.poker.model.rules;

import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.table.Table;
import ru.mozevil.poker.model.table.dealer.Blinds;


public abstract class AbstractRules implements Rules {

    private Table table;

    public AbstractRules(Table table) {
        this.table = table;
    }

    @Override
    public void run() {

        for (Player player : table.getPlayers()) {
            player.setStack(getStartStack());
        }

        int time = 0;
        int level = 1;  // если поставить 0, то расти не будут

        while (true) {
            if (level == 0) {
                Blinds blinds = getBlinds(level);
                if (blinds != null) {
                    table.setBlinds(blinds);
                }
                return;
            }

            if (time  == level * getSpan()) {
                Blinds blinds = getBlinds(level);
                if (blinds != null) {
                    table.setBlinds(blinds);
                    level++;
                }

            }
            try {
                Thread.sleep(1000);
                time++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
