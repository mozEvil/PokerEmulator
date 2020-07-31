package ru.mozevil.poker.strategy;

import ru.mozevil.poker.model.state.Environment;

public class FiftyFifty implements Strategy {

    @Override
    public void doMove(Environment env) {
        int rnd = (int)(Math.random() * 10);

        // CALL/CHECK
        if (rnd == 2 || rnd == 3 || rnd == 4 ) {
            env.heroPressCall();
        } else
        // RAISE/CALL
        if (rnd == 0 || rnd == 1){
            env.heroPressRaise();
        } else
        // FOLD/CHECK
        env.heroPressFold();
    }
}
