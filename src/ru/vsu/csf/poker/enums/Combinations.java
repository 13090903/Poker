package ru.vsu.csf.poker.enums;

public enum Combinations{
    ROYAL_FLUSH(9, "Royal flush"),
    STRAIGHT_FLUSH(8, "Straight flush"),
    FOUR_OF_A_KIND(7, "For of a kind"),
    FULL_HOUSE(6, "Full house"),
    FLUSH(5, "Flush"),
    STRAIGHT(4, "Straight"),
    THREE_OF_A_KIND(3, "Three of a kind"),
    TWO_PAIRS(2, "Two pairs"),
    PAIR(1, "Pair"),
    HIGH_CARD(0, "High card");

    private final int strength;
    private final String name;

    Combinations(int strength, String name) {
        this.strength = strength;
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public String toString() {
        return name;
    }
}
