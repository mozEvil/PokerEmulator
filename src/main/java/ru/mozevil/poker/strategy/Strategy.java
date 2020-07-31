package ru.mozevil.poker.strategy;

import ru.mozevil.poker.model.state.Environment;

public interface Strategy {

    void doMove(Environment env);
}
