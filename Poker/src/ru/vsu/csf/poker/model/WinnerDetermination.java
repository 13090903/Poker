package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.combination.Combination;
import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.enums.PlayerState;

import java.util.*;


public class WinnerDetermination {
    public static Player[] determineTheWinner(Player[] players, Card[] table) {
        for (Player player : players) {
            if (!player.state.equals(PlayerState.FOLD)) {
                Combination.checkCombination(player, table);
            } else {
                player.combination = new CombinationPlusHighCard(Combinations.HIGH_CARD, 0);
            }
        }
        Player[] newPlayers = Arrays.copyOf(players, players.length);
        Arrays.sort(newPlayers);

        int winCounter = 1;
        for (int i = 1; i < players.length; i++) {
            if (newPlayers[i].combination.combination.getStrength() == newPlayers[i - 1].combination.combination.getStrength()) {
                if (Objects.equals(newPlayers[i].combination.highCard, newPlayers[i - 1].combination.highCard)) {
                    winCounter += 1;
                }
            }
        }

        return Arrays.copyOfRange(newPlayers, 0, winCounter);
    }
}
