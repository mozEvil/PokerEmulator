package ru.mozevil.poker.model.card;

import javax.swing.ImageIcon;
import java.io.Serializable;

public enum Card implements Serializable {

    TWO_OF_CLUBS(1, Rank.TWO, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/2c.png")),
    TWO_OF_DIAMONDS(2, Rank.TWO, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/2d.png")),
    TWO_OF_HEARTS(3, Rank.TWO, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/2h.png")),
    TWO_OF_SPADES(4, Rank.TWO, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/2s.png")),

    THREE_OF_CLUBS(5, Rank.THREE, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/3c.png")),
    THREE_OF_DIAMONDS(6, Rank.THREE, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/3d.png")),
    THREE_OF_HEARTS(7, Rank.THREE, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/3h.png")),
    THREE_OF_SPADES(8, Rank.THREE, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/3s.png")),

    FOUR_OF_CLUBS(9, Rank.FOUR, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/4c.png")),
    FOUR_OF_DIAMONDS(10, Rank.FOUR, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/4d.png")),
    FOUR_OF_HEARTS(11, Rank.FOUR, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/4h.png")),
    FOUR_OF_SPADES(12, Rank.FOUR, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/4s.png")),

    FIVE_OF_CLUBS(13, Rank.FIVE, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/5c.png")),
    FIVE_OF_DIAMONDS(14, Rank.FIVE, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/5d.png")),
    FIVE_OF_HEARTS(15, Rank.FIVE, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/5h.png")),
    FIVE_OF_SPADES(16, Rank.FIVE, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/5s.png")),

    SIX_OF_CLUBS(17, Rank.SIX, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/6c.png")),
    SIX_OF_DIAMONDS(18, Rank.SIX, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/6d.png")),
    SIX_OF_HEARTS(19, Rank.SIX, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/6h.png")),
    SIX_OF_SPADES(20, Rank.SIX, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/6s.png")),

    SEVEN_OF_CLUBS(21, Rank.SEVEN, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/7c.png")),
    SEVEN_OF_DIAMONDS(22, Rank.SEVEN, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/7d.png")),
    SEVEN_OF_HEARTS(23, Rank.SEVEN, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/7h.png")),
    SEVEN_OF_SPADES(24, Rank.SEVEN, Suit.SPADES,new ImageIcon("src/main/resources/images/deck/7s.png")),

    EIGHT_OF_CLUBS(25, Rank.EIGHT, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/8c.png")),
    EIGHT_OF_DIAMONDS(26, Rank.EIGHT, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/8d.png")),
    EIGHT_OF_HEARTS(27, Rank.EIGHT, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/8h.png")),
    EIGHT_OF_SPADES(28, Rank.EIGHT, Suit.SPADES,new ImageIcon("src/main/resources/images/deck/8s.png")),

    NINE_OF_CLUBS(29, Rank.NINE, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/9c.png")),
    NINE_OF_DIAMONDS(30, Rank.NINE, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/9d.png")),
    NINE_OF_HEARTS(31, Rank.NINE, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/9h.png")),
    NINE_OF_SPADES(32, Rank.NINE, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/9s.png")),

    TEN_OF_CLUBS(33, Rank.TEN, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/Tc.png")),
    TEN_OF_DIAMONDS(34, Rank.TEN, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/Td.png")),
    TEN_OF_HEARTS(35, Rank.TEN, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/Th.png")),
    TEN_OF_SPADES(36, Rank.TEN, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/Ts.png")),

    JACK_OF_CLUBS(37, Rank.JACK, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/Jc.png")),
    JACK_OF_DIAMONDS(38, Rank.JACK, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/Jd.png")),
    JACK_OF_HEARTS(39, Rank.JACK, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/Jh.png")),
    JACK_OF_SPADES(40, Rank.JACK, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/Js.png")),

    QUEEN_OF_CLUBS(41, Rank.QUEEN, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/Qc.png")),
    QUEEN_OF_DIAMONDS(42, Rank.QUEEN, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/Qd.png")),
    QUEEN_OF_HEARTS(43, Rank.QUEEN, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/Qh.png")),
    QUEEN_OF_SPADES(44, Rank.QUEEN, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/Qs.png")),

    KING_OF_CLUBS(45, Rank.KING, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/Kc.png")),
    KING_OF_DIAMONDS(46, Rank.KING, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/Kd.png")),
    KING_OF_HEARTS(47, Rank.KING, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/Kh.png")),
    KING_OF_SPADES(48, Rank.KING, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/Ks.png")),

    ACE_OF_CLUBS(49, Rank.ACE, Suit.CLUBS, new ImageIcon("src/main/resources/images/deck/Ac.png")),
    ACE_OF_DIAMONDS(50, Rank.ACE, Suit.DIAMONDS, new ImageIcon("src/main/resources/images/deck/Ad.png")),
    ACE_OF_HEARTS(51, Rank.ACE, Suit.HEARTS, new ImageIcon("src/main/resources/images/deck/Ah.png")),
    ACE_OF_SPADES(52, Rank.ACE, Suit.SPADES, new ImageIcon("src/main/resources/images/deck/As.png"));

    private final int id;
    private transient final Rank rank;
    private transient final Suit suit;
    private final transient ImageIcon image;

    Card(int id, Rank rank, Suit suit, ImageIcon image) {
        this.id = id;
        this.rank = rank;
        this.suit = suit;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getName() {
        return rank.getName() + suit.getName();
    }

    @Override
    public String toString() {
        return getName();
    }

}
