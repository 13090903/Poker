package ru.vsu.csf.poker.model;

public class Main {
    public static void main(String[] args) {
        Player[] players = {new Player("Dima", 5000), new Player("Bot", 5000)};
        Deck deck = new Deck();
        Game.game(players, deck);
    }
}
