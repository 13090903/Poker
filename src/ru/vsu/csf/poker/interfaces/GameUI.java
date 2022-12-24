package ru.vsu.csf.poker.interfaces;

import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Move;
import ru.vsu.csf.poker.model.Table;

public interface GameUI {
    default Move ask(String message) {
        showMessage(message);
        return ask();
    }

    Move ask();

    void showGameState(int playerBet, int currBet, int bank);

    void showMessage(String message);

    void showTable(Table table);

    void showHand(Card[] cards);
}
