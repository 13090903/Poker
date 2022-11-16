package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.GraphicsCardType;
import ru.vsu.csf.poker.enums.Rank;
import ru.vsu.csf.poker.enums.Suit;
import ru.vsu.csf.poker.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static ru.vsu.csf.poker.Main.rb;


public class DrawPanel extends JPanel {

    private String bankText = rb.getString("bank");
    private String currBetText = rb.getString("currBet");
    private String winnersText = rb.getString("winners");
    private String withCombText = rb.getString("withComb");

    private final Font DEFAULT_FONT = new Font("Times", Font.BOLD, 20);

    private JLabel bankLabel = new JLabel();
    private JLabel currentBetLabel = new JLabel();
    private JLabel winnersLabel = new JLabel();
    private JTextArea raiseBet = new JTextArea();

    public static class Slot {
        private final int x, y;
        private boolean free = true;

        public Slot(int x, int y) {
            this.x = x;
            this.y = y;

        }
    }

    private Game game;

    private final List<Slot> playerSlots = new ArrayList<>();
    private final List<GraphicsPlayer> players = new ArrayList<>();

    private final CardsContainer cardsContainer = new CardsContainer(550, 160);
    private Toolbar toolbar;

    public DrawPanel() {
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        initPlayerSlots();
        initToolbar();

        initCardsContainer();
        initLabels();

        initBotGame(3);

        repaint();
    }

    private void initLabels() {
        bankLabel.setBounds(1000, 350, 300, 50);
        currentBetLabel.setBounds(1000, 400, 300, 50);
        winnersLabel.setBounds(25, 325, 350, 150);
        raiseBet.setBounds(1000, 720, 100, 30);
        winnersLabel.setFont(new Font("Times", Font.BOLD, 30));
        raiseBet.setFont(DEFAULT_FONT);
        winnersLabel.setForeground(Color.BLUE);
        bankLabel.setFont(DEFAULT_FONT);
        currentBetLabel.setFont(DEFAULT_FONT);
        add(winnersLabel);
        add(bankLabel);
        add(currentBetLabel);
        add(raiseBet);
    }

    private void initBotGame(int n) {
        game = new Game();
        game.setCallback(id -> {
            if (id != null) {
                for (GraphicsPlayer graphicsPlayer : players) {
                    if (graphicsPlayer.getPlayer().getId().equals(id)) {
                        graphicsPlayer.highlight();
                    }
                }
                return;
            }

            update();
        });
        Player player = new Player("Player", 4000, null, new GraphicsGameUI(this));
        game.addPlayer(player);
        addPlayer(player, true);
        for (int i = 0; i < n; i++) {
            Bot bot = new Bot("Bot" + i, 4000, null);
            game.addPlayer(bot);
            addPlayer(bot, false);
        }
        game.start();
    }

    private void initPlayerSlots() {
        playerSlots.add(new Slot(100, 80));
        playerSlots.add(new Slot(590, 80));
        playerSlots.add(new Slot(1080, 80));
        playerSlots.add(new Slot(100, 520));
        playerSlots.add(new Slot(590, 520));
        playerSlots.add(new Slot(1080, 520));
    }

    private void update() {
        for (GraphicsPlayer player : players) {
            player.render();
        }
        cardsContainer.clear();
        winnersLabel.setText("");
        for (Card card : game.getTable().getCards()) {
            cardsContainer.addCard(new GraphicsCard(card, GraphicsCardType.SHOWN));
        }


        bankLabel.setText(bankText + ": " + game.getTable().getBank());
        currentBetLabel.setText(currBetText + ": " + game.getTable().getCurrentBet());

        if (game.getRoundWinners().size() != 0) {
            StringBuilder s = new StringBuilder(winnersText + ": ");
            StringBuilder s1 = new StringBuilder(withCombText + ": ");
            for (Player player : game.getRoundWinners()) {
                s.append(player.getName()).append(" ");
            }
            s1.append(game.getRoundWinners().get(0).getCombination().getCombination());
            winnersLabel.setText("<html>" + s + "<br>" + s1 + "</html>");
            for (GraphicsPlayer player : players) {
                player.getHand()[0].setType(GraphicsCardType.SHOWN);
                player.getHand()[1].setType(GraphicsCardType.SHOWN);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        revalidate();

        repaint();
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
        toolbar.setBounds(425, 705, toolbar.getWidth(), toolbar.getHeight());
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

    public Toolbar getToolbar() {
        return toolbar;
    }

    public JTextArea getRaiseBet() {
        return raiseBet;
    }
}


