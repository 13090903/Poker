package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.model.interfaces.CardGeneration;

import java.util.Random;

public class Table implements CardGeneration {

    protected Card[] table = new Card[5];
    private final Random rnd = new Random();

    private int bank = 0;
    private int currentBet = 0;

    @Override
    public Card[] generateCards(Deck deck, int cardAmount) { // Сгенерировать 3 карты на столе
        int cardsCounter = deck.deck.size();
        int num;
        Card[] generatedCards = new Card[cardAmount];
        for (int i = 0; i < cardAmount; i++) {
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
}
