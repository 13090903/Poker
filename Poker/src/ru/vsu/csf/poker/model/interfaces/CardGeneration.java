package ru.vsu.csf.poker.model.interfaces;

import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Deck;

public interface CardGeneration {

    Card[] generateCards(Deck deck, int cardAmount);
}
