package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.GameStages;
import ru.vsu.csf.poker.enums.PlayerState;
import ru.vsu.csf.poker.model.interfaces.GameInOut;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DecisionMaker {
    //todo разделить на принятие решений и ввод вывод

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
        if ((table.amountOfFolds >= players.length - 1)) {
            if (table.amountOfFolds == players.length - 1) {
                for (Player player : players) {
                    if (player.getState() != PlayerState.FOLD) {
                        player.setCash(player.getCash() + table.getBank());
                    }
                }
            }
            if (table.amountOfFolds == players.length) {
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

    public int bettingCircle(Player[] players, GameStages stage, Table table) { // Один ставочный круг
        boolean flag = false;
        while (!betIsTheSame(players) || !everybodyChecks(players)) {
            if (everybodyAllIn(players)) {
                break;
            }
            for (int i = 0; i < players.length; i++) {
                if (players[i].getState().equals(PlayerState.FOLD)) {
                    continue;
                }
                if (endGameConditions(players)) {
                    return 1;
                }
                players[i].move();
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
