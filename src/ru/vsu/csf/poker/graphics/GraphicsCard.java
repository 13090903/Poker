package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.GraphicsCardType;
import ru.vsu.csf.poker.model.Card;

import javax.swing.*;
import java.awt.*;

public class GraphicsCard extends JComponent {

    private GraphicsCardType type;

    private final Image img;

    public GraphicsCard(Card card, GraphicsCardType type) {
        String s = "src/img/" + card.getRank().toString() + card.getSuitName() + ".png";
        this.img = new ImageIcon(s).getImage();
        setPreferredSize(new Dimension(100, 145));
        this.type = type;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        if (type == GraphicsCardType.HIDDEN) {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, 100, 145);
        } else {
            g.drawImage(img, 0, 0, null);
        }
    }
}
