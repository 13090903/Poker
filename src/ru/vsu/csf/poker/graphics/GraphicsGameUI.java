package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.MoveType;
import ru.vsu.csf.poker.graphics.components.ToolbarButton;
import ru.vsu.csf.poker.interfaces.GameUI;
import ru.vsu.csf.poker.model.*;

public class GraphicsGameUI implements GameUI {
    private final DrawPanel panel;
    private Move move;

    public GraphicsGameUI(DrawPanel panel) {
        this.panel = panel;
    }

    @Override
    public Move ask() {
        move = null;
        panel.resetHighlightButton();
        for (ToolbarButton button : panel.getToolbar().getButtons()) {
            button.addActionListener(e -> {
                panel.getToolbar().recountRaiseBet();
                panel.highlightButton((ToolbarButton) e.getSource());
                move = button.getMoveType();
            });
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return move;
    }

    @Override
    public void showGameState(int playerBet, int currBet, int bank) {
    }

    @Override
    public void showMessage(String message) {
    }

    @Override
    public void showTable(Table table) {
    }

    @Override
    public void showHand(Card[] cards) {
    }


}
