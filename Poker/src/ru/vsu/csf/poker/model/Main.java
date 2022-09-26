package ru.vsu.csf.poker;

public class Main {
    public static void main(String[] args) {
        Deck d = new Deck();
        d.initDeck();
        System.out.println(d.deck);
    }
}
