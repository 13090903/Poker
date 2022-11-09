package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.interfaces.GameUI;
import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Move;
import ru.vsu.csf.poker.model.Player;
import ru.vsu.csf.poker.model.Table;

public class GraphicsGameUI implements GameUI {
    @Override
    public Move prompt() {
        return null;
    }

    @Override
    public void showGameState(int playerBet, int currBet, int bank) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showTable(Table table) {
//        int ind = 0;
//        for (int i = 0; i < table.getTable().length; i++) {
//            if (table.getTable()[i] == null) {
//                ind = i;
//            }
//        }
//
//        for (int i = 0; i < ind; i++) {
//            panel.drawCard(panel.g, table.getTable()[i], table.getCoords()[i].getX(), table.getCoords()[i].getY());
//        }
    }

    @Override
    public void showHand(Card[] cards) {
//        for (int i = 0; i < player.getHand().length; i++) {
//            panel.drawCard(panel.g, player.getHand()[i], player.getCoords()[i].getX(), player.getCoords()[i].getY());
//        }
    }
}
