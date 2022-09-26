package ru.vsu.csf.poker.enums;

public enum Suit {
    DIAMONDS ("♦"),
    HEARTS ("♥"),
    SPADES ("♠"),
    CLUBS ("♣");

    private final String pic;

    Suit(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return pic;
    }
}
