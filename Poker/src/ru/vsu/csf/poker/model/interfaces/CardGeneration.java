package ru.vsu.csf.poker.model.interfaces;

import ru.vsu.csf.poker.model.Deck;

public interface CardGeneration {

    void generateThreeCards(int cardsCounter, Deck deck);

    void generateOneCard(int cardsCounter, int cardNum, Deck deck);
}
