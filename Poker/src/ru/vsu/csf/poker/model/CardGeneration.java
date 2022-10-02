package ru.vsu.csf.poker.model;

import java.util.Random;

public class CardGeneration {
    private static final Random rnd = new Random();

    public static void generateHand(Player player, int cardsCounter, Deck deck) { // Сгенерировать 2 карты игрока
        Card[] hand = new Card[2];
        int num = rnd.nextInt(cardsCounter);
        hand[0] = deck.deck.get(num);
        deck.deck.remove(num);
        num = rnd.nextInt(cardsCounter - 1);
        hand[1] = deck.deck.get(num);
        deck.deck.remove(num);
        player.setHand(hand);
    }

    public static void generateThreeCards(Card[] table, int cardsCounter, Deck deck) { // Сгенерировать 3 карты на столе
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

    public static void generateOneCard(Card[] table, int cardsCounter, int cardNum, Deck deck) { // Сгенерировать 1 карту на столе
        int num = rnd.nextInt(cardsCounter);
        table[cardNum - 1] = deck.deck.get(num);
        deck.deck.remove(num);
    }
}
