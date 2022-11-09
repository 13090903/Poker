package ru.vsu.csf.poker.graphics.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Button extends JButton {

    private static final Color DEFAULT_COLOR = new Color(31, 31, 16);

    public Button(Color color, String text) {
        super(text);
        setBackground(color);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setForeground(Color.WHITE);
        setFocusable(false);
    }

    public Button(String text) {
        this(DEFAULT_COLOR, text);
    }
}
