package ru.mozevil.poker.eva;

import org.jetbrains.annotations.NotNull;
import ru.mozevil.poker.model.card.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MozEvaluator implements Evaluator {

    private boolean pair;
    private boolean twoPairs;
    private boolean set;
    private boolean straight;
    private boolean flush;
    private boolean fullHouse;
    private boolean quads;
    private boolean straightFlush;
    private boolean royalFlush;

    private Suit flushSuit;
    private int straightStartIndex;
    private int straightFlushStartIndex;


    @Override
    public Combination getBestCombo(@NotNull Hand hand, @NotNull Combination tableCards) {

        reset();

        List<Card> cards = getOrderedList(hand, tableCards);

        return getBestCombo(cards);
    }

    private void reset() {
        pair = false;
        twoPairs = false;
        set = false;
        straight = false;
        flush = false;
        fullHouse = false;
        quads = false;
        straightFlush = false;
        royalFlush = false;
    }

    private List<Card> getOrderedList(@NotNull Hand hand, @NotNull Combination tableCards) {
        List<Card> cards = new ArrayList<>();
        cards.add(hand.getHighCard());
        cards.add(hand.getLowCard());
        cards.addAll(tableCards.getCards());
        cards.sort(Collections.reverseOrder());

        return cards;
    }

    private List<Card> getOrderedListByStream(@NotNull Hand hand, @NotNull Combination tableCards) {

        return Stream.concat(
                Stream.of(hand.getHighCard(), hand.getLowCard()),
                tableCards.getCards().stream()
        ).sorted(Collections.reverseOrder()
        ).collect(Collectors.toList());
    }

    private Combination getBestCombo(@NotNull List<Card> cards) {

        findStraight(cards);
        findFlush(cards);

        if (straight && flush) {

            findStraightFlush(cards);
            if (straightFlush) {

                findRoyalFlush(cards);
                if (royalFlush) {
                    return getRoyalFlush(cards); // royal flush
                }

                return getStraightFlush(cards); // straight flush
            }
        }

        if (!straight && !flush) {

            findByRank(cards);

            if (quads) {
                return getQuads(cards); // quads
            }

            if (fullHouse) {
                return getFullHouse(cards); // full house
            }
        }

        if (flush) {
            return getFlush(cards); // flush
        }

        if (straight) {
            return getStraight(cards); // straight
        }

        if (set) {
            return getSet(cards); // set
        }

        if (twoPairs) {
            return getTwoPairs(cards); // two pairs
        }

        if (pair) {
            return getPair(cards); // pair
        }

        return getHighCard(cards); // High card
    }

    private void findByRank(List<Card> cards) {
        EnumMap<Rank, Integer> map = new EnumMap<>(Rank.class);

        for (Card c : cards) {
            Integer oldValue = map.put(c.getRank(), 1);

            if (oldValue != null) {
                map.put(c.getRank(), oldValue + 1);
            }
        }

        int twoPairsCount = 0;
        for (int val : map.values()) {
            if (val == 2) {
                twoPairsCount++;
                pair = true;
            }
            if (val == 3) {
                set = true;
            }
            if (val == 4) {
                quads = true;
            }
        }

        if (twoPairsCount >= 2) {
            twoPairs = true;
        }

        if (pair && set) {
            fullHouse = true;
        }
    }

    private void findStraight(List<Card> cards) {
        LinkedHashSet<Rank> ranks = new LinkedHashSet<>();
        // избавляемся от дубликатов по рангу
        for (Card card : cards) {
            ranks.add(card.getRank());
        }

        if (ranks.size() < 5) {
            return;
        }

        List<Rank> list = new ArrayList<>(ranks);
//        listR.sort(Collections.reverseOrder());

        for (int i = 0; i < (list.size() - 4); i++) {
            if (list.get(i).previousRank() == list.get(i + 1)
                    && list.get(i + 1).previousRank() == list.get(i + 2)
                    && list.get(i + 2).previousRank() == list.get(i + 3)
                    && list.get(i + 3).previousRank() == list.get(i + 4)) {

                straight = true;
                straightStartIndex = i;
                return;
            }
        }

        // ищем Лоу стирт
        int i = list.size() - 4;
        if (list.get(i).previousRank() == list.get(i+1)
                && list.get(i+1).previousRank() == list.get(i+2)
                && list.get(i+2).previousRank() == list.get(i+3)
                && list.get(i+3).previousRank() == list.get(0)) {

            straight = true;
            straightStartIndex = i;
        }

    }

    private void findFlush(List<Card> cards) {
        EnumMap<Suit, Integer> map = new EnumMap<>(Suit.class);

        for (Card c : cards) {
            Integer oldValue = map.put(c.getSuit(), 1);

            if (oldValue != null) {
                map.put(c.getSuit(), oldValue + 1);
            }
        }

        for (Map.Entry<Suit, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= 5) {
                flush = true;
                flushSuit = entry.getKey();
                return;
            }
        }
    }

    private void findStraightFlush(List<Card> cards) {
        List<Card> list = new ArrayList<>(cards);
        // удаляем не нашу масть
        list.removeIf(card -> card.getSuit() != flushSuit);

        if (list.size() < 5) {
            return;
        }

        for (int i = 0; i < (list.size() - 4); i++) {
            if (list.get(i).getRank().previousRank() == list.get(i+1).getRank()
                    && list.get(i+1).getRank().previousRank() == list.get(i+2).getRank()
                    && list.get(i+2).getRank().previousRank() == list.get(i+3).getRank()
                    && list.get(i+3).getRank().previousRank() == list.get(i+4).getRank()) {

                straightFlush = true;
                straightFlushStartIndex = i;
                return;
            }
        }
        // ищем Лоу стирт-флеш
        int i = list.size() - 4; // min=1, max=3
        if (list.get(i).getRank().previousRank() == list.get(i+1).getRank()
                && list.get(i+1).getRank().previousRank() == list.get(i+2).getRank()
                && list.get(i+2).getRank().previousRank() == list.get(i+3).getRank()
                && list.get(i+3).getRank().previousRank() == list.get(0).getRank()) {

            straightFlush = true;
            straightFlushStartIndex = i;
        }
    }

    private void findRoyalFlush(List<Card> cards) {
        if (cards.get(straightFlushStartIndex).getRank() == Rank.ACE) {
            royalFlush = true;
        }
    }

    private Combination getHighCard(List<Card> cards) {
        Combination combo = new Combination();
        for (int i = 0; i < 5; i++) {
            combo.addCard(cards.get(i));
        }

        combo.setTitle(Combination.HIGH_CARD);

        return combo;
    }

    private Combination getPair(List<Card> cards) {
        Combination combo = new Combination();

        for (int i = 0; i <= 5; i++) {
            if (cards.get(i).getRank() == cards.get(i+1).getRank()) {
                combo.addCard(cards.remove(i));
                combo.addCard(cards.remove(i));
                break;
            }
        }

        combo.addCard(cards.get(0));
        combo.addCard(cards.get(1));
        combo.addCard(cards.get(2));

        combo.setTitle(Combination.PAIR);

        return combo;
    }

    private Combination getTwoPairs(List<Card> cards) {
        Combination combo = new Combination();
        int count = 0;

        for (int i = 0; i <= 5; i++) {
            if (cards.get(i).getRank() == cards.get(i+1).getRank()) {
                combo.addCard(cards.remove(i));
                combo.addCard(cards.remove(i));
                i--;
                count++;
                if (count == 2) {
                    break;
                }
            }
        }

        combo.addCard(cards.get(0));

        combo.setTitle(Combination.TWO_PAIRS);

        return combo;
    }

    private Combination getSet(List<Card> cards) {
        Combination combo = new Combination();

        for (int i = 0; i <= 4; i++) {
            if (cards.get(i).getRank() == cards.get(i+1).getRank() &&
                    cards.get(i+1).getRank() == cards.get(i+2).getRank()) {
                combo.addCard(cards.remove(i));
                combo.addCard(cards.remove(i));
                combo.addCard(cards.remove(i));
                break;
            }
        }

        combo.addCard(cards.get(0));
        combo.addCard(cards.get(1));

        combo.setTitle(Combination.THREE_OF_A_KIND);

        return combo;
    }

    private Combination getStraight(List<Card> cards) {
        Combination combo = new Combination();

        //удалить дубликаты рангов
        for (int i = 0; i < (cards.size() - 1); i++) {
            if (cards.get(i).getRank() == cards.get(i+1).getRank()) {
                cards.remove(i);
                i--;
            }
        }

        if (straightStartIndex < (cards.size() - 4)) {
            for (int i = 0; i < 5; i++) {
                combo.addCard(cards.get(straightStartIndex + i));
            }
        } else {
            for (int i = 0; i < 4; i++) {
                combo.addCard(cards.get(straightStartIndex + i));
            }
            combo.addCard(cards.get(0));
        }

        combo.setTitle(Combination.STRAIGHT);

        return combo;
    }

    private Combination getFlush(List<Card> cards) {
        Combination combo = new Combination();

        for (Card card : cards) {
            if (card.getSuit() == flushSuit) {
                combo.addCard(card);
            }
            if (combo.getCount() == 5) {
                break;
            }
        }

        combo.setTitle(Combination.FLUSH);

        return combo;
    }

    private Combination getFullHouse(List<Card> cards) {
        Combination combo = new Combination();

        for (int i = 0; i <= 4; i++) {
            if (cards.get(i).getRank() == cards.get(i+1).getRank()
                    && cards.get(i).getRank() == cards.get(i+2).getRank()) {
                combo.addCard(cards.remove(i));
                combo.addCard(cards.remove(i));
                combo.addCard(cards.remove(i));
                break;
            }
        }

        for (int i = 0; i <= 2; i++) {
            if (cards.get(i).getRank() == cards.get(i+1).getRank()) {
                combo.addCard(cards.get(i));
                combo.addCard(cards.get(i+1));
                break;
            }
        }

        combo.setTitle(Combination.FULL_HOUSE);

        return combo;
    }

    private Combination getQuads(List<Card> cards) {
        Combination combo = new Combination();

        for (int i = 0; i <= 3; i++) {
            if (cards.get(i).getRank() == cards.get(i+1).getRank() &&
                    cards.get(i+1).getRank() == cards.get(i+2).getRank() &&
                    cards.get(i+2).getRank() == cards.get(i+3).getRank()) {
                combo.addCard(cards.remove(i));
                combo.addCard(cards.remove(i));
                combo.addCard(cards.remove(i));
                combo.addCard(cards.remove(i));
                break;
            }
        }

        combo.addCard(cards.get(0));

        combo.setTitle(Combination.FOUR_OF_A_KIND);

        return combo;
    }

    private Combination getStraightFlush(List<Card> cards) {
        Combination combo = new Combination();

        // удаляем не нашу масть
        cards.removeIf(card -> card.getSuit() != flushSuit);

        if (straightFlushStartIndex < (cards.size() - 4)) {
            for (int i = 0; i < 5; i++) {
                combo.addCard(cards.get(straightFlushStartIndex + i));
            }
        } else {
            for (int i = 0; i < 4; i++) {
                combo.addCard(cards.get(straightFlushStartIndex + i));
            }
            combo.addCard(cards.get(0));
        }

        combo.setTitle(Combination.STRAIGHT_FLUSH);

        return combo;
    }

    private Combination getRoyalFlush(List<Card> cards) {
        Combination combo = getStraightFlush(cards);
        combo.setTitle(Combination.ROYAL_FLUSH);

        return combo;
    }
}
