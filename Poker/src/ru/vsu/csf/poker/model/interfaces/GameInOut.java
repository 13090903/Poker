package ru.vsu.csf.poker.model.interfaces;

import ru.vsu.csf.poker.model.Player;
import ru.vsu.csf.poker.model.Table;

public interface GameInOut {

    void oneBet(Player[] players, int i, Table table);

    void botsDecisions(Player[] players, int i, Table table);

    void playerDecisions(Player[] players, int i, Table table);
}
