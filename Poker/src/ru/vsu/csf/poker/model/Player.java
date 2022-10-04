package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.PlayerState;
import ru.vsu.csf.poker.model.interfaces.PlayerActions;

import java.util.Arrays;
import java.util.Random;

public class Player implements PlayerActions, Comparable<Player>{
    private final Random rnd = new Random();
    protected String name;
    protected int cash;
    protected int bet;

    protected PlayerState state;
    protected CombinationPlusHighCard combination;
    protected Card[] hand;
    private final Table table;
    private final CombinationDeterminator cd = new CombinationDeterminator();

    public Player(String name, int cash, Table table) {
        this.name = name;
        this.cash = cash;
        this.table = table;
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
        table.setCurrentBet(newBet);
        bet = newBet;
    }

    @Override
    public void check() {
        state = PlayerState.CHECK;
    }

    public void checkCombination() {
        cd.checkCombination(this, table);
        combination = cd.getPlayerCombination();
    }

    public void generateHand(int cardsCounter, Deck deck) { // Сгенерировать 2 карты игрока
        Card[] hand = new Card[2];
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

    @Override
    public String toString() {
        return name + "{" +
                "cash=" + cash +
                ", hand=" + Arrays.toString(hand) +
                '}';
    }
    public int compareTo(Player p) {
        int result =  this.combination.combination.compareTo(p.combination.combination);
        if (result == 0) {
            result =  p.combination.highCard.compareTo(this.combination.highCard);
        }
        return result;
    }
}
