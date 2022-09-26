package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.enums.PlayerState;

import java.util.Arrays;

class CombinationPlusHighCard {
    protected Combinations combination;
    protected int highCard;

    @Override
    public String toString() {
        return "CombinationPlusHighCard{" +
                "combination=" + combination +
                ", highCard=" + highCard +
                '}';
    }

    public CombinationPlusHighCard(Combinations combination, int highCard) {
        this.combination = combination;
        this.highCard = highCard;


    }
}

public class Player {
    protected String name;
    protected int cash;
    protected int bet;

    protected PlayerState state;
    protected CombinationPlusHighCard combination;
    protected Card[] hand;

    public Player(String name, int cash) {
        this.name = name;
        this.cash = cash;
    }

    public void fold() {
        state = PlayerState.FOLD;
        Game.bank += bet;
        cash -= bet;
        bet = 0;
    }

    public void call() {
        bet = Game.getCurrentBet();
    }

    public void raise(int newBet) {
        Game.setCurrentBet(newBet);
        bet = newBet;
    }

    public void check() {
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

    @Override
    public String toString() {
        return name + "{" +
                "cash=" + cash +
                ", hand=" + Arrays.toString(hand) +
                '}';
    }
}
