package ru.vsu.csf.poker.model.interfaces;

import ru.vsu.csf.poker.model.Move;

public interface GameUI {
    default Move prompt(String message) {
        showMessage(message);
        return null;
    }

    Move prompt();

    void showGameState(int playerBet, int currBet, int bank);

    void showMessage(String message);
}
