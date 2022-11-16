package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.MoveType;
import ru.vsu.csf.poker.graphics.components.MyButton;
import ru.vsu.csf.poker.interfaces.GameUI;
import ru.vsu.csf.poker.model.*;

import java.awt.*;
import java.util.Arrays;

import static ru.vsu.csf.poker.graphics.components.MyButton.DEFAULT_COLOR;

public class GraphicsGameUI implements GameUI {
    private DrawPanel panel;

    public GraphicsGameUI(DrawPanel panel) {
        this.panel = panel;
    }

    @Override
    public Move prompt() {
        MyButton fold = panel.getToolbar().getButtons().get(0);
        MyButton call = panel.getToolbar().getButtons().get(1);
        MyButton check = panel.getToolbar().getButtons().get(2);
        MyButton raise = panel.getToolbar().getButtons().get(3);
        fold.setBackground(DEFAULT_COLOR);
        call.setBackground(DEFAULT_COLOR);
        check.setBackground(DEFAULT_COLOR);
        raise.setBackground(DEFAULT_COLOR);
        final Move[] move = new Move[1];
        fold.addActionListener(e -> {
            move[0] = new Move(MoveType.FOLD);
            fold.setBackground(Color.BLUE);
            call.setBackground(DEFAULT_COLOR);
            check.setBackground(DEFAULT_COLOR);
            raise.setBackground(DEFAULT_COLOR);
        });
        call.addActionListener(e -> {
            move[0] = new Move(MoveType.CALL);
            call.setBackground(Color.BLUE);
            fold.setBackground(DEFAULT_COLOR);
            check.setBackground(DEFAULT_COLOR);
            raise.setBackground(DEFAULT_COLOR);
        });
        check.addActionListener(e -> {
            move[0] = new Move(MoveType.CHECK);
            check.setBackground(Color.BLUE);
            call.setBackground(DEFAULT_COLOR);
            fold.setBackground(DEFAULT_COLOR);
            raise.setBackground(DEFAULT_COLOR);
        });
        raise.addActionListener(e -> {
            move[0] = new Move(MoveType.RAISE, Integer.parseInt(panel.getRaiseBet().getText()));
            raise.setBackground(Color.BLUE);
            call.setBackground(DEFAULT_COLOR);
            check.setBackground(DEFAULT_COLOR);
            fold.setBackground(DEFAULT_COLOR);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return move[0];
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
