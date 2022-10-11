package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.PlayerState;
import ru.vsu.csf.poker.model.interfaces.GameInOut;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DecisionMaker implements GameInOut {
    private final Scanner scanner = new Scanner(System.in);
    private final Random rnd = new Random();
    protected int amountOfFolds = 0;

    @Override
    public void botsDecisions(Player[] players, int i, Table table) {// Все решения, принимаемые ботами
        if (players[i].getState().equals(PlayerState.FOLD)) {
            return;
        }
        int decision = 1 + rnd.nextInt(30);
        if (decision == 1 && i != 0) {
            int newBet = table.getCurrentBet() + 100 + (rnd.nextInt(table.getCurrentBet() + 100) / 100) * 100;
            while (players[i].getCash() <= newBet) {
                newBet = table.getCurrentBet() + 100 + (rnd.nextInt(table.getCurrentBet()) / 100) * 100;
            }
            players[i].raise(newBet);
            System.out.printf("Player %d raises to %d\n", i, players[i].getBet());
        }
        if (decision == 2 && i != 0) {
            players[i].fold();
            System.out.printf("Player %d folds\n", i);
            amountOfFolds += 1;
            return;
        }
        if (decision > 2 && i != 0) {
            if (table.getCurrentBet() > players[i].getBet()) {
                if (table.getCurrentBet() <= players[i].getCash()) {
                    players[i].call();
                    System.out.printf("Player %d calls\n", i);
                } else {
                    players[i].setBet(players[i].getCash());
                    System.out.printf("Player %d going all-in\n", i);
                }
            } else {
                players[i].check();
                System.out.printf("Player %d checks\n", i);
            }
        }
        System.out.println("Current bet is " + table.getCurrentBet());
    }

    @Override
    public void oneBet(Player[] players, int i, Table table) {// Первая ставка перед каждым открытием карт (Можно заменить на блайнды)
        System.out.println(players[i]);
        System.out.print("Use blind - blind, raise - raise or fold - fold\n");
        String blind = scanner.next();
        if (blind.equals("blind")) {
            table.setCurrentBet(100);
            players[0].setBet(100);
            return;
        }
        if (blind.equals("fold")) {
            players[i].fold();
            amountOfFolds += 1;
            return;
        }
        if (blind.equals("raise")) {
            System.out.print("Enter your bet: ");
            int someBet = scanner.nextInt();
            while (someBet > players[i].getCash() || someBet <= 0) {
                System.out.print("This bet is impossible. Enter another bet: ");
                someBet = scanner.nextInt();
            }
            table.setCurrentBet(someBet);
            players[0].setBet(someBet);
        } else {
            System.out.print("Use blind - blind, raise - raise or fold - fold\n");
            String dec = scanner.next();
            while (!dec.equals("blind") && !dec.equals("raise") && !dec.equals("fold")) {
                System.out.print("Use blind - blind, raise - raise or fold - fold\n");
                dec = scanner.next();
            }
            if (dec.equals("blind")) {
                players[0].blind();
                return;
            }
            if (dec.equals("fold")) {
                players[i].fold();
                amountOfFolds += 1;
            } else {
                System.out.print("Enter your bet: ");
                int someBet = scanner.nextInt();
                while (someBet > players[i].getCash() || someBet <= 0) {
                    System.out.print("This bet is impossible. Enter another bet: ");
                    someBet = scanner.nextInt();
                }
                table.setCurrentBet(someBet);
                players[0].setBet(someBet);
            }
        }
    }

    @Override
    public void playerDecisions(Player[] players, int i, Table table) {// Решения игрока, за которого мы играем
        System.out.println("Enter your decision: ");
        String dec = scanner.next();
        switch (dec) {
            case "fold":
                players[i].fold();
                amountOfFolds += 1;
                break;
            case "call":
                if (players[i].getBet() < table.getCurrentBet() || players[i].getBet() > table.getCurrentBet()) {
                    players[i].call();
                } else {
                    System.out.println("You don't need to call, your bet is current bet in game, so check");
                    players[i].check();
                }
                players[i].call();
                break;
            case "check":
                if (players[i].getBet() == table.getCurrentBet()) {
                    players[i].check();
                } else {
                    System.out.println("You can't check, current bet is higher than yours. Call, raise or fold: ");
                    String dec1 = scanner.next();
                    switch (dec1) {
                        case "fold":
                            players[i].fold();
                            amountOfFolds += 1;
                            break;
                        case "call":
                            if (players[i].getCash() <= table.getCurrentBet()) {
                                players[i].call();
                            } else {
                                players[i].setBet(players[i].getCash());
                            }
                            break;
                        case "raise":
                            System.out.println("Enter new bet: ");
                            int newBet = scanner.nextInt();
                            while (players[i].getCash() <= newBet || newBet <= table.getCurrentBet()) {
                                System.out.println("You haven't got enough cash or bet is less than last. Enter new bet: : ");
                                newBet = scanner.nextInt();
                            }
                            players[i].raise(newBet);
                            break;
                        default:
                            System.out.println("Impossible action");
                    }

                }
                break;
            case "raise":
                System.out.println("Enter new bet: ");
                int newBet = scanner.nextInt();
                while (players[i].getCash() < newBet || newBet <= table.getCurrentBet()) {
                    System.out.println("You haven't got enough cash or bet is less than last. Enter new bet: : ");
                    newBet = scanner.nextInt();
                }
                players[i].raise(newBet);
                break;
            default:
                System.out.println("Impossible action");
        }
    }

    public boolean everybodyChecks(Player[] players) {
        for (Player player : players) {
            if (player.getState() == PlayerState.DEFAULT) {
                return false;
            }
        }
        return true;
    }

    public boolean endGameConditions(Player[] players) { // Условия, при которых раунд не может продолжаться
        Table table = players[0].getTable();
        if ((amountOfFolds >= players.length - 1)) {
            if (amountOfFolds == players.length - 1) {
                for (Player player : players) {
                    if (player.getState() != PlayerState.FOLD) {
                        player.setCash(player.getCash() + table.getBank());
                    }
                }
            }
            if (amountOfFolds == players.length) {
                table.setBank(0);
            }
            System.out.println("Round is over");
            System.out.println(Arrays.toString(players));
            return true;
        }
        return false;
    }

    public boolean everybodyAllIn(Player[] players) {
        for (Player player : players) {
            if (player.getCash() != player.getBet()) {
                return false;
            }
        }
        return true;
    }

    public boolean betIsTheSame(Player[] players) { // Ставка у всех одинаковая
        int lastNotFold = 0;
        for (int i = 1; i < players.length; i++) {
            if (!players[i].getState().equals(PlayerState.FOLD)) {
                if (players[i].getBet() != players[lastNotFold].getBet()) {
                    return false;
                }
                lastNotFold = i;
            }
        }
        return true;
    }
        public int bettingCircle(Player[] players, int circleNumber, Table table) { // Один ставочный круг
        boolean flag = false;
        while (!betIsTheSame(players) || !everybodyChecks(players)) {
            if (everybodyAllIn(players)) {
                break;
            }
            for (int i = 0; i < players.length; i++) {
                if (i == 0 && table.getCurrentBet() == 0 && circleNumber == 1) {
                    oneBet(players, i, table);
                }

                if (endGameConditions(players)) {
                    return 1;
                }

                if (i > 0) {
                    botsDecisions(players, i, table);
                    flag = true;
                }

                if (endGameConditions(players)) {
                    return 1;
                }

                if (i == 0 && flag) {
                    playerDecisions(players, i, table);
                }

                if (endGameConditions(players)) {
                    return 1;
                }

            }
        }
        for (Player player : players) {
            if (player.getState().equals(PlayerState.CHECK)) {
                player.setState(PlayerState.DEFAULT);
            }
        }
        return 0;
    }
}
