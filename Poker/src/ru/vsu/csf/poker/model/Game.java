package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.GameStages;
import ru.vsu.csf.poker.enums.PlayerState;

import java.util.*;


public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private final Random rnd = new Random();
    private boolean isTextGame;

    protected Table table;
    private WinnerDeterminator wd;
    private final DecisionMaker dm = new DecisionMaker();

    public int bankrupts(Player[] players) {// Количество банкротов
        int counter = 0;
        for (Player player : players) {
            if (player.cash == 0) {
                counter += 1;
            }
        }
        return counter;
    }

    public void displayText(String str, boolean isTextGame){
        if (isTextGame) {
            System.out.println(str);
        }
    }

    public void oneRound(Player[] players, Deck deck, Table table, boolean isTextGame) {
        for (Player player : players) {
            player.state = PlayerState.DEFAULT;
            player.generateHand(deck);
        }

        displayText("New game!", isTextGame);

        if (dm.bettingCircle(players, GameStages.FIRST, table) == 1) {// Первое определение ставки, после чего достаются 3 карты
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
        displayText(Arrays.toString(table.table), isTextGame);

        if (!allIn) {
            if (dm.bettingCircle(players, GameStages.SECOND, table) == 1) {// Второе определение ставки, после чего достется 4 карта
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
        displayText(Arrays.toString(table.table), isTextGame);

        if (bankrupts(players) > 0) {
            allIn = true;
        }

        if (!allIn) {
            if (dm.bettingCircle(players, GameStages.THIRD, table) == 1) {// Третье определение ставки, после чего достется 5 карта
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
        displayText(Arrays.toString(table.table), isTextGame);

        if (bankrupts(players) > 0) {
            allIn = true;
        }

        if (!allIn) {
            if (dm.bettingCircle(players, GameStages.FOURTH, table) == 1) {// Четвертое определение ставки, когда все карты открыты
                return;
            }
            for (Player player : players) {
                table.setBank(table.getBank() + player.bet);
                player.cash -= player.bet;
                player.bet = 0;
            }
            table.setCurrentBet(0);
        }


        displayText(Arrays.toString(table.table), isTextGame);

        displayText("Opening up!", isTextGame);

        wd = new WinnerDeterminator(players);
        Player[] winners = wd.determineTheWinner();
        displayText("Winner: ", isTextGame);
        for (Player player : players) {
            for (int i = 0; i < winners.length; i++) {
                if (player.equals(winners[i])) {
                    displayText(player.name, isTextGame);
                    if (i != winners.length - 1) {
                        displayText(", ", isTextGame);
                    } else {
                        displayText(" ", isTextGame);
                    }
                    player.cash += (table.getBank()) / winners.length;
                }
            }
        }

        displayText("with combination " + winners[0].combination.combination.toString(), isTextGame);

        displayText(Arrays.toString(players), isTextGame);
    }

    public void gameSimulation(String playerName, int cash, int amountOfBots, boolean isTextGame) {
        Deck deck = new Deck();
        Table table = new Table();
        Player[] players = new Player[amountOfBots + 1];
        players[0] = new Player(playerName, cash, table);
        for (int i = 1; i < players.length; i++) {
            players[i] = new Player("Bot" + i, cash, table);
        }
        displayText("Enter play or stop: ", isTextGame);
        String dec = "";
        if (isTextGame) {
            dec = scanner.next();
        }
        while (!dec.equals("stop")) {
            if (dec.equals("play")) {
                if (bankrupts(players) > 0) {
                    displayText("Game is end! Somebody haven't got money \n" + Arrays.toString(players), isTextGame);
                    return;
                }
                oneRound(players, deck, table, isTextGame);
                table.setBank(0);
                table.setCurrentBet(0);
                table.table = new Card[5];
                dm.amountOfFolds = 0;
                deck = new Deck();
                for (Player player : players) {
                    player.combination = null;
                }
            }
            displayText("Enter play or stop: ", isTextGame);
            dec = scanner.next();
        }
    }

}
