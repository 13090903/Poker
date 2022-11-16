package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.Rank;
import ru.vsu.csf.poker.enums.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    Stack<Card> cards = new Stack<>();

    public Deck() {
        shuffle();
    }

    public void shuffle() {
        cards.clear();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    public Card pop() {
        return cards.pop();
    }

    public int size() {
        return  cards.size();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Card card : cards) {
            s.append(card);
        }
        return s.toString();
    }
}
