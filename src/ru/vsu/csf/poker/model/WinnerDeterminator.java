package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.PlayerState;

import java.util.*;

public class WinnerDeterminator {
    private final Player[] players;

    public WinnerDeterminator(List<Player> players) {
        this.players = players.toArray(new Player[0]);
    }

    public Player[] determineTheWinner() {
        List<Player> playersToCompare = new ArrayList<>();
        for (Player player : players) {
            if (!player.state.equals(PlayerState.FOLD)) {
                player.checkCombination();
                playersToCompare.add(player);
            }
        }
        Collections.sort(playersToCompare);

        int winCounter = 1;
        for (int i = 1; i < playersToCompare.size(); i++) {
            if (playersToCompare.get(i).combination.combination.getStrength() == playersToCompare.get(0).combination.combination.getStrength()) {
                if (Objects.equals(playersToCompare.get(i).combination.highCard, playersToCompare.get(0).combination.highCard)) {
                    winCounter += 1;
                }
            }
        }

        Player[] winners = new Player[winCounter];
        for (int i = 0; i < winCounter; i++) {
            winners[i] = playersToCompare.get(i);
        }
        return winners;
    }
}
