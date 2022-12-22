package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.GraphicsCardType;
import ru.vsu.csf.poker.event.PlayerEvent;
import ru.vsu.csf.poker.event.TableEvent;
import ru.vsu.csf.poker.graphics.components.ToolbarButton;
import ru.vsu.csf.poker.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static ru.vsu.csf.poker.Main.rb;
import static ru.vsu.csf.poker.graphics.components.ToolbarButton.DEFAULT_COLOR;


public class DrawPanel extends JPanel {
    Map<UUID, GraphicsPlayer> graphicsPlayerMap = new HashMap<>();
    private final ServerConnection serverConnection;

    private final String bankText = rb.getString("bank");
    private final String currBetText = rb.getString("currBet");
    private final String winnersText = rb.getString("winners");
    private final String withCombText = rb.getString("withComb");

    private final Font DEFAULT_FONT = new Font("Times", Font.BOLD, 20);

    private final JLabel bankLabel = new JLabel();
    private final JLabel currentBetLabel = new JLabel();
    private final JLabel winnersLabel = new JLabel();
    private final JTextArea raiseBet = new JTextArea();

    public static class Slot {
        private final int x, y;
        private boolean free = true;

        public Slot(int x, int y) {
            this.x = x;
            this.y = y;

        }
    }

    private final List<Slot> playerSlots = new ArrayList<>();

    private final CardsContainer cardsContainer = new CardsContainer(550, 160);
    private final Toolbar toolbar = new Toolbar(this);

    public DrawPanel() {
        serverConnection = new ServerConnection("localhost", 9999, this);
        new Thread(serverConnection).start();

        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        initPlayerSlots();
        initToolbar();

        initCardsContainer();
        initLabels();


        repaint();
    }

    private void initLabels() {
        bankLabel.setBounds(1000, 300, 300, 50);
        currentBetLabel.setBounds(1000, 350, 300, 50);
        winnersLabel.setBounds(25, 275, 350, 150);
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

//    private void initBotGame(int n) {
//        game = new Game();
//        game.setCallback(id -> {
//            if (id != null) {
//                for (GraphicsPlayer graphicsPlayer : players) {
//                    if (graphicsPlayer.getPlayerEvent().getId().equals(id)) {
//                        graphicsPlayer.highlight();
//                    }
//                }
//                return;
//            }
//            update();
//        });
//        Player player = new Player("Player", 4000, null, new GraphicsGameUI(this));
//        game.addPlayer(player);
//        addPlayer(true);
//        for (int i = 0; i < n; i++) {
//            Bot bot = new Bot("Bot" + i, 4000, null);
//            game.addPlayer(bot);
//            addPlayer(false);
//        }
//        game.start();
//    }

    private void initPlayerSlots() {
        playerSlots.add(new Slot(100, 30));
        playerSlots.add(new Slot(590, 30));
        playerSlots.add(new Slot(1080, 30));
        playerSlots.add(new Slot(100, 470));
        playerSlots.add(new Slot(590, 470));
        playerSlots.add(new Slot(1080, 470));
    }

    public void updatePlayer(PlayerEvent e) {
        graphicsPlayerMap.get(e.getId()).render(e);

        revalidate();
        repaint();
    }

    public void updateTable(TableEvent e) {
        bankLabel.setText(bankText + ": " + e.getBank());
        currentBetLabel.setText(currBetText + ": " + e.getCurrentBet());

        cardsContainer.clear();
        for (Card card : e.getTableCards()) {
            cardsContainer.addCard(new GraphicsCard(card, GraphicsCardType.SHOWN));
        }

        revalidate();
        repaint();
    }

//    private void update() {
//
//        if (game.getRoundWinners().size() != 0) {
//            StringBuilder s = new StringBuilder(winnersText + ": ");
//            StringBuilder s1 = new StringBuilder(withCombText + ": ");
//            for (Player player : game.getRoundWinners()) {
//                s.append(player.getName()).append(" ");
//            }
//            s1.append(game.getRoundWinners().get(0).getCombination().getCombination());
//            winnersLabel.setText("<html>" + s + "<br>" + s1 + "</html>");
//            for (GraphicsPlayer player : players) {
//                player.getHand()[0].setType(GraphicsCardType.SHOWN);
//                player.getHand()[1].setType(GraphicsCardType.SHOWN);
//            }
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        revalidate();
//
//        repaint();
//    }

    public void addPlayer(UUID id, boolean self) {
        GraphicsPlayer newGraphicsPlayer = new GraphicsPlayer(self);
        graphicsPlayerMap.put(id, newGraphicsPlayer);

        Slot freeSlot = null;
        for (Slot slot : playerSlots) {
            if (slot.free) {
                freeSlot = slot;
                freeSlot.free = false;
                break;
            }
        }

        newGraphicsPlayer.setSlot(freeSlot);
        placePlayer(newGraphicsPlayer);
    }

    private void initToolbar() {
        toolbar.setBounds(425, 705, toolbar.getWidth(), toolbar.getHeight());
        add(toolbar);
    }

    private void initCardsContainer() {
        cardsContainer.setBounds(425, 270, cardsContainer.getWidth(), cardsContainer.getHeight());
        add(cardsContainer);
    }

    public void highlightButton(ToolbarButton button) {
        for (ToolbarButton button1 : toolbar.getButtons()) {
            if (button1.equals(button)) {
                button1.highlight();
            } else {
                button1.setBackground(DEFAULT_COLOR);
                button1.setBorder(null);
            }
        }
    }

    public void resetHighlightButton() {
        for (ToolbarButton button1 : toolbar.getButtons()) {
            button1.setBackground(DEFAULT_COLOR);
            button1.setBorder(null);
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


