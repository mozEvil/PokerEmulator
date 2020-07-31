package ru.mozevil.poker.model.table;

import org.jetbrains.annotations.NotNull;
import ru.mozevil.poker.eva.Evaluator;
import ru.mozevil.poker.eva.MozEvaluator;
import ru.mozevil.poker.model.Game;
import ru.mozevil.poker.model.card.Combination;
import ru.mozevil.poker.model.card.Deck;
import ru.mozevil.poker.model.card.Hand;
import ru.mozevil.poker.model.player.Move;
import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.rules.prizes.Prizes;
import ru.mozevil.poker.model.state.AllSeeingEye;
import ru.mozevil.poker.model.state.Observer;
import ru.mozevil.poker.model.table.dealer.Blinds;
import ru.mozevil.poker.model.table.dealer.PositionTracker;
import ru.mozevil.poker.model.table.dealer.Pot;
import ru.mozevil.poker.model.table.dealer.Street;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Table {

    public static final int MAX_PLAYERS_ON_THE_TABLE = 9;
//todo street remake
    public static final int ANTE = -1;
    public static final int PREFLOP = 0;
    public static final int FLOP = 1;
    public static final int TURN = 2;
    public static final int RIVER = 3;

    private long mills = 0;
    private int timeForDecision = 15;

    private int countPlayers = 0;
    private Player[] players = new Player[MAX_PLAYERS_ON_THE_TABLE];

    //todo Dealer
//    private Dealer dealer = new Dealer(this);

    private Vector<Pot> pots = new Vector<>();
    private Set<Player> potPlayers = new HashSet<>();
    private Map<Pot, Set<Player>> potMap = new HashMap<>();

    private Street street = Street.PREFLOP;
    private volatile Combination communityCards = new Combination();

    private Blinds blinds = new Blinds(); // обновляется при старте новой раздачи
    private volatile Blinds newBlinds; // обновляется из вне

    private Deck deck = new Deck();
    private PositionTracker posTracker = new PositionTracker(players);
    private Evaluator evaluator = new MozEvaluator();
    private List<Player> activePlayers;
    //===========================================
    private Game game;

    private Observer observer;
    private AllSeeingEye allSee;
    private boolean openViewMode = false;
    private boolean godMode = false;
    private boolean debugMode = false;

    public Observer getObserver() {
        if (observer == null) {
            observer = new Observer(this);
        }
        return observer;
    }

    public AllSeeingEye getAllSee() {
        if (allSee == null) {
            allSee = new AllSeeingEye(this);
        }
        return allSee;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private void notifyView() {
        if (game != null) {
            game.updateUI(this);
        }
    }

    private void notifyAndWait(long mills) {
        notifyView();
        if (mills > 0) {
            try {
                Thread.sleep(mills);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSleepTime(long mills) {
        this.mills = mills;
    }

    public void setTimeForDecision(int timeForDecision) {
        this.timeForDecision = timeForDecision;
    }

    private void setOpenViewMode(boolean value) {
        this.openViewMode = value;
        notifyView();
    }

    public boolean isOpenViewMode() {
        return openViewMode;
    }

    public boolean isGodMode() {
        return godMode;
    }

    public void setGodMode(boolean valaue) {
        godMode = valaue;
        setOpenViewMode(valaue);
    }

    public void setDebugMode(boolean value) {
        this.debugMode = value;
    }

    public void addPlayer(Player player) {
        if (getPlayers().contains(player)) {
            System.out.println("Player already exist!");
            return;
        }
        if (getCountPlayers() == MAX_PLAYERS_ON_THE_TABLE) {
            System.out.println("Table is full!");
            return;
        }
        players[getCountPlayers()] = player;
        countPlayers++;
    }

    /**
     * @return All active players in current Hand, where players have not FOLD decision.
     * */
    public List<Player> getActivePlayers() {
        return activePlayers;
    }

    /**
     * @return Create a new List without null links and not ordered by position.
     * */
    public List<Player> getPlayers() {

        return Arrays.stream(players).filter(Objects::nonNull).collect(Collectors.toList());

//        List<Player> result = new ArrayList<>();
//        for (Player p : players) {
//            if (p != null) {
//                result.add(p);
//            }
//        }
//        return result;
    }

    public Player[] getPlayersArray() {
        return players;
    }

    public int getCountPlayers() {
        return countPlayers;
    }

    public Combination getCommunityCards() {
        return communityCards;
    }

    public int getPotSize() {
//        return pots.stream().flatMapToInt(p -> IntStream.of(p.getSize())).sum();
        int pot = 0;
        for (Pot p : pots) {
            pot += p.getSize();
        }
        return pot;
    }

    private Pot getPot() {
        // возвращает текущий пот
        return pots.lastElement();
    }

    private void receiveChipsFromPlayer(@NotNull Player player) {
        player.transferChipsToPot(street);
        getPot().deposit(player.getMove().getSize());
        potPlayers.add(player);
    }

    private void payoutChipsToPlayerFromPot(int value , @NotNull Player player, @NotNull Pot pot) {
        player.transferChipsToStack(value);
        pot.withdrawal(value);
    }

    private void checkForSidePot() {
        // убираем всех кто фолд
        clearPotPlayers();

        // проверяем оставшихся на оллин
        boolean wasAllin = potPlayers.stream().anyMatch(Player::isAllin);

        if (!wasAllin) {
            return;
        }

        // вычисляем сумму сайд пота
        List<Player> pushers = potPlayers.stream(
        ).filter(Player::isAllin).collect(Collectors.toList());

        int minValue = pushers.stream().flatMapToInt(p -> IntStream.of(
                p.getValueChipsInPotOnThisStreet(street))).min().orElse(0);

        int sidePot = getPlayers().stream().flatMapToInt(p -> IntStream.of(
                p.getValueChipsInPotOnThisStreet(street))
        ).reduce(0, (left, right) -> {
//            left + right - minValue
            if (right > minValue) {
                return left + right - minValue;
            } else {
                return left;
            }
        });

        // в сайд поте нет овер бетов, все уровняли оллин
        if (sidePot == 0) {
            pots.add(new Pot());
            potPlayers = new HashSet<>();
            potMap.put(getPot(), potPlayers);

            return;
        }

        HashSet<Player> sidePotPlayers = potPlayers.stream().filter(
                player -> player.getValueChipsInPotOnThisStreet(street) > minValue
        ).collect(Collectors.toCollection(HashSet::new));

        // забрал сайд пот без вскрытия
        if (sidePotPlayers.size() == 1) {
            Player player = sidePotPlayers.stream().findFirst().get();
            payoutChipsToPlayerFromPot(sidePot, player, getPot());
            notifyAndWait(mills);

            // добавляем нулевой сайд пот для корректной работы алгоритма на следующих улицах
            pots.add(new Pot());
            potPlayers = new HashSet<>();
            potMap.put(getPot(), potPlayers);

            return;
        }

        int mainPot = getPot().getSize() - sidePot;

        // устанавливаем мейн пот
        getPot().setSize(mainPot);
        // добавляем сайд пот
        pots.add(new Pot(sidePot));
        potPlayers = sidePotPlayers;
        potMap.put(getPot(), potPlayers);

        // на случай, если в сайд поте есть еще олл-ин
        checkForSidePot();
    }

    private void clearPotPlayers() {
        potMap.forEach((pot, set) -> set.removeIf(Player::isFold));
    }

    public Blinds getBlinds() {
        return blinds;
    }

    public void setBlinds(@NotNull Blinds blinds) {
        this.newBlinds = blinds;
    }

    public Street getStreet() {
        return street;
    }

    public PositionTracker getPosTracker() {
        return posTracker;
    }

    public void startNewHand() {

        blindsUpdate();
        initPot();

        activePlayers = posTracker.getNextQueue();

        dealPreflop();

        if (activePlayers.size() > 1) {
            checkForSidePot();
            dealFlop();

            if (activePlayers.size() > 1) {
                checkForSidePot();
                dealTurn();

                if (activePlayers.size() > 1) {
                    checkForSidePot();
                    dealRiver();

                    if (activePlayers.size() > 1) {
                        checkForSidePot();
                        dealShowdown();
                    }
                }
            }
        }

        if (activePlayers.size() == 1) {
            dealWithoutShowdown();
        }

        clear();
    }

    private void blindsUpdate() {
        if (newBlinds != null) {
            blinds = newBlinds;
            newBlinds = null;
        }
    }

    private void initPot() {
        pots.add(new Pot());
        potPlayers = new HashSet<>();
        potMap.put(getPot(), potPlayers);
    }

    private void dealPreflop() {
        street = Street.ANTE;
        notifyAndWait(mills);

        for (Player p : activePlayers) {
            Hand hand = new Hand(deck.popCard(), deck.popCard());
            p.setHand(hand);
            notifyAndWait(mills/2);
        }

        dealAnte();

        street = Street.PREFLOP;
        notifyAndWait(mills);

        dealBlinds();

        dealTrades(posTracker.getQueuePreflop(activePlayers));
    }

    private void dealAnte() {

        int ante = blinds.getAnte();

        if (ante > 0) {
            for (Player player : activePlayers) {
                checkStackAndReceiveChips(ante, player);
            }
            checkForSidePot();
        }

        notifyAndWait(mills*2);
    }

    private void dealBlinds() {

        int sbSize = blinds.getSb();
        int bbSize = blinds.getBb();

        Player sbPlayer;
        Player bbPlayer;

        if (getCountPlayers() == 2) {
            sbPlayer = activePlayers.get(1); // index 1 - sb if 2 players
            bbPlayer = activePlayers.get(0); // index 0 - bb if 2 players
        } else {
            sbPlayer = activePlayers.get(0); // index 0 - sb if 3+ players
            bbPlayer = activePlayers.get(1); // index 1 - bb if 3+ players
        }

        checkStackAndReceiveChips(sbSize, sbPlayer);
        notifyAndWait(mills/2);

        checkStackAndReceiveChips(bbSize, bbPlayer);
        notifyAndWait(mills/2);
    }

    private void checkStackAndReceiveChips(int value, @NotNull Player player) {
        int stack = player.getStack();
        if (value > stack) {
            player.setMove(new Move(stack));
        } else {
            player.setMove(new Move(value));
        }
        receiveChipsFromPlayer(player);
    }


    /**
     * Тогри идут до тех пор пока все участники не уровняют самый большой рейз или
     * все кроме одного не выкинут карты в пас или не вложат все свои фишки.
     * */
    private void dealTrades(@NotNull List<Player> tradeQueue) {
        notifyAndWait(mills);

        if (!isCanMove()) {
            setOpenViewMode(true);
            return;
        }

        Iterator<Player> it = tradeQueue.iterator();
        while (it.hasNext()) {

            Player player = it.next();

            if (player.isAllin()) { // игроки в Allin пропускают ход
                continue;
            }

            if (debugMode) {
                game.setHero(player); // ручной ввод за всех игроков
                notifyAndWait(100);
                // чтобы работало, должна быть выставлена соответсвующая стратегия у всех игроков
            }

            player.doMove();    // просим игрока сделать свой ход

            notifyAndWait(100);

            int time = timeForDecision;
            while (player.isMoving()) {// ждем timeForDecision секунд, пока игрок не сделает свой ход
                if (!debugMode) { // при дебаг моде нет ограничения времени на ход
                    if (time <= 5) {
                        System.out.println("Time is left: " + time);
                    }
                    if (time == 0) {
                        System.out.println("Time is over!");
                        player.setMove(new Move(Move.Decision.FOLD));
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    time--;
                }
            }

            if (player.isFold()) {
                if (tradeQueue == activePlayers) { //если у аргумента ссылка на activePlayers
                    it.remove();                  // то удаляем безопасным способом
                } else {
                    activePlayers.remove(player);
                }

                notifyAndWait(mills);

                if (activePlayers.size() == 1) { // если все FOLD
                    return;
                }

                continue;
            }

            if (player.isCheck()) {
                notifyAndWait(mills);
                continue;
            }

            receiveChipsFromPlayer(player); // if Call||Raise перводим фишки в банк
            notifyAndWait(mills);

            if (player.isRaise()) { //if Raise - start new tradeQueue without raiser
                dealTrades(posTracker.getQueueWithoutRaiser(player, activePlayers));
                return;
            }
        }
    }

    private boolean isCanMove() {
        int countActiveNotAllin = 0;
        Player lastNotAllinPlayer = null;

        for (Player p : activePlayers) {
            if (!p.isAllin()) {
                countActiveNotAllin++;
                lastNotAllinPlayer = p;
            }
        }

        if (countActiveNotAllin > 1) {
            return true;
        }

        if (countActiveNotAllin == 1) {

            int oppsMaxValue = lastNotAllinPlayer.getEnvironment().getOpponents().stream()
                    .flatMapToInt(p -> IntStream.of(p.getValueChipsInPot()))
                    .max().orElse(0);

            int heroValue = lastNotAllinPlayer.getValueChipsInPot();

            // если hero есть что колить то true
            return oppsMaxValue - heroValue > 0;

        }
        // все в ALLINе
        return false;
    }

    private void dealFlop() {
        street = Street.FLOP;
        notifyAndWait(mills);

        communityCards.addCard(deck.popCard());
        communityCards.addCard(deck.popCard());
        communityCards.addCard(deck.popCard());

        notifyAndWait(mills);

        dealTrades(activePlayers);
    }

    private void dealTurn() {
        street = Street.TURN;
        notifyAndWait(mills);

        communityCards.addCard(deck.popCard());
        notifyAndWait(mills);

        dealTrades(activePlayers);
    }

    private void dealRiver() {
        street = Street.RIVER;
        notifyAndWait(mills);

        communityCards.addCard(deck.popCard());
        notifyAndWait(mills);

        dealTrades(activePlayers);
    }

    private void dealShowdown() {
        street = Street.SHOWDOWN;
        notifyAndWait(mills);

        for (Pot pot : pots) {
            Set<Player> playersInPot = potMap.get(pot);

            long bestComboStrength = 0;
            int countWinners = 1;

            // вычисляем лучшую комбинацию для каждого игрока
            for (Player p : playersInPot) {
                Combination combo;
                if (p.getCombination() == null) {
                    combo = evaluator.getBestCombo(p.getHand(), getCommunityCards());
                    p.setCombination(combo);
                } else {
                    combo = p.getCombination();
                }

                // определяем комбинацию победителя и количество победителей
                if (combo.getStrength() > bestComboStrength) {
                    bestComboStrength = combo.getStrength();
                    countWinners = 1;
                } else if (combo.getStrength() == bestComboStrength) {
                    countWinners++;
                }
            }

            int winSize = pot.getSize() / countWinners;

            // определяем победителей и выдаем им их часть пота
            for (Player player : playersInPot) {
                if (player.getCombination().getStrength() == bestComboStrength) {
                    notifyAndWait(mills*3);
                    payoutChipsToPlayerFromPot(winSize, player, pot);
                    player.setWinSize(winSize);
                    notifyAndWait(mills*2);
                }
            }

            // если в поте остались фишки из за дробного деления,
            // то отдать их одному из победителей с наименьшим стеком
            if (pot.getSize() > 0) {

                long tempBestCombo = bestComboStrength;

                Player minStackPlayer = playersInPot.stream().filter(
                        p -> p.getCombination().getStrength() == tempBestCombo
                ).min(Comparator.comparingInt(Player::getStack)).get();

                payoutChipsToPlayerFromPot(pot.getSize(), minStackPlayer, pot);
            }

            notifyAndWait(mills*4);
        }

        notifyAndWait(mills);
    }

    private void dealWithoutShowdown() {
        // победа без вскрытия, игрок один, пот один, оллинов нет
        payoutChipsToPlayerFromPot(getPotSize(), activePlayers.get(0), getPot());

        notifyAndWait(mills);
    }

    private void clear() {
        street = Street.ANTE;
        communityCards = new Combination();
        pots = new Vector<>();
        potMap = new HashMap<>();
        potPlayers = null;
        deck = new Deck();
        clearPlayers();
        setOpenViewMode(godMode);
        notifyAndWait(mills);
    }

    private void clearPlayers() {
        for (int i = 0; i < MAX_PLAYERS_ON_THE_TABLE; i++) {
            if (players[i] != null) {
                if (players[i].getStack() <= 0) {
                    removePlayer(i);
                } else {
                    players[i].clear();
                }
            }
        }
    }

    private void removePlayer(int pos) {
        players[pos] = null;
        countPlayers--;
    }

    public Prizes getPrizes() {
        return game.getPrizes();
    }
}