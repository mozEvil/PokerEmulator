package ru.mozevil.poker.eva;

import org.jetbrains.annotations.NotNull;
import ru.mozevil.poker.model.card.Card;
import ru.mozevil.poker.model.card.Combination;
import ru.mozevil.poker.model.card.Hand;
import ru.mozevil.poker.model.card.Range;
import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.rules.prizes.Prizes;
import ru.mozevil.poker.model.state.Environment;
import ru.mozevil.poker.model.state.Opponent;

import java.util.*;

public class EquityCalculator {

    private Evaluator eva;

    public EquityCalculator(Evaluator eva) {
        this.eva = eva;
    }

    //todo
    /**
     * Ожидаемое эквити после пуша.
     * Если > 0, то пуш будет прибыльным на дистанции.
     * Эквити вырожено в количестве фишек
     * */
    public double getChipEV(Environment env) {

//        int minStack = env.getEffectiveStack();
//        int maxStack = env.getChipLeaderStack();
//        int heroStack = env.getHeroStack() + env.getHeroValueChipsInPot();

//        double winEV = getWinChipEV(env);
//        double looseEV = getLooseChipEV(env);

//        return winEV - looseEV;
        return 0;
    }

    public double getChipEV(Player hero, List<Opponent> opps, Combination tableCards) {
        //todo
        return 0;
    }

    /**
     * Ожидаемое эквити после пуша.
     * Если > 0, то пуш будет прибыльным на дистанции.
     * Эквити вырожено в количестве денег
     * */
    public double getMoneyEV(Environment env) {

        double[][] stackMap = getStackMap(env);
        Prizes prizes = env.getPrizes();

        double currentEquity = getCurrentEquity(stackMap, prizes);
        double afterWinEquity = getAfterWinEquity(stackMap, prizes);
        double afterLooseEquity = getAfterLooseEquity(stackMap, prizes);

        double winEquity = afterWinEquity - currentEquity;
        double looseEquity = currentEquity - afterLooseEquity;

        Rate rate = getRate(env);

        double winEV = rate.getWin() * winEquity;
        double looseEV = rate.getLoose() * looseEquity;

        return winEV - looseEV;
    }

    public double getMoneyEV(Player hero, List<Opponent> opps,
                             Combination tableCards, Prizes prizes) {

        int heroStack = hero.getStack() + hero.getValueChipsInPot();
        int[] oppStacks = opps.stream().mapToInt(
                op -> op.getStack() + op.getValueChipsInPot()
        ).toArray();

        double[][] stackMap = getStackMap(heroStack, oppStacks);

        double currentEquity = getCurrentEquity(stackMap, prizes);
        double afterWinEquity = getAfterWinEquity(stackMap, prizes);
        double afterLooseEquity = getAfterLooseEquity(stackMap, prizes);

        double winEquity = afterWinEquity - currentEquity;
        double looseEquity = currentEquity - afterLooseEquity;

        Rate rate = getRate(hero.getHand(), tableCards, opps);

        double winEV = rate.getWin() * winEquity;
        double looseEV = rate.getLoose() * looseEquity;

        return winEV - looseEV;
    }

    /**
     * double[i][4]
     * where i - index of player (0 - hero),
     * 0 - stack of player
     * 1 - chance to finish on 1st place
     * 2 - chance to finish on 2nd place
     * 3 - chance to finish on 3rd place
     * */
    private double[][] getStackMap(Environment env) {
        int heroStack = env.getHeroStack() + env.getHeroValueChipsInPot();
        int[] oppStacks = env.getOppStacks();

        return getStackMap(heroStack, oppStacks);
    }

    private double[][] getStackMap(int heroStack, int[] oppStacks) {
        double[][] stackMap = new double[oppStacks.length + 1][4];
        stackMap[0][0] = heroStack;

        for (int i = 0; i < oppStacks.length; i++) {
            stackMap[i + 1][0] = oppStacks[i];
        }

        return stackMap;
    }

    private double getCurrentEquity(double[][] stackMap, Prizes prizes) {
//  equity = (Вероятность попасть на 1-ое место * Выплата за 1-ое место)
//         + (Вероятность попасть на 2-ое место * Выплата за 2-ое место)
//         + (Вероятность попасть на 3-е место * Выплата за 3-е место)

        // определяем шансы занять первое место для каждого игрока
        fillFirstPlaceChance(stackMap);

        if (prizes.getTotalPlaceCount() == 1) {
            return stackMap[0][1] * prizes.getPrizeForPlace(1);
        }

        // определяем шансы занять второе место для каждого игрока
        fillSecondPlaceChance(stackMap);

        if (prizes.getTotalPlaceCount() == 2 || stackMap.length == 2) {
            return stackMap[0][1] * prizes.getPrizeForPlace(1)
                    + stackMap[0][2] * prizes.getPrizeForPlace(2);
        }

        // определяем шансы занять третье место для hero
        fillThirdPlaceChance(stackMap);

        return stackMap[0][1] * prizes.getPrizeForPlace(1)
                + stackMap[0][2] * prizes.getPrizeForPlace(2)
                + stackMap[0][3] * prizes.getPrizeForPlace(3);
    }

    private void fillFirstPlaceChance(double[][] stackMap) {
        // Вероятность попасть на 1-ое место = стек / Общее количество фишек в игре
        double totalChips = 0;

        // вычисляем общее количество фишек
        for (int i = 0; i < stackMap.length; i++) {
            totalChips += stackMap[i][0];
        }

        // определяем шансы занять первое место для каждого игрока
        for (int i = 0; i < stackMap.length; i++) {
            stackMap[i][1] = stackMap[i][0] / totalChips;
        }
    }

    private void fillSecondPlaceChance(double[][] stackMap) {
        // Вероятность попасть на 2-ое место = Pr(A2) = Pr(B1) * Pr(A2) + Pr(C1) * Pr(A2) + ...
        int playersCount = stackMap.length;

        if (playersCount == 2) {
            for (int i = 0; i < playersCount; i++) {
                stackMap[i][2] = 1 - stackMap[i][1];
            }
            return;
        }

        double[][] tmpChance = new double[playersCount][playersCount];
        for (int i = 0; i < playersCount; i++) {
            tmpChance[i][i] = 0; // необходимо для упрощения подсчетов в дальнейшем коде
        }
        // поочередно исключаем из претендентов на второе место одного игрока
        // предпологая что он займет первое место
        for (int i = 0; i < playersCount; i++) {

            double[][] tempMap = new double[playersCount - 1][2];
            int index = 0;

            for (int j = 0; j < playersCount; j++) {
                if (j != i) {
                    tempMap[index][0] = stackMap[j][0];
                    index++;
                }
            }

            fillFirstPlaceChance(tempMap);

            index = 0;
            for (int j = 0; j < playersCount; j++) {
                if (j != i) {
                    tmpChance[j][i] = stackMap[i][1] * tempMap[index][1];
                    index++;
                }
            }
        }

        for (int i = 0; i < playersCount; i++) {
            double chance = 0;
            for (int j = 0; j < playersCount; j++) {
                chance += tmpChance[i][j];
            }
            stackMap[i][2] = chance;
        }

    }

    private void fillThirdPlaceChance(double[][] stackMap) {
        // Вероятность попасть на 3-е место =
        // Pr(A3) = Pr(B1) * Pr(C2) * Pr(A3) + Pr(B1) * Pr(D2) * Pr(A3) + ...
        // ... + Pr(C1) * Pr(B2) * Pr(A3) + ...
        int playersCount = stackMap.length;

        if (playersCount == 3) {
            for (int i = 0; i < playersCount; i++) {
                stackMap[i][3] = 1 - stackMap[i][1] - stackMap[i][2];
            }
            return;
        }

        double heroChance = 0;

        // считаем шансы только для hero, т.к. для остальных игроков эти расчеты нам не нужны
        for (int i = 1; i < playersCount; i++) { //i - первые места
            for (int j = 1; j < playersCount; j++) { //j - вторые места
                if (j == i) continue;

                double[][] tempMap = new double[playersCount - 2][2];
                tempMap[0][0] = stackMap[0][0]; // стек hero
                int index = 1;

                for (int k = 1; k < playersCount; k++) { // k - третьи места
                    if (k == i || k == j) continue;

                    tempMap[index][0] = stackMap[k][0]; // все претенденты на 3-е место
                    index++;
                }

                fillFirstPlaceChance(tempMap);

                heroChance += stackMap[i][1] * stackMap[j][2] * tempMap[0][1];
            }
        }

        stackMap[0][3] = heroChance;
    }

    private double getAfterWinEquity(double[][] stackMap, Prizes prizes) {
        // посчитать эквити для всех возможных вариантов победы и сложить их
        //todo
        double[][] winStackMap = new double[stackMap.length][3];

        for (int i = 0; i < stackMap.length; i++) {

        }

        return 0;
    }

    private double getAfterLooseEquity(double[][] stackMap, Prizes prizes) {
        //todo

        return 0;
    }

    //todo public Rate getRateByMonteCarlo(Environment env) {}

    /**
     * Вероятности победы/поражения/разделения для руки Hero, при текущих обстоятельствах
     * Перебор всех вариантов.
     * */
    public Rate getRate(Environment env) {
        Hand heroHand = env.getHeroHand();
        Combination tableCards = env.getTableCards();
        List<Opponent> opponents = env.getOpponents();

        return getRate(heroHand, tableCards, opponents);
    }

    public Rate getRate(@NotNull Hand hand, Combination tableCards, @NotNull List<Opponent> opps) {

        if (tableCards != null) {

            if (tableCards.getCount() == 3) {
                return getFlopRate(hand, tableCards, opps);
            }
            if (tableCards.getCount() == 4) {
                return getTurnRate(hand, tableCards, opps);
            }
            if (tableCards.getCount() == 5) {
                return getRiverRate(hand, tableCards, opps);
            }
        }

        return getPreflopRate(hand, opps);
    }

    private Rate getPreflopRate(Hand hand, List<Opponent> opps) {

        double winRate = 0;
        double looseRate = 0;
        double splitRate = 0;
        int count = 0;

        ArrayList<Card> lastCards = new ArrayList<>(Arrays.asList(Card.values()));

        lastCards.remove(hand.getHighCard());
        lastCards.remove(hand.getLowCard());

        for (int i = 0; i < lastCards.size(); i++) {
            for (int j = i + 1; j < lastCards.size(); j++) {
                for (int k = j + 1; k < lastCards.size(); k++) {
                    for (int l = k + 1; l < lastCards.size(); l++) {
                        for (int m = l + 1; m < lastCards.size(); m++) {
                            Combination probableTableCards = new Combination();
                            probableTableCards.addCard(lastCards.get(i));
                            probableTableCards.addCard(lastCards.get(j));
                            probableTableCards.addCard(lastCards.get(k));
                            probableTableCards.addCard(lastCards.get(l));
                            probableTableCards.addCard(lastCards.get(m));

                            Rate riverRate = getRiverRate(hand, probableTableCards, opps);
                            count++;

                            winRate += riverRate.getWin();
                            looseRate += riverRate.getLoose();
                            splitRate += riverRate.getSplit();
                        }
                    }
                }
            }
        }

        double win = winRate / count;
        double loose = looseRate / count;
        double split = splitRate / count;

        return new Rate(win, loose, split);
    }

    private Rate getFlopRate(Hand hand, Combination tableCards, List<Opponent> opps) {

        double winRate = 0;
        double looseRate = 0;
        double splitRate = 0;
        int count = 0;

        ArrayList<Card> lastCards = new ArrayList<>(Arrays.asList(Card.values()));

        lastCards.remove(hand.getHighCard());
        lastCards.remove(hand.getLowCard());
        lastCards.removeAll(tableCards.getCards());

        for (int i = 0; i < lastCards.size(); i++) {
            for (int j = i + 1; j < lastCards.size(); j++) {
                Combination probableTableCards = new Combination();
                probableTableCards.addCards(tableCards.getCards());
                probableTableCards.addCard(lastCards.get(i)); // возможная карта терна
                probableTableCards.addCard(lastCards.get(j)); // возможная карта ривера

                Rate riverRate = getRiverRate(hand, probableTableCards, opps);
                count++;

                winRate += riverRate.getWin();
                looseRate += riverRate.getLoose();
                splitRate += riverRate.getSplit();
            }
        }

        double win = winRate / count;
        double loose = looseRate / count;
        double split = splitRate / count;

        return new Rate(win, loose, split);
    }

    private Rate getTurnRate(Hand hand, Combination tableCards, List<Opponent> opps) {

        double winRate = 0;
        double looseRate = 0;
        double splitRate = 0;

        LinkedList<Card> lastCards = new LinkedList<>(Arrays.asList(Card.values()));

        lastCards.removeFirstOccurrence(hand.getHighCard());
        lastCards.removeFirstOccurrence(hand.getLowCard());
        lastCards.removeAll(tableCards.getCards());

        for (Card card : lastCards) {
            Combination probableTableCards = new Combination();
            probableTableCards.addCards(tableCards.getCards());
            probableTableCards.addCard(card); // возможная карта ривера

            Rate riverRate = getRiverRate(hand, probableTableCards, opps);

            winRate += riverRate.getWin();
            looseRate += riverRate.getLoose();
            splitRate += riverRate.getSplit();
        }

        double win = winRate / lastCards.size();
        double loose = looseRate / lastCards.size();
        double split = splitRate / lastCards.size();

        return new Rate(win, loose, split);
    }

    private Rate getRiverRate(Hand hand, Combination tableCards, List<Opponent> opps) {

        Combination heroCombo = eva.getBestCombo(hand, tableCards);

        Map<Opponent, List<Combination>> oppsCombos = new HashMap<>();

        for (Opponent opp : opps) {
            Range range = opp.getRange();
            range.exclude(hand);
            range.exclude(tableCards);

            List<Hand> oppHands = range.getAllHands();
            List<Combination> combos = new LinkedList<>();

            for (Hand h : oppHands) {
                Combination combo = eva.getBestCombo(h, tableCards);
                combos.add(combo);
            }

            oppsCombos.put(opp, combos);
        }

        double win = 101;
        double loose = -1;
        double split = -1;

        for (List<Combination> combos : oppsCombos.values()) {

            int winCount = 0;
            int looseCount = 0;
            int splitCount = 0;

            for (Combination combo : combos) {
                int compareResult = heroCombo.compareTo(combo);

                if (compareResult > 0) {
                    winCount++;
                }
                if (compareResult < 0) {
                    looseCount++;
                }
                if (compareResult == 0) {
                    splitCount++;
                }
            }

            double total = winCount + looseCount + splitCount;

            double tmp_win = winCount / total * 100;
            double tmp_loose = looseCount / total * 100;
            double tmp_split = splitCount / total * 100;

            // учитываем только самый наихудший результат
            if (tmp_loose > loose
                    || (tmp_loose == loose && tmp_split > split)
                    || (tmp_loose == loose && tmp_split == split && tmp_win < win)) {

                win = tmp_win;
                loose = tmp_loose;
                split = tmp_split;
            }
        }

        return new Rate(win, loose, split);
    }


    public class Rate {

        private double win;
        private double loose;
        private double split;

        private Rate(double win, double loose, double split) {
            this.win = win;
            this.loose = loose;
            this.split = split;
        }

        public double getWin() {
            return win;
        }

        public double getLoose() {
            return loose;
        }

        public double getSplit() {
            return split;
        }
    }
}
