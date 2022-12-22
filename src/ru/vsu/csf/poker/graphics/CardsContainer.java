package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.event.TableEvent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CardsContainer extends JComponent {

    private final List<GraphicsCard> cards = new ArrayList<>();
    private int width, height;

    public CardsContainer(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createLineBorder(Color.black, 2));
    }

    public void addCard(GraphicsCard card) {
        cards.add(card);
        add(card);
        repaint();
    }

    public void clear() {
        cards.clear();
        removeAll();
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
