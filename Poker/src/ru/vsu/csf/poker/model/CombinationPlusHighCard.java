package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.Combinations;

public class CombinationPlusHighCard{
    public Combinations combination;
    public Integer highCard;

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
