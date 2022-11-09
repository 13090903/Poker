package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.GameStages;
import ru.vsu.csf.poker.interfaces.CardGeneration;
import ru.vsu.csf.poker.utils.Coord;

import java.util.Random;

public class Table implements CardGeneration {

    protected Card[] table = new Card[5];
    private final Random rnd = new Random();
    private Deck deck;

    private int bank = 0;
    private int currentBet = 0;
    protected int amountOfFolds = 0;

    public Table() {
        resetDeck();
    }

    public void resetDeck() {
        deck = new Deck();
    }

    @Override
    public Card[] generateCards(GameStages stage) {
        int cardsCounter = deck.deck.size();
        int num;
        Card[] generatedCards = new Card[stage.getCardAmount()];
        for (int i = 0; i < stage.getCardAmount(); i++) {
            num = rnd.nextInt(cardsCounter - i);
            generatedCards[i] = deck.deck.get(num);
            deck.deck.remove(num);
        }
        return generatedCards;
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

    public Card[] getTable() {
        return table;
    }

    public Deck getDeck() {
        return deck;
    }
}
