package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.PlayerState;
import ru.vsu.csf.poker.model.interfaces.GameUI;
import ru.vsu.csf.poker.model.interfaces.PlayerActions;

import java.util.Arrays;
import java.util.Random;

public class Player implements PlayerActions, Comparable<Player> {
    private final Random rnd = new Random();
    protected String name;
    protected int cash;
    protected int bet;

    protected PlayerState state;
    protected CombinationPlusHighCard combination;
    protected Card[] hand;
    private Table table;
    private final CombinationDeterminator cd = new CombinationDeterminator(this, this.table);
    protected GameUI ui;

    public Player(String name, int cash, Table table, GameUI ui) {
        this.name = name;
        this.cash = cash;
        this.table = table;
        this.ui = ui;
    }

    public Player(String name, int cash, Table table) {
        this(name, cash, table, null);
    }

    @Override
    public void fold() {
        state = PlayerState.FOLD;
        table.setBank(table.getBank() + bet);
        cash -= bet;
        bet = 0;
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

    public void move() {
        ui.showGameState(bet, table.getCurrentBet(), table.getBank());
        Move move = ui.prompt();
        switch (move.getMoveType()) {
            case CALL -> {
                if (bet < table.getCurrentBet() || bet > table.getCurrentBet()) {
                    call();
                } else {
                    ui.showMessage("You don't need to call, your bet is current bet in game, so check");
                    check();
                }
            }
            case CHECK -> {
                if (bet == table.getCurrentBet()) {
                    check();
                } else {
                    ui.showMessage("You can't check, your bet isn't current bet in game, so call");
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
                    ui.showMessage("You haven't got enough cash, so you bet all");
                    raise(cash);
                } else if (newBet <= table.getCurrentBet()) {
                    ui.showMessage("Bet is less than last, so raise for 100");
                    raise(table.getCurrentBet() + 100);
                }
                raise(newBet);
            }
        }
        ui.showGameState(bet, table.getCurrentBet(), table.getBank());
    }

    public void checkCombination() {
        combination = cd.getCombination();
    }

    public void generateHand(Deck deck) { // Сгенерировать 2 карты игрока
        Card[] hand = new Card[2];
        int cardsCounter = deck.deck.size();
        int num = rnd.nextInt(cardsCounter);
        hand[0] = deck.deck.get(num);
        deck.deck.remove(num);
        num = rnd.nextInt(cardsCounter - 1);
        hand[1] = deck.deck.get(num);
        deck.deck.remove(num);
        this.setHand(hand);
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

    public void setBet(int bet) {
        this.bet = bet;
    }

    public Table getTable() {
        return table;
    }

    @Override
    public String toString() {
        return name + "{" +
                "cash=" + cash +
                ", hand=" + Arrays.toString(hand) +
                '}';
    }

    public int compareTo(Player p) {
        int result = this.combination.combination.compareTo(p.combination.combination);
        if (result == 0) {
            result = p.combination.highCard.compareTo(this.combination.highCard);
        }
        return result;
    }
}
