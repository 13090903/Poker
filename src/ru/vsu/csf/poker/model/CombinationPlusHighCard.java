package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.Combinations;

public class CombinationPlusHighCard{
    protected Combinations combination;
    protected Integer highCard;
    protected Integer[] hcNotInComb;

    @Override
    public String toString() {
        return "CombinationPlusHighCard{" +
                "combination=" + combination +
                ", highCard=" + highCard +
                '}';
    }

    public CombinationPlusHighCard(Combinations combination, int highCard, Integer[] hcNotInComb) {
        this.combination = combination;
        this.highCard = highCard;
        this.hcNotInComb = hcNotInComb;
    }
}
