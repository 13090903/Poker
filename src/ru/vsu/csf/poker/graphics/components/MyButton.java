package ru.vsu.csf.poker.graphics.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MyButton extends JButton {

    public static final Color DEFAULT_COLOR = new Color(31, 31, 16);

    public MyButton(Color color, String text) {
        super(text);
        setBackground(color);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setForeground(Color.WHITE);
        setFocusable(false);
        if (this.isCursorSet()) {
            setBackground(Color.RED);
        }
    }

    public MyButton(String text) {
        this(DEFAULT_COLOR, text);
    }

}
