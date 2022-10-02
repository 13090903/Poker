package ru.vsu.csf.poker.decision;

import ru.vsu.csf.poker.enums.PlayerState;
import ru.vsu.csf.poker.model.Player;

import java.util.Random;

import static ru.vsu.csf.poker.model.Game.getCurrentBet;

public class BotDecisions {
    private static final Random rnd = new Random();

    public static void botsDecisions(Player[] players, int i) {// Все решения, принимаемые ботами
        if (players[i].getState().equals(PlayerState.FOLD)) {
            return;
        }
        int decision = 1 + rnd.nextInt(90);
        if (decision == 1 && i != 0) {
            int newBet = getCurrentBet() + 100 + (rnd.nextInt(getCurrentBet() + 100) / 100) * 100;
            while (players[i].getCash() <= newBet) {
                newBet = getCurrentBet() + 100 + (rnd.nextInt(getCurrentBet()) / 100) * 100;
            }
            players[i].raise(newBet);
            System.out.printf("Player %d raises to %d\n", i, players[i].getBet());
        }
        if (decision == 2 && i != 0) {
            players[i].fold();
            System.out.printf("Player %d folds\n", i);
            Decisions.amountOfFolds += 1;
            return;
        }
        if (decision > 2 && i != 0) {
            if (getCurrentBet() > players[i].getBet()) {
                if (getCurrentBet() <= players[i].getCash()) {
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
        System.out.println("Current bet is " + getCurrentBet());
    }
}
