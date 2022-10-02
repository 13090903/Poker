package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.PlayerState;

import java.util.Arrays;

public class Player implements PlayerActions, Comparable<Player>{
    protected String name;
    protected int cash;
    protected int bet;
    protected int totalBet;

    protected PlayerState state;
    protected CombinationPlusHighCard combination;
    protected Card[] hand;

    public Player(String name, int cash) {
        this.name = name;
        this.cash = cash;
    }

    @Override
    public void fold() {
        state = PlayerState.FOLD;
        Game.bank += bet;
        cash -= bet;
        bet = 0;
    }

    @Override
    public void call() {
        bet = Game.getCurrentBet();
    }

    public void blind() {
        bet = 100;
        Game.setCurrentBet(bet);
        totalBet += bet;
    }

    @Override
    public void raise(int newBet) {
        Game.setCurrentBet(newBet);
        bet = newBet;
    }

    @Override
    public void check() {
        totalBet += bet;
        state = PlayerState.CHECK;
    }

    public CombinationPlusHighCard getCombination() {
        return combination;
    }

    public void setCombination(CombinationPlusHighCard combination) {
        this.combination = combination;
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

    public int getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(int totalBet) {
        this.totalBet = totalBet;
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
