package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.Rank;
import ru.vsu.csf.poker.enums.Suit;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    List<Card> deck = new ArrayList<>();

    public Deck() {
        initDeck();
    }

    public void initDeck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Card card : deck) {
            s.append(card);
        }
        return s.toString();
    }
}
