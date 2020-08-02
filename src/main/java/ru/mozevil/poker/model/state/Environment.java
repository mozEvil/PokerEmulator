package ru.mozevil.poker.model.state;

import org.jetbrains.annotations.NotNull;
import ru.mozevil.poker.model.card.Combination;
import ru.mozevil.poker.model.card.Hand;
import ru.mozevil.poker.model.player.Move;
import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.rules.prizes.Prizes;
import ru.mozevil.poker.model.table.Table;
import ru.mozevil.poker.model.table.dealer.Blinds;
import ru.mozevil.poker.model.table.dealer.Street;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Environment {

    private Table table;
    private Player hero;

    private int heroIndex;

    public Environment(@NotNull Player hero, @NotNull Table table) {
        this.table = table;
        this.hero = hero;
        findHeroIndex();
    }

    private void findHeroIndex() {
        for (int i = 0; i < Table.MAX_PLAYERS_ON_THE_TABLE; i++) {
            if (table.getPlayersArray()[i] == hero) {
                heroIndex = i;
                return;
            }
        }
    }

    public int getHeroIndex() {
        return heroIndex;
    }

    public int getPotSize() {
        return table.getPotSize();
    }

    public Combination getTableCards() {
        return table.getCommunityCards();
    }

    public Blinds getBlinds() {
        return table.getBlinds();
    }

    public int getBb() {
        return table.getBlinds().getBb();
    }

    public int getAnte() {
        return table.getBlinds().getAnte();
    }

    public Street getStreet() {
        return table.getStreet();
    }

    public int getDealerIndex() {
        return table.getPosTracker().getButtonIndex();
    }

    /**
     * @return Only active not null opponents, without hero
     * */
    public List<Opponent> getOpponents() {
        return table.getActivePlayers().stream()
                .filter(p -> p != hero)
                .flatMap(p -> Stream.of(new Opponent(p)))
                .collect(Collectors.toList());
    }

    /**
     * Массив оппонентов в соответсвии с местами за столом
     * Включая Hero, так же обернутого в представление оппонента
     * Элементы массива могут быть null
     * */
    public Opponent[] getOpponentsViewArray() {
        Opponent[] opponents = new Opponent[Table.MAX_PLAYERS_ON_THE_TABLE];
        for (int i = 0; i < opponents.length; i++) {
            Player p = table.getPlayersArray()[i];
            if (p == null) {
                opponents[i] = null;
            } else {
                opponents[i] = new Opponent(p);
            }
        }
        return opponents;
    }

    public int[] getPlayersBets() {
        int[] bets = new int[Table.MAX_PLAYERS_ON_THE_TABLE];
        for (int i = 0; i < bets.length; i++) {
            Player p = table.getPlayersArray()[i];
            if (p == null) {
                bets[i] = 0;
            } else {
                bets[i] = p.getValueChipsInPotOnThisStreet(getStreet());
            }
        }
        return bets;
    }

    public int[] getOppStacks() {
        return  getOpponents().stream()
                .mapToInt(op -> op.getStack() + op.getValueChipsInPot())
                .toArray();
    }

    /**
     * Минимальный стек среди активных игроков на начало раздачи
     * */
    public int getEffectiveStack() {
        return table.getActivePlayers().stream()
                .mapToInt(p -> p.getStack() + p.getValueChipsInPot())
                .min().orElse(0);
    }

    /**
     * Максимальный стек среди активных игроков на начало раздачи
     * */
    public int getChipLeaderStack() {
        return table.getActivePlayers().stream()
                .mapToInt(p -> p.getStack() + p.getValueChipsInPot())
                .max().orElse(0);
    }

    public String getHeroName() {
        return hero.getName();
    }

    public int getHeroStack() {
        return hero.getStack();
    }

    public int getHeroValueChipsInPot() {
        return hero.getValueChipsInPot();
    }

    public Hand getHeroHand() {
        return hero.getHand();
    }

    public Move getHeroLastMove() {
        return hero.getMove();
    }

    public boolean isPreflop() {
        return getStreet() == Street.PREFLOP ||  getStreet() == Street.ANTE;
    }

    public boolean heroIsActive() {
        return hero.isActive();
    }

    public boolean heroIsMoving() {
        return hero.isMoving();
    }

    public boolean heroIsWinner() {
        return hero.isWinner();
    }

    public int getHeroWinSize() {
        return hero.getWinSize();
    }

    public int getValueForCall(){
        int maxRaise = getMaxRaise();

        // все чек/фолд, еще никто не делал рейз
        if (maxRaise == 0) {
            return 0;
        }

        int value = maxRaise - hero.getValueChipsInPotOnThisStreet(getStreet());

        return checkStack(value);
    }

    // minRaise = [lastBet + lastBet - secondLastBet]
    public int getValueForMinRaise() {

        if (getValueForCall() == getHeroStack()) {
            return 0;
        }

        // если кроме hero все в оллине, то рейз = 0
        if (getCountNotAllinOpps() == 0) {
            return 0;
        }

        int bb = getBb();
        int lastBet = getMaxRaise();
        int heroChipsInPot = hero.getValueChipsInPotOnThisStreet(getStreet());

        // небыло рейзов на префлопе
        if (isPreflop() && lastBet <= bb) {
            return checkStack(bb * 2 - heroChipsInPot);
        }

        // на постфлопе не было рейзов или не хватило на нормальный рейз
        if (lastBet < bb) {
            return checkStack(bb - heroChipsInPot);
        }

        int preLastBet = getPreLastBet();
        int value = lastBet * 2 - preLastBet - heroChipsInPot;

        return checkStack(value);
    }

    private int checkStack(int value) {
        if (value > getHeroStack()) {
            return getHeroStack();
        } else {
            return value;
        }
    }

    private int getCountNotAllinOpps() {
        return (int) getOpponents().stream()
                .flatMap(op -> Stream.of(op.isAllin()))
                .filter(Predicate.isEqual(false))
                .count();
    }

    private int getPreLastBet() {
        return table.getActivePlayers().stream()
                .flatMap(p -> Stream.of(p.getValueChipsInPotOnThisStreet(getStreet())))
                .sorted(Comparator.reverseOrder())
                .flatMapToInt(IntStream::of)
                .distinct().skip(1)
                .findFirst().orElse(0);
    }

    // максимальная ставка на этой улице
    private int getMaxRaise() {
        return table.getActivePlayers().stream()
                .flatMapToInt(p -> IntStream.of(p.getValueChipsInPotOnThisStreet(getStreet())))
                .max().orElse(0);
    }

    private int getMaximumValueChipsInPot() {
        int value = hero.getValueChipsInPot();
        for (Opponent op : getOpponents()) {
            if (op.getValueChipsInPot() > value) {
                value = op.getValueChipsInPot();
            }
        }
        return value;
    }

    public void heroPressFold() {
        //проверка для тесовой стратегии, потом удалить
        int callSize = getValueForCall();
        if (callSize == 0) {
             heroPressCheck();
        } else {
            hero.setMove(new Move(Move.Decision.FOLD));
        }
    }

    public void heroPressCheck() {
        hero.setMove(new Move(Move.Decision.CHECK));
    }

    // для тестовой стратегии, потом удалить
    public void heroPressCall() {
        int callSize = getValueForCall();

        if (callSize == 0) {
            heroPressCheck();
        } else {
            hero.setMove(new Move(Move.Decision.CALL, callSize));
        }
    }

    public void heroPressCall(int callSize) {
        hero.setMove(new Move(Move.Decision.CALL, callSize));
    }

    // для тестовой стратегии, потом удалить
    public void heroPressRaise() {
        int callSize = getValueForCall();
        int raiseSize = getValueForMinRaise();

        if (raiseSize == 0) {
            hero.setMove(new Move(Move.Decision.CALL, callSize));
        } else {
            hero.setMove(new Move(Move.Decision.RAISE, raiseSize));
        }
    }

    public void heroPressRaise(int raiseSize) {
        hero.setMove(new Move(Move.Decision.RAISE, raiseSize));
    }


    public Prizes getPrizes() {
        return table.getPrizes();
    }
}
