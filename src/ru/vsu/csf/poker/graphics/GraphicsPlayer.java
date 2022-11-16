package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.GraphicsCardType;
import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static ru.vsu.csf.poker.Main.rb;

public class GraphicsPlayer extends JComponent {

    private String cashText = rb.getString("cash");

    private static Color DEFAULT_BORDER_COLOR;
    private static final Font DEFAULT_FONT = new Font("Times", Font.BOLD, 20);

    private Color borderColor;
    private Player player;
    private JLabel cashLabel = new JLabel();

    private DrawPanel.Slot slot;
    private final int width = 220, height = 160 + 25;

    private final GraphicsCard[] hand = new GraphicsCard[2];
    private final boolean self;

    public GraphicsPlayer(Player player, boolean self) {
        this.player = player;
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

    public void render() {
        reset();
        renderCards();
        cashLabel.setText( player.getName() + " " + cashText + ": " + player.getCash());
        cashLabel.setFont(DEFAULT_FONT);
        add(cashLabel);

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

    public void renderCards() {
        int i = 0;
        for (Card card : player.getHand()) {
            GraphicsCard graphicsCard = new GraphicsCard(card, self ? GraphicsCardType.SHOWN : GraphicsCardType.HIDDEN);
            add(graphicsCard);
            hand[i++] = graphicsCard;
        }
    }

    public Player getPlayer() {
        return player;
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
}
