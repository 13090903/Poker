package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.Rank;
import ru.vsu.csf.poker.enums.Suit;

public class Card {
    protected Rank rank;
    protected Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return  rank + "" + suit;
    }
}
