package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.graphics.components.MyButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Toolbar extends JComponent {

    private final int width, height;
    private final MyButton fold, call, check, raise;
    private List<MyButton> myButtons = new ArrayList<>();

    public Toolbar() {
        width = 550;
        height = 100;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        Font font = new Font("Times", Font.BOLD, 24);
        int bWidth = 130;
        int bHeight = 50;

        fold = new MyButton("Fold");
        call = new MyButton("Call");
        check = new MyButton("Check");
        raise = new MyButton("Raise");
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

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public List<MyButton> getButtons() {
        return myButtons;
    }
}
