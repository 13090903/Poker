package ru.vsu.csf.poker.interfaces;

import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Move;

public interface GameUI {
    default Move prompt(String message) {
        showMessage(message);
        return prompt();
    }

    Move prompt();

    void showGameState(int playerBet, int currBet, int bank);

    void showMessage(String message);

    void showCards(Card[] cards);
}
