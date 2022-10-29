package ru.vsu.csf.poker.interfaces;

import ru.vsu.csf.poker.model.Player;

public interface GameInOut {

    void oneBet(Player[] players, int i);

    void botsDecisions(Player[] players, int i);

    void playerDecisions(Player[] players, int i);
}
