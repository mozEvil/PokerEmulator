package ru.mozevil.poker.view.com;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

import static javax.swing.SpringLayout.*;

public class PotView extends JPanel {

    private JLabel potSize;

    public PotView() {
        init();
    }

    private void init() {
        setOpaque(false);
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        setSize(97, 90);

        ImageIcon imagePot = new ImageIcon("src/main/resources/images/table/pot.png");

        JLabel pot = new JLabel(imagePot);
        pot.setHorizontalAlignment(JLabel.CENTER);

        add(pot);

        layout.putConstraint(WEST, pot, 0, WEST, this);
        layout.putConstraint(NORTH, pot, 0, NORTH, this);
        layout.putConstraint(EAST, pot, 97, WEST, pot); // weight
        layout.putConstraint(SOUTH, pot, 57, NORTH, pot); // height

        potSize = new JLabel("13500");
//        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
//        potSize.setBorder(solidBorder);
        potSize.setHorizontalAlignment(JLabel.CENTER);
        Font font = new Font("Verdana", Font.BOLD, 14);
        potSize.setFont(font);
        Color colorFgr = new Color(255, 255, 255);
        Color colorBgr = new Color(0, 0, 0);
        potSize.setForeground(colorFgr);
        potSize.setBackground(colorBgr);
        potSize.setOpaque(true);

        add(potSize);

        layout.putConstraint(WEST, potSize, 0, WEST, this);
        layout.putConstraint(NORTH, potSize, 3, SOUTH, pot);
        layout.putConstraint(EAST, potSize, 97, WEST, potSize); // weight
        layout.putConstraint(SOUTH, potSize, 30, NORTH, potSize); // height


    }

    public void setPotSize(int value) {
        potSize.setText("" + value);
    }
}
