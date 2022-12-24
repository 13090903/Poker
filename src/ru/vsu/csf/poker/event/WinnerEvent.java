package ru.vsu.csf.poker.event;

import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WinnerEvent {
    private List<String> winnersNames;
    Combinations combination;

    public WinnerEvent(List<String> winnersNames, Combinations combination) {
        this.winnersNames = winnersNames;
        this.combination = combination;
    }

    public WinnerEvent(List<Player> roundWinners) {
        List<String> names = new ArrayList<>();
        for (Player p : roundWinners) {
            names.add(p.getName());
        }
        this.winnersNames = names;
        this.combination = roundWinners.get(0).getCombination().getCombination();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String name : winnersNames) {
            sb.append(name).append(" ");
        }
        sb = new StringBuilder(sb.toString().trim());
        sb.append("|").append(combination.toString());
        return sb.toString();
    }

    public List<String> getWinnersNames() {
        return winnersNames;
    }

    public Combinations getCombination() {
        return combination;
    }
}
