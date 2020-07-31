package ru.mozevil.poker.view.com;

import ru.mozevil.poker.model.card.Combination;
import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.state.AllSeeingEye;
import ru.mozevil.poker.model.state.Environment;
import ru.mozevil.poker.model.state.Observer;
import ru.mozevil.poker.model.state.Opponent;
import ru.mozevil.poker.model.table.Table;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SpringLayout.*;

public class TableView extends JPanel {

    public final static int MAX_PLAYERS = Table.MAX_PLAYERS_ON_THE_TABLE;

    private PlayerView[] players = new PlayerView[MAX_PLAYERS];
    private PotView pot = new PotView();
    private CardsView cards = new CardsView();
    private JLabel[] bets = new JLabel[MAX_PLAYERS];
    private JLabel[] dealers = new JLabel[MAX_PLAYERS];

    private ImageIcon btnImage = new ImageIcon("src/main/resources/images/table/dealer.png");


    public TableView() {
        setOpaque(true);
        init();
    }

    private void init() {

        for (int i = 0; i < MAX_PLAYERS; i++) {
            players[i] = new PlayerView();
//            players[i].setPlayerName("" + i);

            bets[i] = new JLabel("");
            bets[i].setHorizontalAlignment(JLabel.CENTER);

            dealers[i] = new JLabel(btnImage);
            dealers[i].setHorizontalAlignment(JLabel.CENTER);

            add(players[i]);
            add(bets[i]);
            add(dealers[i]);
        }

        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setSize(800, 500);

        // 0
        layout.putConstraint(NORTH, players[0], 400, NORTH, this);
        layout.putConstraint(WEST, players[0], 350, WEST, this);
        layout.putConstraint(EAST, players[0], 100, WEST, players[0]); // weight
        layout.putConstraint(SOUTH, players[0], 100, NORTH, players[0]); // height

        layout.putConstraint(NORTH, bets[0], -60, NORTH, players[0]);
        layout.putConstraint(WEST, bets[0], 375, WEST, this);
        layout.putConstraint(EAST, bets[0], 50, WEST, bets[0]); // weight
        layout.putConstraint(SOUTH, bets[0], 15, NORTH, bets[0]); // height

        layout.putConstraint(NORTH, dealers[0], -30, NORTH, players[0]);
        layout.putConstraint(WEST, dealers[0], 390, WEST, this);
        layout.putConstraint(EAST, dealers[0], 20, WEST, dealers[0]); // weight
        layout.putConstraint(SOUTH, dealers[0], 20, NORTH, dealers[0]); // height

        // 1
        layout.putConstraint(NORTH, players[1], 375, NORTH, this);
        layout.putConstraint(WEST, players[1], 175, WEST, this);
        layout.putConstraint(EAST, players[1], 100, WEST, players[1]); // weight
        layout.putConstraint(SOUTH, players[1], 100, NORTH, players[1]); // height

        layout.putConstraint(NORTH, bets[1], -50, NORTH, players[1]);
        layout.putConstraint(WEST, bets[1], 230, WEST, this);
        layout.putConstraint(EAST, bets[1], 50, WEST, bets[1]); // weight
        layout.putConstraint(SOUTH, bets[1], 15, NORTH, bets[1]); // height

        layout.putConstraint(NORTH, dealers[1], -25, NORTH, players[1]);
        layout.putConstraint(WEST, dealers[1], 220, WEST, this);
        layout.putConstraint(EAST, dealers[1], 20, WEST, dealers[1]); // weight
        layout.putConstraint(SOUTH, dealers[1], 20, NORTH, dealers[1]); // height

        // 2
        layout.putConstraint(NORTH, players[2], 285, NORTH, this);
        layout.putConstraint(WEST, players[2], 15, WEST, this);
        layout.putConstraint(EAST, players[2], 100, WEST, players[2]); // weight
        layout.putConstraint(SOUTH, players[2], 100, NORTH, players[2]); // height

        layout.putConstraint(NORTH, bets[2], -10, NORTH, players[2]);
        layout.putConstraint(WEST, bets[2], 140, WEST, this);
        layout.putConstraint(EAST, bets[2], 50, WEST, bets[2]); // weight
        layout.putConstraint(SOUTH, bets[2], 15, NORTH, bets[2]); // height

        layout.putConstraint(NORTH, dealers[2], 20, NORTH, players[2]);
        layout.putConstraint(WEST, dealers[2], 125, WEST, this);
        layout.putConstraint(EAST, dealers[2], 20, WEST, dealers[2]); // weight
        layout.putConstraint(SOUTH, dealers[2], 20, NORTH, dealers[2]); // height

        // 3
        layout.putConstraint(NORTH, players[3], 115, NORTH, this);
        layout.putConstraint(WEST, players[3], 15, WEST, this);
        layout.putConstraint(EAST, players[3], 100, WEST, players[3]); // weight
        layout.putConstraint(SOUTH, players[3], 100, NORTH, players[3]); // height

        layout.putConstraint(NORTH, bets[3], 80, NORTH, players[3]);
        layout.putConstraint(WEST, bets[3], 170, WEST, this);
        layout.putConstraint(EAST, bets[3], 50, WEST, bets[3]); // weight
        layout.putConstraint(SOUTH, bets[3], 15, NORTH, bets[3]); // height

        layout.putConstraint(NORTH, dealers[3], 80, NORTH, players[3]);
        layout.putConstraint(WEST, dealers[3], 125, WEST, this);
        layout.putConstraint(EAST, dealers[3], 20, WEST, dealers[3]); // weight
        layout.putConstraint(SOUTH, dealers[3], 20, NORTH, dealers[3]); // height

        // 4
        layout.putConstraint(NORTH, players[4], 15, NORTH, this);
        layout.putConstraint(WEST, players[4], 175, WEST, this);
        layout.putConstraint(EAST, players[4], 100, WEST, players[4]); // weight
        layout.putConstraint(SOUTH, players[4], 100, NORTH, players[4]); // height

        layout.putConstraint(NORTH, bets[4], 30, SOUTH, players[4]);
        layout.putConstraint(WEST, bets[4], 260, WEST, this);
        layout.putConstraint(EAST, bets[4], 50, WEST, bets[4]); // weight
        layout.putConstraint(SOUTH, bets[4], 15, NORTH, bets[4]); // height

        layout.putConstraint(NORTH, dealers[4], 10, SOUTH, players[4]);
        layout.putConstraint(WEST, dealers[4], 225, WEST, this);
        layout.putConstraint(EAST, dealers[4], 20, WEST, dealers[4]); // weight
        layout.putConstraint(SOUTH, dealers[4], 20, NORTH, dealers[4]); // height

        // 5
        layout.putConstraint(NORTH, players[5], 15, NORTH, this);
        layout.putConstraint(WEST, players[5], 525, WEST, this);
        layout.putConstraint(EAST, players[5], 100, WEST, players[5]); // weight
        layout.putConstraint(SOUTH, players[5], 100, NORTH, players[5]); // height

        layout.putConstraint(NORTH, bets[5], 30, SOUTH, players[5]);
        layout.putConstraint(WEST, bets[5], 500, WEST, this);
        layout.putConstraint(EAST, bets[5], 50, WEST, bets[5]); // weight
        layout.putConstraint(SOUTH, bets[5], 15, NORTH, bets[5]); // height

        layout.putConstraint(NORTH, dealers[5], 10, SOUTH, players[5]);
        layout.putConstraint(WEST, dealers[5], 560, WEST, this);
        layout.putConstraint(EAST, dealers[5], 20, WEST, dealers[5]); // weight
        layout.putConstraint(SOUTH, dealers[5], 20, NORTH, dealers[5]); // height

        // 6
        layout.putConstraint(NORTH, players[6], 115, NORTH, this);
        layout.putConstraint(WEST, players[6], 685, WEST, this);
        layout.putConstraint(EAST, players[6], 100, WEST, players[6]); // weight
        layout.putConstraint(SOUTH, players[6], 100, NORTH, players[6]); // height

        layout.putConstraint(NORTH, bets[6], -10, SOUTH, players[6]);
        layout.putConstraint(WEST, bets[6], 570, WEST, this);
        layout.putConstraint(EAST, bets[6], 50, WEST, bets[6]); // weight
        layout.putConstraint(SOUTH, bets[6], 15, NORTH, bets[6]); // height

        layout.putConstraint(NORTH, dealers[6], -20, SOUTH, players[6]);
        layout.putConstraint(WEST, dealers[6], 655, WEST, this);
        layout.putConstraint(EAST, dealers[6], 20, WEST, dealers[6]); // weight
        layout.putConstraint(SOUTH, dealers[6], 20, NORTH, dealers[6]); // height

        // 7
        layout.putConstraint(NORTH, players[7], 285, NORTH, this);
        layout.putConstraint(WEST, players[7], 685, WEST, this);
        layout.putConstraint(EAST, players[7], 100, WEST, players[7]); // weight
        layout.putConstraint(SOUTH, players[7], 100, NORTH, players[7]); // height

        layout.putConstraint(NORTH, bets[7], 0, NORTH, players[7]);
        layout.putConstraint(WEST, bets[7], -100, WEST, players[7]);
        layout.putConstraint(EAST, bets[7], 50, WEST, bets[7]); // weight
        layout.putConstraint(SOUTH, bets[7], 15, NORTH, bets[7]); // height

        layout.putConstraint(NORTH, dealers[7], 30, NORTH, players[7]);
        layout.putConstraint(WEST, dealers[7], -30, WEST, players[7]);
        layout.putConstraint(EAST, dealers[7], 20, WEST, dealers[7]); // weight
        layout.putConstraint(SOUTH, dealers[7], 20, NORTH, dealers[7]); // height

        // 8
        layout.putConstraint(NORTH, players[8], 375, NORTH, this);
        layout.putConstraint(WEST, players[8], 525, WEST, this);
        layout.putConstraint(EAST, players[8], 100, WEST, players[8]); // weight
        layout.putConstraint(SOUTH, players[8], 100, NORTH, players[8]); // height

        layout.putConstraint(NORTH, bets[8], -60, NORTH, players[8]);
        layout.putConstraint(WEST, bets[8], -30, WEST, players[8]);
        layout.putConstraint(EAST, bets[8], 50, WEST, bets[8]); // weight
        layout.putConstraint(SOUTH, bets[8], 15, NORTH, bets[8]); // height

        layout.putConstraint(NORTH, dealers[8], -30, NORTH, players[8]);
        layout.putConstraint(WEST, dealers[8], 30, WEST, players[8]);
        layout.putConstraint(EAST, dealers[8], 20, WEST, dealers[8]); // weight
        layout.putConstraint(SOUTH, dealers[8], 20, NORTH, dealers[8]); // height

        add(pot);

        layout.putConstraint(NORTH, pot, 20, NORTH, this);
        layout.putConstraint(WEST, pot, 350, WEST, this);
        layout.putConstraint(EAST, pot, 97, WEST, pot); // weight
        layout.putConstraint(SOUTH, pot, 90, NORTH, pot); // height

        add(cards);

        layout.putConstraint(NORTH, cards, 200, NORTH, this);
        layout.putConstraint(WEST, cards, 275, WEST, this);
        layout.putConstraint(EAST, cards, 250, WEST, cards); // weight
        layout.putConstraint(SOUTH, cards, 70, NORTH, cards); // height


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.green);
        g2d.fillOval(30, 30, getWidth() - 60, getHeight() - 60);

    }

    public void setPot(int value) {
        pot.setPotSize(value);
    }

    public void setCards(Combination combo) {
        cards.setCards(combo);
    }

    public void clearCards() {
        cards.clear();
    }

    public void setDealer(int index) {
        clearDealers();
        dealers[index].setVisible(true);
    }

    public void clearDealers(){
        for (int i = 0; i < MAX_PLAYERS; i++) {
            dealers[i].setVisible(false);
        }
    }

    public void setPlayerBet(int index, int value) {
        String result = value == 0 ? "" : "" + value;
        bets[index].setText(result);
    }

    public void clearBets(){
        for (int i = 0; i < MAX_PLAYERS; i++) {
            bets[i].setText("");
        }
    }

    private void setPlayersBets(int[] playersBets) {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            String value = playersBets[i] == 0 ? "" : "" + playersBets[i];
            bets[i].setText(value);
        }
    }

    public PlayerView getPlayerView(int index) {
        return players[index];
    }

    public void clearAll() {
        for (int i = 0; i < MAX_PLAYERS; i++) {
            bets[i].setText("");
        }
        clearCards();
        clearBets();
        clearDealers();
        setPot(0);
    }

    public void updateTable(Environment env) {
        // обновить пот, карты стола, знак диллера, ставки игроков, состояния ироков
        setPot(env.getPotSize());
        setCards(env.getTableCards());
        setDealer(env.getDealerIndex());
        setPlayersBets(env.getPlayersBets());
        updatePlayers(env);
        if (env.heroIsWinner()) {
            setPlayerBet(env.getHeroIndex(), env.getHeroWinSize());
        }
    }

    private void updatePlayers(Environment env) {
        // обновить состояние всех игроков, как то понять где позиция Героя а где Оппонентов
        Opponent[] opps = env.getOpponentsViewArray();
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (i == env.getHeroIndex()) {
                players[i].updateHero(env);
            } else {
                players[i].updateOpponent(opps[i]);
            }
        }
    }

    public void updateTable(Observer obs) {
        setPot(obs.getPotSize());
        setCards(obs.getTableCards());
        setDealer(obs.getDealerIndex());
        setPlayersBets(obs.getPlayersBets());
        updatePlayers(obs);
    }

    private void updatePlayers(Observer obs) {
        Opponent[] opps = obs.getAllOpponents();
        for (int i = 0; i < MAX_PLAYERS; i++) {
            players[i].updateOpponent(opps[i]);

            if (opps[i] != null && opps[i].isWinner()) {
                setPlayerBet(i, opps[i].getWinSize());
            }
        }
    }

    public void updateTable(AllSeeingEye allSee) {
        setPot(allSee.getPotSize());
        setCards(allSee.getTableCards());
        setDealer(allSee.getDealerIndex());
        setPlayersBets(allSee.getPlayersBets());
        updatePlayers(allSee);
    }

    private void updatePlayers(AllSeeingEye allSee) {
        Player[] ps = allSee.getAllPlayers();
        for (int i = 0; i < MAX_PLAYERS; i++) {
            if (ps[i] == null) {
                players[i].setNullPlayer();
            } else {
                players[i].updateHero(ps[i].getEnvironment());

                if (ps[i].isWinner()) {
                    setPlayerBet(i, ps[i].getWinSize());
                }
            }
        }
    }
}
