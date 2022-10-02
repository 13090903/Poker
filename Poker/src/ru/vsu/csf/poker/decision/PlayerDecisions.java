package ru.vsu.csf.poker.decision;

import ru.vsu.csf.poker.model.Player;

import java.util.Scanner;

import static ru.vsu.csf.poker.model.Game.*;

public class PlayerDecisions {
    private static final Scanner scanner = new Scanner(System.in);

    public static void oneBet(Player[] players, int i) {// Первая ставка перед каждым открытием карт (Можно заменить на блайнды)
        System.out.println(players[i]);
        System.out.print("Use blind - blind, raise - raise or fold - fold\n");
        String blind = scanner.next();
        if (blind.equals("blind")) {
            setCurrentBet(100);
            players[0].setBet(100);
            return;
        }
        if (blind.equals("fold")) {
            players[i].fold();
            Decisions.amountOfFolds += 1;
            return;
        }
        if (blind.equals("raise")) {
            System.out.print("Enter your bet: ");
            int someBet = scanner.nextInt();
            while (someBet > players[i].getCash() || someBet <= 0) {
                System.out.print("This bet is impossible. Enter another bet: ");
                someBet = scanner.nextInt();
            }
            setCurrentBet(someBet);
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
                Decisions.amountOfFolds += 1;
            } else {
                System.out.print("Enter your bet: ");
                int someBet = scanner.nextInt();
                while (someBet > players[i].getCash() || someBet <= 0) {
                    System.out.print("This bet is impossible. Enter another bet: ");
                    someBet = scanner.nextInt();
                }
                setCurrentBet(someBet);
                players[0].setBet(someBet);
            }
        }
    }

    public static void playerDecisions(Player[] players, int i) {// Решения игрока, за которого мы играем
        if (i == 0) {
            System.out.println("Enter your decision: ");
            String dec = scanner.next();
            switch (dec) {
                case "fold":
                    players[i].fold();
                    Decisions.amountOfFolds += 1;
                    break;
                case "call":
                    if (players[i].getBet() < getCurrentBet() || players[i].getBet() > getCurrentBet()) {
                        players[i].call();
                    } else {
                        System.out.println("You don't need to call, your bet is current bet in game, so check");
                        players[i].check();
                    }
                    players[i].call();
                    break;
                case "check":
                    if (players[i].getBet() == getCurrentBet()) {
                        players[i].check();
                    } else {
                        System.out.println("You can't check, current bet is higher than yours. Call, raise or fold: ");
                        String dec1 = scanner.next();
                        switch (dec1) {
                            case "fold":
                                players[i].fold();
                                Decisions.amountOfFolds += 1;
                                break;
                            case "call":
                                if (players[i].getCash() <= getCurrentBet()) {
                                    players[i].call();
                                } else {
                                    players[i].setBet(players[i].getCash());
                                }
                                break;
                            case "raise":
                                System.out.println("Enter new bet: ");
                                int newBet = scanner.nextInt();
                                while (players[i].getCash() <= newBet || newBet <= getCurrentBet()) {
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
                    while (players[i].getCash() < newBet || newBet <= getCurrentBet()) {
                        System.out.println("You haven't got enough cash or bet is less than last. Enter new bet: : ");
                        newBet = scanner.nextInt();
                    }
                    players[i].raise(newBet);
                    break;
                default:
                    System.out.println("Impossible action");
            }
        }
    }
}
