package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.graphics.components.Button;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Toolbar extends JComponent {

    private final int width, height;
    private final Button fold, call, check, raise;
    private List<Button> buttons;

    public Toolbar() {
        width = 550;
        height = 100;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        Font font = new Font("Times", Font.BOLD, 24);
        int bWidth = 130;
        int bHeight = 50;

        fold = new Button("Fold");
        call = new Button("Call");
        check = new Button("Check");
        raise = new Button("Raise");
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
}
