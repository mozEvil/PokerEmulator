package ru.mozevil.poker.eva;

import ru.mozevil.poker.model.card.Card;
import ru.mozevil.poker.model.card.Deck;

import java.util.ArrayList;
import java.util.List;

public class ComboEvaluator {

//    7  6  5  4  3  2  1
//    ....................
//    52 51 50 49 48 47 46
//    --------------------
//    45 45 45 45 45 45 45
//
//    10 8 7 5 4 2 1
//    3 2 2 1 1 0 0 + 1 = 10

    private final int[] min = new int[]{7, 6, 5, 4, 3, 2, 1};
    private final int[] max = new int[]{52, 51, 50, 49, 48, 47, 46};

    private int[] cc = null;

    public int[] getComboArray() {
        return cc;
    }

    public List<Card> getComboList() {
        if (cc == null) return null;

        List<Card> result = new ArrayList<>(7);

        for (int id : cc) {
            result.add(Deck.getCard(id));
        }

        return result;
    }

    public boolean nextCombo() {
        if (cc == null) {
            cc = new int[] {7, 6, 5, 4, 3, 2, 1};
            return true;
        }

        return inc(6);
    }

    private boolean inc(int i) {
        if (cc[i] < max[i]){
            if (i > 0) {
                if (cc[i-1] - cc[i] > 1) {
                    cc[i]++;
                    resetDex(i);
                    return true;
                } else {
                    return inc(i-1);
                }
            } else {
                cc[i]++;
                resetDex(i);
                return true;
            }
        } else {
            // конец
            System.out.println("END");
            return false;
        }
    }

    private void resetDex(int i) {
        // все элементы правее от i ставим на минимум
        for (i++; i < 7; i++) {
            cc[i] = min[i];
        }
    }



}
