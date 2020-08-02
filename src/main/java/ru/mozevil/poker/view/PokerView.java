package ru.mozevil.poker.view;

import ru.mozevil.poker.model.player.Player;
import ru.mozevil.poker.model.table.Table;
import ru.mozevil.poker.model.table.dealer.Street;
import ru.mozevil.poker.view.com.HandleView;
import ru.mozevil.poker.view.com.TableView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow.Subscription;

import static javax.swing.SpringLayout.*;

public class PokerView extends JFrame implements View {

    private Subscription subscription;

    private Player hero;

    private TableView tableView;
    private HandleView handleView;

    public PokerView() throws HeadlessException { // observer mode
        super("Texas Holdem tournament by mozEvil");

        init();

        showEnvironmentFor(null);
    }

    public PokerView(Player hero) throws HeadlessException { // play for hero mode
        super("Texas Holdem tournament by mozEvil");

        init();

        showEnvironmentFor(hero);
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(816,638);

        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        Container contentPane = getContentPane(); // main container

        tableView = new TableView();
        tableView.clearAll();
        add(tableView);

        layout.putConstraint(NORTH, tableView,0, NORTH, contentPane);
        layout.putConstraint(WEST, tableView,0, WEST , contentPane);
        layout.putConstraint(EAST, tableView,800, WEST , tableView);        // weight
        layout.putConstraint(SOUTH, tableView,500, NORTH , tableView);      // height

        handleView = new HandleView();
        handleView.setVisible(false);
        add(handleView);

        layout.putConstraint(NORTH, handleView,0, SOUTH, tableView);
        layout.putConstraint(WEST, handleView,0, WEST , contentPane);
        layout.putConstraint(EAST, handleView,800, WEST , handleView);        // weight
        layout.putConstraint(SOUTH, handleView,100, NORTH , handleView);      // height

        setResizable(false);
        setVisible(true);
    }

    @Override
    public void showEnvironmentFor(Player player) {
        hero = player;
    }

    @Override
    public void refresh(Table table) {
        // god mode
        if (table.isGodMode()){
            tableView.updateTable(table.getAllSee());
            if (hero != null) {
                handleView.updateHandle(hero.getEnvironment());
            }
            return;
        }
        // showdown mode
        if (table.isOpenViewMode()){
            tableView.updateTable(table.getAllSee());
            return;
        }
        // observer mode
        if (hero == null) {
            tableView.updateTable(table.getObserver());
            return;
        }
        // normal mode
        tableView.updateTable(hero.getEnvironment()); // ставки, пот, карты стола, знак дилера, игроки
        handleView.updateHandle(hero.getEnvironment()); // значения для ставок, кнопки
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("PokerView : GAME STARTING...");
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Table table) {
        // если мы не в openView режиме и не в Observer режиме
        if (!table.isOpenViewMode() && hero != null) {
            // проверяем не выбыл ли из игры hero
            int i = hero.getEnvironment().getHeroIndex();
            if (table.getPlayersArray()[i] == null) {
                //если выбыл, то меняем режим
//            showEnvironmentFor(table.getPlayers().get(0)); // другой активный игрок
                showEnvironmentFor(null); // observer mode
            }
        }

        refresh(table);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("PokerView ERROR");
        t.printStackTrace();
        System.exit(0);
    }

    @Override
    public void onComplete() {
        System.out.println("PokerView: GAME OVER!");
        System.exit(0);
    }
}
