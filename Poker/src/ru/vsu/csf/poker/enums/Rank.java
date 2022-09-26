package ru.vsu.csf.poker.enums;

public enum Rank {
    TWO (2, "2"),
    THREE (3, "3"),
    FOUR (4, "4"),
    FIVE (5, "5"),
    SIX (6, "6"),
    SEVEN (7, "7"),
    EIGHT (8, "8"),
    NINE (9, "9"),
    TEN (10, "10"),
    JACK (11, "J"),
    QUEEN (12, "Q"),
    KING (13, "K"),
    ACE (14, "A");

    private final int rank;
    private final String name;

    Rank(int rank, String name) {
        this.rank = rank;
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return name;
    }
}
