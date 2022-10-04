package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.PlayerState;

import java.util.Arrays;
import java.util.Objects;

public class WinnerDeterminator {
    private final Player[] players;

    public WinnerDeterminator(Player[] players) {
        this.players = players;
    }

    public Player[] determineTheWinner() {
        for (Player player : players) {
            if (!player.state.equals(PlayerState.FOLD)) {
                player.checkCombination();
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
