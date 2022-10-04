package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.decision.Decisions;
import ru.vsu.csf.poker.enums.PlayerState;

import java.util.*;

import static ru.vsu.csf.poker.decision.Decisions.bettingCircle;

public class Game {

    private static final Scanner scanner = new Scanner(System.in);

    protected static int bank = 0;
    protected static int currentBet = 0;
    protected Table table;
    private WinnerDeterminator wd;

    public static int getBank() {
        return bank;
    }

    public static void setBank(int bank) {
        Game.bank = bank;
    }

    public static int getCurrentBet() {
        return currentBet;
    }

    public static void setCurrentBet(int currentBet) {
        Game.currentBet = currentBet;
    }

    public static int bankrupts(Player[] players) {// Количество банкротов
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
            player.generateHand(cardsCounter, deck);
            cardsCounter -= 2;
        }

        System.out.println("New game!");

        if (bettingCircle(players, 1) == 1) {// Первое определение ставки, после чего достаются 3 карты
            return;
        }

        for (Player player : players) {
            bank += player.bet;
            player.cash -= player.bet;
            player.bet = 0;
        }
        currentBet = 0;

        boolean allIn = bankrupts(players) > 0;

        table.generateThreeCards(cardsCounter, deck);
        cardsCounter -= 3;
        System.out.println(Arrays.toString(table.table));

        if (!allIn) {
            if (bettingCircle(players, 2) == 1) {// Второе определение ставки, после чего достется 4 карта
                return;
            }

            for (Player player : players) {
                bank += player.bet;
                player.cash -= player.bet;
                player.bet = 0;
            }
            currentBet = 0;
        }

        table.generateOneCard(cardsCounter, 4, deck);
        cardsCounter -= 1;
        System.out.println(Arrays.toString(table.table));

        if (bankrupts(players) > 0) {
            allIn = true;
        }

        if (!allIn) {
            if (bettingCircle(players, 3) == 1) {// Третье определение ставки, после чего достется 5 карта
                return;
            }
            for (Player player : players) {
                bank += player.bet;
                player.cash -= player.bet;
                player.bet = 0;
            }
            currentBet = 0;
        }

        table.generateOneCard(cardsCounter, 5, deck);
        System.out.println(Arrays.toString(table.table));

        if (bankrupts(players) > 0) {
            allIn = true;
        }

        if (!allIn) {
            if (bettingCircle(players, 4) == 1) {// Четвертое определение ставки, когда все карты открыты
                return;
            }
            for (Player player : players) {
                bank += player.bet;
                player.cash -= player.bet;
                player.bet = 0;
            }
            currentBet = 0;
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
                    player.cash += (bank)/ winners.length;
                }
            }
        }

        System.out.println("with combination " + winners[0].combination.combination.toString());

        System.out.println(Arrays.toString(players));
    }

    public void gameSimulation() {
        Deck deck = new Deck();
        Table table = new Table();
        Player[] players = new Player[]{new Player("Dima", 5000, table), new Player("Bot", 3000, table)};
        System.out.println("Enter play or stop: ");
        String dec = scanner.next();
        while (!dec.equals("stop")) {
            if (dec.equals("play")) {
                if (bankrupts(players) > 0) {
                    System.out.println("Game is end! Somebody haven't got money \n" + Arrays.toString(players));
                    return;
                }
                oneRound(players, deck, table);
                bank = 0;
                currentBet = 0;
                Decisions.setAmountOfFolds(0);
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
