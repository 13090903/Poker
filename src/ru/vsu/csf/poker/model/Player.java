package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.PlayerState;
import ru.vsu.csf.poker.graphics.DrawPanel;
import ru.vsu.csf.poker.interfaces.GameUI;
import ru.vsu.csf.poker.interfaces.PlayerActions;
import ru.vsu.csf.poker.server.ClientHandler;

import java.util.*;

import static ru.vsu.csf.poker.Main.rb;

public class Player implements PlayerActions, Comparable<Player> {

    private final String yourTurn = rb.getString("yourTurn");
    private final String invalidInput = rb.getString("invalidInput");
    private final String dontCall = rb.getString("dontCall");
    private final String dontCheck = rb.getString("dontCheck");
    private final String notEnoughCash = rb.getString("notEnoughCash");
    private final String betLessThanLast = rb.getString("betLessThanLast");
    private final ClientHandler clientHandler;

    protected String name;
    protected int cash;
    protected int bet;

    private final UUID id = UUID.randomUUID();

    protected PlayerState state;
    protected CombinationPlusHighCard combination;
    protected Card[] hand = new Card[2];
    private Table table;
    private final CombinationDeterminator cd = new CombinationDeterminator(this, this.table);
    protected GameUI ui;

    public Player(String name, int cash, Table table, GameUI ui, ClientHandler clientHandler) {
        this.name = name;
        this.cash = cash;
        this.table = table;
        this.ui = ui;
        this.clientHandler = clientHandler;
    }

    public Player(String name, int cash, Table table, GameUI ui) {
        this(name, cash, table, ui, null);
    }

    public Player(String name, int cash, Table table) {
        this(name, cash, table, null);
    }

    @Override
    public void fold() {
        state = PlayerState.FOLD;
        table.setBank(table.getBank() + bet);
        cash -= bet;
    }

    @Override
    public void call() {
        bet = table.getCurrentBet();
    }

    public void blind() {
        bet = 100;
        table.setCurrentBet(bet);
    }

    @Override
    public void raise(int newBet) {
        if (cash < newBet) {
            table.setCurrentBet(cash);
            bet = cash;
        } else if (newBet <= table.getCurrentBet()) {
            table.setCurrentBet(bet + 100);
            bet += 100;
        } else {
            table.setCurrentBet(newBet);
            bet = newBet;
        }
    }


    @Override
    public void check() {
        state = PlayerState.CHECK;
    }

    public void makeMove() {
        if (clientHandler == null) {
            throw new RuntimeException("Player has no clientHandler");
        }

        Move move = clientHandler.requestMove();

        switch (move.getMoveType()) {
            case CALL -> {
                if (cash < table.getCurrentBet()) {
                    fold();
                }
                if (bet < table.getCurrentBet() || bet > table.getCurrentBet()) {
                    call();
                } else {
//                    ui.showMessage(dontCall);
                    check();
                }
            }
            case CHECK -> {
                if (bet == table.getCurrentBet()) {
                    check();
                } else {
//                    ui.showMessage(dontCheck);
                    call();
                }
            }
            case FOLD -> {
                fold();
                table.amountOfFolds += 1;
            }
            case RAISE -> {
                int newBet = move.getRaiseValue();
                if (cash < newBet) {
                    ui.showMessage(notEnoughCash);
                    raise(cash);
                } else if (newBet <= table.getCurrentBet()) {
                    ui.showMessage(betLessThanLast);
                    raise(table.getCurrentBet() + 100);
                    return;
                }
                raise(newBet);
            }
        }
//        ui.showGameState(bet, table.getCurrentBet(), table.getBank());
    }

    public void checkCombination() {
        combination = cd.getCombination();
    }

    public void generateHand(Deck deck) { // Сгенерировать 2 карты игрока
        for (int i = 0; i < 2; i++) {
            hand[i] = deck.pop();
        }
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public int getBet() {
        return bet;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "{" +
                "cash=" + cash +
                ", hand=" + Arrays.toString(hand) +
                '}';
    }

    public CombinationPlusHighCard getCombination() {
        return combination;
    }

    public int compareTo(Player p) {
        int result = this.combination.combination.compareTo(p.combination.combination);
        if (result == 0) {
            result = p.combination.highCard.compareTo(this.combination.highCard);
        }
        return result;
    }
}
