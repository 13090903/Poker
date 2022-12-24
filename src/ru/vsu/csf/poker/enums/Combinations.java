package ru.vsu.csf.poker.enums;

import java.util.Objects;

import static ru.vsu.csf.poker.Main.rb;

public enum Combinations{
    ROYAL_FLUSH(9, rb.getString("ROYAL_FLUSH")),
    STRAIGHT_FLUSH(8, rb.getString("STRAIGHT_FLUSH")),
    FOUR_OF_A_KIND(7, rb.getString("FOUR_OF_A_KIND")),
    FULL_HOUSE(6, rb.getString("FULL_HOUSE")),
    FLUSH(5, rb.getString("FLUSH")),
    STRAIGHT(4, rb.getString("STRAIGHT")),
    THREE_OF_A_KIND(3, rb.getString("THREE_OF_A_KIND")),
    TWO_PAIRS(2, rb.getString("TWO_PAIRS")),
    PAIR(1, rb.getString("PAIR")),
    HIGH_CARD(0, rb.getString("HIGH_CARD"));

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

    public static Combinations valueOf(String s, int dummy) {
        for (Combinations c : values()) {
            if (Objects.equals(c.name, s)) {
                return c;
            }
        }
        return null;
    }
}
