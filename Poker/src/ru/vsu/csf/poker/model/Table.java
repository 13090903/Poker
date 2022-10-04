package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.model.interfaces.CardGeneration;

import java.util.Random;

public class Table implements CardGeneration {

    protected Card[] table = new Card[5];
    private final Random rnd = new Random();

    private int bank = 0;
    private int currentBet = 0;

    @Override
    public void generateThreeCards(int cardsCounter, Deck deck) { // Сгенерировать 3 карты на столе
        int num = rnd.nextInt(cardsCounter);
        table[0] = deck.deck.get(num);
        deck.deck.remove(num);
        num = rnd.nextInt(cardsCounter - 1);
        table[1] = deck.deck.get(num);
        deck.deck.remove(num);
        num = rnd.nextInt(cardsCounter - 2);
        table[2] = deck.deck.get(num);
        deck.deck.remove(num);
    }

    @Override
    public void generateOneCard(int cardsCounter, int cardNum, Deck deck) { // Сгенерировать 1 карту на столе
        int num = rnd.nextInt(cardsCounter);
        table[cardNum - 1] = deck.deck.get(num);
        deck.deck.remove(num);
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
}
