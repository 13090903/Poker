package ru.vsu.csf.poker.decision;

import ru.vsu.csf.poker.enums.PlayerState;
import ru.vsu.csf.poker.model.Game;
import ru.vsu.csf.poker.model.Player;

import java.util.Arrays;

import static ru.vsu.csf.poker.decision.BotDecisions.botsDecisions;
import static ru.vsu.csf.poker.decision.PlayerDecisions.oneBet;
import static ru.vsu.csf.poker.decision.PlayerDecisions.playerDecisions;
import static ru.vsu.csf.poker.model.Game.getCurrentBet;

public class Decisions {
    protected static int amountOfFolds = 0;

    public static int getAmountOfFolds() {
        return amountOfFolds;
    }

    public static void setAmountOfFolds(int amountOfFolds) {
        Decisions.amountOfFolds = amountOfFolds;
    }

    public static boolean everybodyChecks(Player[] players) {
        for (Player player : players) {
            if (player.getState() == PlayerState.DEFAULT) {
                return false;
            }
        }
        return true;
    }

    public static boolean endGameConditions(Player[] players) { // Условия, при которых раунд не может продолжаться
        if ((amountOfFolds >= players.length - 1)) {
            if (amountOfFolds == players.length - 1) {
                for (Player player : players) {
                    if (player.getState() != PlayerState.FOLD) {
                        player.setCash(player.getCash() + Game.getBank());
                    }
                }
            }
            if (amountOfFolds == players.length) {
                Game.setBank(0);
            }
            System.out.println("Round is over");
            System.out.println(Arrays.toString(players));
            return true;
        }
        return false;
    }

    public static boolean everybodyAllIn(Player[] players) {
        for (Player player : players) {
            if (player.getCash() != player.getBet()) {
                return false;
            }
        }
        return true;
    }

    public static boolean betIsTheSame(Player[] players) { // Ставка у всех одинаковая
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

    public static int bettingCircle(Player[] players, int circleNumber) { // Один ставочный круг
        boolean flag = false;
        while (!betIsTheSame(players) || !everybodyChecks(players)) {
            if (everybodyAllIn(players)) {
                break;
            }
            for (int i = 0; i < players.length; i++) {
                if (i == 0 && getCurrentBet() == 0 && circleNumber == 1) {
                    oneBet(players, i);
                }

                if (endGameConditions(players)) {
                    return 1;
                }

                if (i > 0) {
                    botsDecisions(players, i);
                    flag = true;
                }

                if (endGameConditions(players)) {
                    return 1;
                }

                if (i == 0 && flag) {
                    playerDecisions(players, i);
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
