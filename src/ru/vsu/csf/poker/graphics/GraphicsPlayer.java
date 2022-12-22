package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.GraphicsCardType;
import ru.vsu.csf.poker.event.PlayerEvent;
import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Player;

import javax.swing.*;
import java.awt.*;

import static ru.vsu.csf.poker.Main.rb;

public class GraphicsPlayer extends JComponent {

    private String cashText = rb.getString("cash");
    private String myBetText = rb.getString("bet");

    private static Color DEFAULT_BORDER_COLOR;
    private static final Font DEFAULT_FONT = new Font("Times", Font.BOLD, 20);

    private Color borderColor;
    private PlayerEvent playerEvent;
    private JLabel cashLabel = new JLabel();
    private JLabel myBetLabel = new JLabel();

    private DrawPanel.Slot slot;
    private final int width = 220, height = 160 + 25 + 35;

    private final GraphicsCard[] hand = new GraphicsCard[2];
    private final boolean self;

    public GraphicsPlayer(boolean self) {
        this.self = self;
        setPreferredSize(new Dimension(width, height));

        if (self) {
            DEFAULT_BORDER_COLOR = Color.GREEN;
        } else {
            DEFAULT_BORDER_COLOR = Color.RED;
        }
        borderColor = DEFAULT_BORDER_COLOR;

        setLayout(new FlowLayout(FlowLayout.CENTER));

        repaint();
    }

    public void render(PlayerEvent playerEvent) {
        reset();
        renderCards(playerEvent.getHand());
        cashLabel.setText(playerEvent.getName() + " " + cashText + ": " + playerEvent.getCash());
        myBetLabel.setText(myBetText + ": " + playerEvent.getBet());
        cashLabel.setFont(DEFAULT_FONT);
        myBetLabel.setFont(DEFAULT_FONT);
        add(cashLabel);
        add(myBetLabel);

        repaint();
    }

    public void highlight() {
        borderColor = Color.YELLOW;
    }

    public void reset() {
        removeAll();
        if (self) {
            DEFAULT_BORDER_COLOR = Color.GREEN;
        } else {
            DEFAULT_BORDER_COLOR = Color.RED;
        }
        borderColor = DEFAULT_BORDER_COLOR;
    }

    public void renderCards(Card[] playerHand) {
        int i = 0;
        for (Card card : playerHand) {
            GraphicsCard graphicsCard = new GraphicsCard(card, self ? GraphicsCardType.SHOWN : GraphicsCardType.HIDDEN);
            add(graphicsCard);
            hand[i++] = graphicsCard;
        }
    }


    public void setSlot(DrawPanel.Slot slot) {
        this.slot = slot;
    }

    public DrawPanel.Slot getSlot() {
        return slot;
    }

    public GraphicsCard[] getHand() {
        return hand;
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBorder(BorderFactory.createLineBorder(borderColor, 2));
    }

    public PlayerEvent getPlayerEvent() {
        return playerEvent;
    }
}
