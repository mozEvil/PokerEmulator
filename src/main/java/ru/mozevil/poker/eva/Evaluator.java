package ru.mozevil.poker.eva;

import ru.mozevil.poker.model.card.Combination;
import ru.mozevil.poker.model.card.Hand;
import ru.mozevil.poker.model.state.Environment;

public interface Evaluator {

   Combination getBestCombo(Hand hand, Combination tableCards);

}
