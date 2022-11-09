package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.GraphicsCardType;
import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Player;

import javax.swing.*;
import java.awt.*;

public class GraphicsPlayer extends JComponent {

    private DrawPanel.Slot slot;
    private final int width = 220, height = 160;

    private final GraphicsCard[] hand = new GraphicsCard[2];
    private final boolean self;

    public GraphicsPlayer(Player player, boolean self) {
        this.self = self;
        setPreferredSize(new Dimension(width, height));

        if (self) {
            setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        } else {
            setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }
        setLayout(new FlowLayout(FlowLayout.CENTER));

        int i = 0;
        for (Card card : player.getHand()) {
            GraphicsCard graphicsCard = new GraphicsCard(card, self ? GraphicsCardType.SHOWN : GraphicsCardType.HIDDEN);
            add(graphicsCard);
            hand[i++] = graphicsCard;
        }

        repaint();
    }

    public void setSlot(DrawPanel.Slot slot) {
        this.slot = slot;
    }

    public DrawPanel.Slot getSlot() {
        return slot;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
    }
}
