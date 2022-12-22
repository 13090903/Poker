package ru.vsu.csf.poker.enums;

import java.util.Objects;

public enum Suit {
    DIAMONDS("♦"),
    HEARTS("♥"),
    SPADES("♠"),
    CLUBS("♣");

    private final String pic;

    Suit(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return pic;
    }

    public static Suit valueOf(String s, int dummy) {
        for (Suit su : values()) {
            if (Objects.equals(su.pic, s)) {
                return su;
            }
        }
        return null;
    }
}
