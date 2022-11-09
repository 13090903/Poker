package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.GraphicsCardType;
import ru.vsu.csf.poker.enums.Rank;
import ru.vsu.csf.poker.enums.Suit;
import ru.vsu.csf.poker.graphics.components.Button;
import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class DrawPanel extends JPanel {

    public static class Slot {
        private final int x, y;
        private boolean free = true;

        public Slot(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private final List<Slot> playerSlots = new ArrayList<>();
    private final List<GraphicsPlayer> players = new ArrayList<>();

    private final CardsContainer cardsContainer = new CardsContainer(550, 160);
    private Toolbar toolbar;

    public DrawPanel() {
        setLayout(null);
        initPlayerSlots();
        initToolbar();

        initCardsContainer();

        cardsContainer.addCard(new GraphicsCard(new Card(Rank.ACE, Suit.DIAMONDS), GraphicsCardType.HIDDEN));
        cardsContainer.addCard(new GraphicsCard(new Card(Rank.QUEEN, Suit.DIAMONDS), GraphicsCardType.SHOWN));
        cardsContainer.addCard(new GraphicsCard(new Card(Rank.QUEEN, Suit.DIAMONDS), GraphicsCardType.SHOWN));
        cardsContainer.addCard(new GraphicsCard(new Card(Rank.QUEEN, Suit.DIAMONDS), GraphicsCardType.SHOWN));
        cardsContainer.addCard(new GraphicsCard(new Card(Rank.QUEEN, Suit.DIAMONDS), GraphicsCardType.SHOWN));

        generatePlayers(6);

        repaint();
    }

    private void initPlayerSlots() {
        playerSlots.add(new Slot(100, 80));
        playerSlots.add(new Slot(590, 80));
        playerSlots.add(new Slot(1080, 80));
        playerSlots.add(new Slot(100, 520));
        playerSlots.add(new Slot(590, 520));
        playerSlots.add(new Slot(1080, 520));
    }

    private void addPlayer(Player player, boolean self) {
        GraphicsPlayer newPlayer = new GraphicsPlayer(player, self);
        players.add(newPlayer);

        Slot freeSlot = null;
        for (Slot slot : playerSlots) {
            if (slot.free) {
                freeSlot = slot;
                freeSlot.free = false;
                break;
            }
        }

        newPlayer.setSlot(freeSlot);
        placePlayer(newPlayer);
    }

    private void initToolbar() {
        toolbar = new Toolbar();
        toolbar.setBounds(425, 700, toolbar.getWidth(), toolbar.getHeight());
        add(toolbar);
    }

    private void initCardsContainer() {
        cardsContainer.setBounds(425, 320, cardsContainer.getWidth(), cardsContainer.getHeight());
        add(cardsContainer);
    }

    public void generatePlayers(int n) {
        for (int i = 0; i < n; i++) {
            Player gamePlayer = new Player("f", 4000, null);
            gamePlayer.setHand(new Card[]{new Card(Rank.THREE, Suit.HEARTS), new Card(Rank.JACK, Suit.CLUBS)});
            addPlayer(gamePlayer, true);
        }
    }

    private void placePlayer(GraphicsPlayer player) {
        player.setBounds(player.getSlot().x, player.getSlot().y, player.getWidth(), player.getHeight());
        add(player);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

    }


}


