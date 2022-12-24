package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.MoveType;
import ru.vsu.csf.poker.graphics.components.ToolbarButton;
import ru.vsu.csf.poker.model.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Toolbar extends JComponent {

    private final int width, height;
    private ToolbarButton fold, call, check, raise;
    private final List<ToolbarButton> myButtons = new ArrayList<>();
    private final DrawPanel panel;
    private Move move;

    public Toolbar(DrawPanel panel) {
        this.panel = panel;
        width = 550;
        height = 100;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        Font font = new Font("Times", Font.BOLD, 24);
        int bWidth = 130;
        int bHeight = 50;

        fold = new ToolbarButton("Fold", new Move(MoveType.FOLD));
        call = new ToolbarButton("Call", new Move(MoveType.CALL));
        check = new ToolbarButton("Check", new Move(MoveType.CHECK));
        raise = new ToolbarButton("Raise", new Move(MoveType.RAISE, 0));
        myButtons.add(fold);
        myButtons.add(call);
        myButtons.add(check);
        myButtons.add(raise);
        fold.setPreferredSize(new Dimension(bWidth, bHeight));
        call.setPreferredSize(new Dimension(bWidth, bHeight));
        check.setPreferredSize(new Dimension(bWidth, bHeight));
        raise.setPreferredSize(new Dimension(bWidth, bHeight));
        fold.setFont(font);
        call.setFont(font);
        check.setFont(font);
        raise.setFont(font);


        add(fold);
        add(call);
        add(check);
        add(raise);

        repaint();

        for (ToolbarButton button : myButtons) {
            button.addActionListener(e -> {
                synchronized (this) {
                    recountRaiseBet();
                    panel.highlightButton((ToolbarButton) e.getSource());
                    move = button.getMoveType();
                    this.notifyAll();
                }
            });
        }

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void recountRaiseBet() {
        raise.setMoveType(new Move(MoveType.RAISE, panel.getRaiseBet().getText().equals("") ? 100 : Integer.parseInt(panel.getRaiseBet().getText())));
    }

    public Move waitForMove() {
        move = null;
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return move;
    }

    public List<ToolbarButton> getButtons() {
        return myButtons;
    }
}
