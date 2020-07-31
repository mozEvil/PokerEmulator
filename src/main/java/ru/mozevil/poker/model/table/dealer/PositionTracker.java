package ru.mozevil.poker.model.table.dealer;

import ru.mozevil.poker.model.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PositionTracker {

    private final Player[] players;
    private int btnIndex = 0;

    public PositionTracker(Player[] players) {
        this.players = players;
    }

    /**
     * For 3 - 9 players: first element SB, second - BB, last - BTN.
     * For 2 players: first element BB, second - SB (SB is a BTN).
     * Each call change circle queue for 1 player forward.
    * */
    public List<Player> getNextQueue() {
        btnIndex = nextIndex(btnIndex); // change circle queue forward

        List<Player> result = new ArrayList<>();
        int countQueue = getCountPlayers();
        int index = nextIndex(btnIndex); // get first not null element index

        while (result.size() != countQueue) {
            result.add(players[index]);
            index = nextIndex(index); // get next not null element index
        }

        fillPositionPlayers(result);

        return result;
    }

    /**
     * Fill field Position in Player, important for Strategy.
     */
    private void fillPositionPlayers(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
//            players.get(i).setPosition(getPosition(i));
            players.get(i).setPosition(i);
        }
    }


    /**
     * Create new List, ordered by PreFlop decision queue.
     * Where first element is a next player after BigBlind.
     * SmallBlind - second last element. BigBlind - last element.
     *
     * @return Create a new List. Don't changed argument List.
     * */
    public List<Player> getQueuePreflop(List<Player> activePlayers) {
        if (getCountPlayers() == 2) {
            List<Player> headsUp = new ArrayList<>();
            headsUp.add(activePlayers.get(1)); // index 1 - sb if 2-max
            headsUp.add(activePlayers.get(0)); // index 0 - bb if 2-max
            return headsUp;
        }

        List<Player> queue = new ArrayList<>();
        //if 3-9 max, sb = 0, bb = 1, next index = 2
        for (int i = 2; i < activePlayers.size(); i++) {
            queue.add(activePlayers.get(i));
        }
        queue.add(activePlayers.get(0)); // sb must move second last
        queue.add(activePlayers.get(1)); // bb must move last

        return queue;
    }

    /**
     * Create a new List without Raiser. First element is a player after Raiser. Last element is a player before Raiser.
     * */
    public List<Player> getQueueWithoutRaiser(Player raiser, List<Player> activePlayers) {
        int raiserIndex = activePlayers.indexOf(raiser);
        if (raiserIndex < 0) throw new RuntimeException();

        List<Player> queue = new LinkedList<>();

        for (int i = (raiserIndex + 1); i < activePlayers.size(); i++) {
            queue.add(activePlayers.get(i));
        }
        for (int i = 0; i < raiserIndex; i++) {
            queue.add(activePlayers.get(i));
        }

        return queue;
    }

    public List<Player> getQueueWithoutRaiserAndAlliners(Player raiser, List<Player> activePlayers) {
        int raiserIndex = activePlayers.indexOf(raiser);
        if (raiserIndex < 0) throw new RuntimeException();

        List<Player> queue = new LinkedList<>();

        for (int i = (raiserIndex + 1); i < activePlayers.size(); i++) {//игроки после рейзера
            if (!activePlayers.get(i).isAllin()) {
                queue.add(activePlayers.get(i));
            }
        }
        for (int i = 0; i < raiserIndex; i++) {// игроки перед рейзером
            if (!activePlayers.get(i).isAllin()) {
                queue.add(activePlayers.get(i));
            }
        }

        return queue;
    }

    private int getCountPlayers() {
        int count = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null){
                count++;
            }
        }
        return count;
    }

    public int getButtonIndex() {
        return btnIndex;
    }

    /**
     * Next not null index in players array.
     * */
    private int nextIndex(int i) {
        int next = i + 1;
        if (i == players.length - 1) {
            next = 0;
        }
        if (players[next] == null) {
            return nextIndex(next);
        }
        return next;
    }

    /**
     * Previous not null index in players array.
     * */
    private int previousIndex(int i) {
        int prev = i - 1;
        if (i == 0) {
            prev = players.length - 1;
        }
        if (players[prev] == null) {
            return previousIndex(prev);
        }
        return prev;
    }
}
