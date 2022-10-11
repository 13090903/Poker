package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.PlayerState;

import java.util.*;


public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private final Random rnd = new Random();

    protected Table table;
    private WinnerDeterminator wd;
    private DecisionMaker dm = new DecisionMaker();

    public int bankrupts(Player[] players) {// Количество банкротов
        int counter = 0;
        for (Player player : players) {
            if (player.cash == 0) {
                counter += 1;
            }
        }
        return counter;
    }

    public void oneRound(Player[] players, Deck deck, Table table) {
        int cardsCounter = 52;
        for (Player player : players) {
            player.state = PlayerState.DEFAULT;
            player.generateHand(deck);
        }

        System.out.println("New game!");

        if (dm.bettingCircle(players, 1, table) == 1) {// Первое определение ставки, после чего достаются 3 карты
            return;
        }

        for (Player player : players) {
            table.setBank(table.getBank() + player.bet);
            player.cash -= player.bet;
            player.bet = 0;
        }
        table.setCurrentBet(0);

        boolean allIn = bankrupts(players) > 0;

        System.arraycopy(table.generateCards(deck, 3), 0, table.table, 0, 3);
        System.out.println(Arrays.toString(table.table));

        if (!allIn) {
            if (dm.bettingCircle(players, 2, table) == 1) {// Второе определение ставки, после чего достется 4 карта
                return;
            }

            for (Player player : players) {
                table.setBank(table.getBank() + player.bet);
                player.cash -= player.bet;
                player.bet = 0;
            }
            table.setCurrentBet(0);
        }

        Card[] genCard1 = table.generateCards(deck, 1);
        System.arraycopy(genCard1, 0, table.table, 3, 1);
        System.out.println(Arrays.toString(table.table));

        if (bankrupts(players) > 0) {
            allIn = true;
        }

        if (!allIn) {
            if (dm.bettingCircle(players, 3, table) == 1) {// Третье определение ставки, после чего достется 5 карта
                return;
            }
            for (Player player : players) {
                table.setBank(table.getBank() + player.bet);
                player.cash -= player.bet;
                player.bet = 0;
            }
            table.setCurrentBet(0);
        }

        Card[] genCard2 = table.generateCards(deck, 1);
        System.arraycopy(genCard2, 0, table.table, 4, 1);
        System.out.println(Arrays.toString(table.table));

        if (bankrupts(players) > 0) {
            allIn = true;
        }

        if (!allIn) {
            if (dm.bettingCircle(players, 4, table) == 1) {// Четвертое определение ставки, когда все карты открыты
                return;
            }
            for (Player player : players) {
                table.setBank(table.getBank() + player.bet);
                player.cash -= player.bet;
                player.bet = 0;
            }
            table.setCurrentBet(0);
        }


        System.out.println(Arrays.toString(table.table));

        System.out.println("Opening up!");

        wd = new WinnerDeterminator(players);
        Player[] winners = wd.determineTheWinner();
        System.out.print("Winner: ");
        for (Player player : players) {
            for (int i = 0; i < winners.length; i++) {
                if (player.equals(winners[i])) {
                    System.out.print(player.name);
                    if (i != winners.length - 1) {
                        System.out.print(", ");
                    } else {
                        System.out.print(" ");
                    }
                    player.cash += (table.getBank()) / winners.length;
                }
            }
        }

        System.out.println("with combination " + winners[0].combination.combination.toString());

        System.out.println(Arrays.toString(players));
    }

    public void gameSimulation(String playerName, int cash, int amountOfBots) {
        Deck deck = new Deck();
        Table table = new Table();
        Player[] players = new Player[amountOfBots + 1];
        players[0] = new Player(playerName, cash, table);
        for (int i = 1; i < players.length; i++) {
            players[i] = new Player("Bot" + i, cash, table);
        }
        System.out.println("Enter play or stop: ");
        String dec = scanner.next();
        while (!dec.equals("stop")) {
            if (dec.equals("play")) {
                if (bankrupts(players) > 0) {
                    System.out.println("Game is end! Somebody haven't got money \n" + Arrays.toString(players));
                    return;
                }
                oneRound(players, deck, table);
                table.setBank(0);
                table.setCurrentBet(0);
                table.table = new Card[5];
                dm.amountOfFolds = 0;
                deck = new Deck();
                for (Player player : players) {
                    player.combination = null;
                }
            }
            System.out.println("Enter play or stop: ");
            dec = scanner.next();
        }
    }

}
