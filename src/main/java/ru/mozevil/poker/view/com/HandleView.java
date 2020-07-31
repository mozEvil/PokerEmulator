package ru.mozevil.poker.view.com;

import ru.mozevil.poker.model.state.Environment;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SpringLayout.*;

public class HandleView extends JPanel {

    private final static int PREFLOP = 0;
    private final static int POSTFLOP = 1;

    private Environment environment;

    private int bbValue = 20;
    private int callValue = 20;
    private int minRaiseValue = 40;
    private int potValue = 30;
    private int stackValue = 1500;

    private int betSizeValue = 40;

    private JButton fold = new JButton("FOLD");
    private JButton check = new JButton("CHECK");
    private JButton call = new JButton("CALL");
    private JButton raise = new JButton("RAISE");

    private JTextField betSize = new JTextField("40");
    private JSlider slider = new JSlider(new DefaultBoundedRangeModel(40, 0, 40, 1500));

    private JButton bet2bb = new JButton("2 bb");
    private JButton bet22bb = new JButton("2.2 bb");
    private JButton bet25bb = new JButton("2.5 bb");
    private JButton bet3bb = new JButton("3 bb");

    private JButton bet33 = new JButton("33%");
    private JButton bet50 = new JButton("50%");
    private JButton bet66 = new JButton("66%");
    private JButton bet75 = new JButton("75%");

    private JButton betPot = new JButton("Pot");
    private JButton betAll = new JButton("All");


    public HandleView() {
        initLook();
        initDecisionButtons();
        initBetButtons();
        updateHandle(20, 20, 40, 30, 1500, PREFLOP);
    }

    private void initLook() {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        Font font = new Font("Verdana", Font.BOLD, 22);
        betSize.setFont(font);
        betSize.setHorizontalAlignment(JLabel.CENTER);
        betSize.setEditable(false);

//        slider.setMinorTickSpacing(bb);
        slider.setMaximum(stackValue);
        slider.setMinimum(minRaiseValue);
//        slider.setSnapToTicks(true);
//        slider.setSnapToTicks(false);

        Insets zeroMargin = new Insets(0, 0, 0, 0);

        fold.setMargin(zeroMargin);
        check.setMargin(zeroMargin);
        call.setMargin(zeroMargin);
        raise.setMargin(zeroMargin);
        betAll.setMargin(zeroMargin);
        betPot.setMargin(zeroMargin);
        bet75.setMargin(zeroMargin);
        bet66.setMargin(zeroMargin);
        bet50.setMargin(zeroMargin);
        bet33.setMargin(zeroMargin);
        bet3bb.setMargin(zeroMargin);
        bet25bb.setMargin(zeroMargin);
        bet22bb.setMargin(zeroMargin);
        bet2bb.setMargin(zeroMargin);

        add(fold);
        layout.putConstraint(NORTH, fold, 40, NORTH, this);
        layout.putConstraint(WEST, fold, 20, WEST, this);
        layout.putConstraint(EAST, fold, 175, WEST, fold); // weight
        layout.putConstraint(SOUTH, fold, 50, NORTH, fold); // height

        add(check);
        layout.putConstraint(NORTH, check, 0, NORTH, fold);
        layout.putConstraint(WEST, check, 20, EAST, fold);
        layout.putConstraint(EAST, check, 175, WEST, check); // weight
        layout.putConstraint(SOUTH, check, 50, NORTH, check); // height

        add(call);
        layout.putConstraint(NORTH, call, 0, NORTH, fold);
        layout.putConstraint(WEST, call, 20, EAST, fold);
        layout.putConstraint(EAST, call, 175, WEST, call); // weight
        layout.putConstraint(SOUTH, call, 50, NORTH, call); // height

        add(raise);
        layout.putConstraint(NORTH, raise, 0, NORTH, check);
        layout.putConstraint(WEST, raise, 20, EAST, check);
        layout.putConstraint(EAST, raise, 175, WEST, raise); // weight
        layout.putConstraint(SOUTH, raise, 50, NORTH, raise); // height

        add(betSize);
        layout.putConstraint(NORTH, betSize, 10, NORTH, raise);
        layout.putConstraint(WEST, betSize, 20, EAST, raise);
        layout.putConstraint(EAST, betSize, 140, WEST, betSize); // weight
        layout.putConstraint(SOUTH, betSize, 40, NORTH, betSize); // height

        add(slider);
        layout.putConstraint(NORTH, slider, -20, NORTH, betSize);
        layout.putConstraint(WEST, slider, 0, WEST, betSize);
        layout.putConstraint(EAST, slider, 140, WEST, slider); // weight
        layout.putConstraint(SOUTH, slider, 20, NORTH, slider); // height

        add(betAll);
        layout.putConstraint(SOUTH, betAll, -1, SOUTH, betSize);
        layout.putConstraint(WEST, betAll, 5, EAST, betSize);
        layout.putConstraint(EAST, betAll, -5, EAST, this); // weight
        layout.putConstraint(NORTH, betAll, 1, NORTH, betSize); // height

        add(betPot);
        layout.putConstraint(SOUTH, betPot, -1, NORTH, betAll);
        layout.putConstraint(WEST, betPot, 0, WEST, betAll);
        layout.putConstraint(EAST, betPot, 0, EAST, betAll);
        layout.putConstraint(NORTH, betPot, -5, NORTH, slider);

        add(bet75); // <- anchor for bet66, bet50, bet33
        layout.putConstraint(NORTH, bet75, 0, NORTH, this);
        layout.putConstraint(SOUTH, bet75, -1, NORTH, betPot);
        layout.putConstraint(EAST, bet75, 0, EAST, betAll);
        layout.putConstraint(WEST, bet75, -45, EAST, bet75);

        add(bet66);
        layout.putConstraint(NORTH, bet66, 0, NORTH, bet75);
        layout.putConstraint(SOUTH, bet66, 0, SOUTH, bet75);
        layout.putConstraint(EAST, bet66, -1, WEST, bet75);
        layout.putConstraint(WEST, bet66, -45, EAST, bet66);

        add(bet50);
        layout.putConstraint(NORTH, bet50, 0, NORTH, bet75);
        layout.putConstraint(SOUTH, bet50, 0, SOUTH, bet75);
        layout.putConstraint(EAST, bet50, -1, WEST, bet66);
        layout.putConstraint(WEST, bet50, -45, EAST, bet50);

        add(bet33);
        layout.putConstraint(NORTH, bet33, 0, NORTH, bet75);
        layout.putConstraint(SOUTH, bet33, 0, SOUTH, bet75);
        layout.putConstraint(EAST, bet33, -1, WEST, bet50);
        layout.putConstraint(WEST, bet33, -45, EAST, bet33);

        add(bet3bb); // <- anchor for bet25bb, bet22bb
        layout.putConstraint(NORTH, bet3bb, 0, NORTH, this);
        layout.putConstraint(SOUTH, bet3bb, -1, NORTH, betPot);
        layout.putConstraint(EAST, bet3bb, 0, EAST, betAll);
        layout.putConstraint(WEST, bet3bb, -45, EAST, bet3bb);

        add(bet25bb);
        layout.putConstraint(NORTH, bet25bb, 0, NORTH, bet75);
        layout.putConstraint(SOUTH, bet25bb, 0, SOUTH, bet75);
        layout.putConstraint(EAST, bet25bb, -1, WEST, bet3bb);
        layout.putConstraint(WEST, bet25bb, -45, EAST, bet25bb);

        add(bet22bb);
        layout.putConstraint(NORTH, bet22bb, 0, NORTH, bet75);
        layout.putConstraint(SOUTH, bet22bb, 0, SOUTH, bet75);
        layout.putConstraint(EAST, bet22bb, -1, WEST, bet25bb);
        layout.putConstraint(WEST, bet22bb, -45, EAST, bet22bb);

        add(bet2bb);
        layout.putConstraint(NORTH, bet2bb, 0, NORTH, bet75);
        layout.putConstraint(SOUTH, bet2bb, 0, SOUTH, bet75);
        layout.putConstraint(EAST, bet2bb, -1, WEST, bet22bb);
        layout.putConstraint(WEST, bet2bb, -45, EAST, bet2bb);
    }

    private void initDecisionButtons() {

        fold.addActionListener(e -> {
            environment.heroPressFold();
        });

        check.addActionListener(e -> {
            environment.heroPressCheck();
        });

        call.addActionListener(e -> {
            environment.heroPressCall(callValue);
        });

        raise.addActionListener(e -> {
            environment.heroPressRaise(betSizeValue);
        });

        slider.addChangeListener(e -> {
            betSize.setText("" + slider.getValue());
            betSize.firePropertyChange("Value",0, slider.getValue());
        });

        betSize.addPropertyChangeListener(evt -> {
            betSizeValue = Integer.parseInt(betSize.getText());
            slider.setValue(betSizeValue);
            raise.setText("RAISE " + betSizeValue);
            if (betSizeValue == stackValue) {
                raise.setText("ALL IN " + betSizeValue);
            }
        });
    }

    private void initBetButtons(){
        betAll.addActionListener(e -> {
            betSize.setText(String.valueOf(stackValue));
            betSize.firePropertyChange("Value",0, stackValue);
        });
        betPot.addActionListener(e -> {
            int value = checkValue(getPercentOfPot(100));
            betSize.setText(String.valueOf(value));
            betSize.firePropertyChange("Value",0, value);
        });
        bet75.addActionListener(e -> {
            int value = checkValue(getPercentOfPot(75));
            betSize.setText(String.valueOf(value));
            betSize.firePropertyChange("Value",0, value);

        });
        bet66.addActionListener(e -> {
            int value = checkValue(getPercentOfPot(66));
            betSize.setText(String.valueOf(value));
            betSize.firePropertyChange("Value",0, value);
        });
        bet50.addActionListener(e -> {
            int value = checkValue(getPercentOfPot(50));
            betSize.setText(String.valueOf(value));
            betSize.firePropertyChange("Value",0, value);
        });
        bet33.addActionListener(e -> {
            int value = checkValue(getPercentOfPot(33));
            betSize.setText(String.valueOf(value));
            betSize.firePropertyChange("Value",0, value);
        });

        bet3bb.addActionListener(e -> {
            int value = checkValue(getNumberOfBigBlind(30));
            betSize.setText(String.valueOf(value));
            betSize.firePropertyChange("Value",0, value);
        });
        bet25bb.addActionListener(e -> {
            int value = checkValue(getNumberOfBigBlind(25));
            betSize.setText(String.valueOf(value));
            betSize.firePropertyChange("Value",0, value);
        });
        bet22bb.addActionListener(e -> {
            int value = checkValue(getNumberOfBigBlind(22));
            betSize.setText(String.valueOf(value));
            betSize.firePropertyChange("Value",0, value);
        });
        bet2bb.addActionListener(e -> {
            int value = checkValue(getNumberOfBigBlind(20));
            betSize.setText(String.valueOf(value));
            betSize.firePropertyChange("Value",0, value);
        });
    }

    private int getPercentOfPot(int percent) {
        if (percent < 0) return 0;

        return potValue * percent / 100;
    }

    private int getNumberOfBigBlind(int num) {
    // arg 25 = 2.5bb, 30 = 3bb, etc..
        if (num < 0) return 0;

        return bbValue * num / 10;
    }

    private int checkValue(int value) {
        value = value > stackValue ? stackValue : value;
        value = value < minRaiseValue ? minRaiseValue : value;
        value = minRaiseValue > stackValue ? stackValue : value;
        return value;
    }

    private void setVisiblePreflopBets(boolean isVisible) {
        bet3bb.setVisible(isVisible);
        bet25bb.setVisible(isVisible);
        bet22bb.setVisible(isVisible);
        bet2bb.setVisible(isVisible);
    }

    private void setVisiblePostflopBets(boolean isVisible) {
        bet75.setVisible(isVisible);
        bet66.setVisible(isVisible);
        bet50.setVisible(isVisible);
        bet33.setVisible(isVisible);
    }

    private void hideBets() {
        setVisiblePostflopBets(false);
        setVisiblePreflopBets(false);
    }

    private void showBets(int flopType) {
        if (flopType == PREFLOP) {
            setVisiblePreflopBets(true);
            setVisiblePostflopBets(false);
        }
        if (flopType == POSTFLOP) {
            setVisiblePostflopBets(true);
            setVisiblePreflopBets(false);
        }
    }

    private void updateVisibleButtons(int flopType) {
        //todo нельзя делать рейз, если кроме hero все в оллине
        if (callValue == 0) {
            fold.setVisible(false);
            check.setVisible(true);
            call.setVisible(false);
            raise.setVisible(true);
            showBets(flopType);
            check.requestFocusInWindow();
        } else {
            fold.setVisible(true);
            check.setVisible(false);
            call.setVisible(true);
            raise.setVisible(true);
            showBets(flopType);

            if (callValue == stackValue || minRaiseValue == 0) {
                raise.setVisible(false);
                hideBets();
            }

            fold.requestFocusInWindow();
        }
    }

    private void updateHandle(int bb, int call, int raise, int pot, int stack, int flopType) {
        bbValue = bb;
        callValue = call;
        minRaiseValue = raise;
        potValue = pot;
        stackValue = stack;

        this.call.setText("CALL " + call);

        slider.setModel(new DefaultBoundedRangeModel(raise, 0, raise, stack));
        betSize.setText(String.valueOf(raise));
        betSize.firePropertyChange("Value", 0, raise);

        updateVisibleButtons(flopType);
    }

    public void updateHandle(Environment env) {
        if (env.heroIsMoving()) {
            int bb = env.getBb();
            int call = env.getValueForCall();
            int raise = env.getValueForMinRaise();
            int pot = env.getPotSize();
            int stack = env.getHeroStack();
            int flopType = env.isPreflop() ? 0 : 1;
            updateHandle(bb, call, raise, pot, stack, flopType);

            waitForPushButton(env);
        }
    }

    private void waitForPushButton(Environment env) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                environment = env;
                setVisible(true);

                while (env.heroIsMoving()) {// wait for decision
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                setVisible(false);
                environment = null;

                return null;
            }
        };

        worker.execute();
    }

}
