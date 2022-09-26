package ru.vsu.csf.poker;

public class Player {
    private int cash;


    public Player(int cash) {
        this.cash = cash;
    }

    public static void fold() {

    }

    public static void call() {

    }

    public static void raise() {

    }

    public static void check() {

    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
