package ru.mozevil.poker.model;

import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.rules.Rules;
import ru.mozevil.poker.model.rules.prizes.Prizes;
import ru.mozevil.poker.model.table.Table;
import ru.mozevil.poker.view.View;

import java.util.concurrent.SubmissionPublisher;

public class Game {

    private int hand;

    private SubmissionPublisher<Table> publisher;

    private Table table;
    private View view;

    private Rules rules;

    public void setTable(Table table) {
        this.table = table;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setRules(Rules rules) {
        this.rules = rules;
    }

    public void startGame() {
        startObserving();
        applyRules();
        showStartGameInfo();

        while (table.getCountPlayers() > 1) {
            hand++;
//            System.out.println("Hand: " + hand + " ");
            table.startNewHand();
        }

        showGameOverInfo();
        stopObserving();
    }

    private void startObserving() {
        if (view == null) return;

        publisher = new SubmissionPublisher<>();
        publisher.subscribe(view);
        table.setGame(this);
    }

    private void stopObserving() {
        if (publisher != null) {
            publisher.close();
        }
    }

    private void applyRules() {
        Thread rulesThread = new Thread(rules);
        rulesThread.setDaemon(true);
        rulesThread.start();
    }

    private void showStartGameInfo() {
        System.out.println("----------------------");
        int time = 3;
        while (time > 0) {
            System.out.println("Game will start in " + time + " seconds");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time--;

        }
        System.out.println("----------------------");
        System.out.println("Game Starting!");

    }

    private void showGameOverInfo() {
        System.out.println("-----------------------");
        System.out.println("GAME OVER");
        System.out.println("WINNER - " + table.getPlayers().get(0).getName());
        System.out.println("Total hands played: " + hand);
        System.out.println("-----------------------");
        System.out.println("Total pot: " + table.getPlayers().get(0).getStack());
    }

    public void updateUI(Table table) {
        if (publisher != null) {
            publisher.submit(table);
        }
    }

    public void setHero(Player hero) {
        view.showEnvironmentFor(hero);
    }

    public Prizes getPrizes() {
        return rules.getPrizes();
    }
}
