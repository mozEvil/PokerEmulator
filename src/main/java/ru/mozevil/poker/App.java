package ru.mozevil.poker;

import ru.mozevil.poker.model.GameFactory;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
//        SwingUtilities.invokeLater(() -> {
//        });
            GameFactory.createGame().startGame();

    }
}
