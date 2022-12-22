package ru.vsu.csf.poker.graphics.components;

import ru.vsu.csf.poker.model.Move;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ToolbarButton extends JButton {

    protected Move moveType;

    public static final Color DEFAULT_COLOR = new Color(31, 31, 16);
    public static final Color DEFAULT_HIGHLIGHT_COLOR = new Color(12, 66, 149);

    public ToolbarButton(Color color, String text, Move moveType) {
        super(text);
        setBackground(color);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setForeground(Color.WHITE);
        setFocusable(false);
        this.moveType = moveType;
    }

    public void highlight() {
        setBackground(DEFAULT_HIGHLIGHT_COLOR);
        setBorder(BorderFactory.createLineBorder(new Color(16, 13, 54), 3));
    }

    public ToolbarButton(String text, Move moveType) {
        this(DEFAULT_COLOR, text, moveType);
    }

    public Move getMoveType() {
        return moveType;
    }

    public void setMoveType(Move moveType) {
        this.moveType = moveType;
    }
}
