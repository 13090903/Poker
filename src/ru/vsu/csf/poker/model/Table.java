package ru.vsu.csf.poker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Table {
    protected List<Card> cards = new ArrayList<>();
    private final Deck deck = new Deck();

    private int bank = 0;
    private int currentBet = 0;
    protected int amountOfFolds = 0;

    public void openCard() {
        cards.add(deck.pop());
    }

    public void reset() {
        cards.clear();
        deck.shuffle();
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Deck getDeck() {
        return deck;
    }
}
