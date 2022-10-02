package ru.vsu.csf.poker;

import ru.vsu.csf.poker.model.Deck;
import ru.vsu.csf.poker.model.Game;
import ru.vsu.csf.poker.model.Player;

public class Main {
    public static void main(String[] args) {
        Player[] players = {new Player("Dima", 5000), new Player("Bot", 3000)};
        Deck deck = new Deck();
        Game.gameSimulation(players, deck);
    }
}
