package ru.mozevil.poker.strategy;

import ru.mozevil.poker.model.state.Environment;

public class ViewHandleInput implements Strategy {

    @Override
    public void doMove(Environment env) {
        // do nothing, input Move happen from UI.
    }
}
