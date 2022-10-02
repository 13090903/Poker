package ru.vsu.csf.poker.combination;

import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.model.CombinationPlusHighCard;

import java.util.Map;

public class MultipleCards {
    public static CombinationPlusHighCard multiplicityOfCards(Map<Integer, Integer> rankMap) {
        int pairsCounter = 0;
        int threeCardsCounter = 0;
        int highPairCard = 0;
        int highSetCard = 0;
        int highCard = 0;
        for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
            if (entry.getValue() == 2) {
                pairsCounter += 1;
                if (entry.getKey() > highPairCard) {
                    highPairCard = entry.getKey();
                }
            }
            if (entry.getValue() == 3) {
                threeCardsCounter += 1;
                if (entry.getKey() > highSetCard) {
                    highSetCard = entry.getKey();
                }
            }
            if (entry.getValue() == 4) {
                highCard = entry.getKey();
                return new CombinationPlusHighCard(Combinations.FOUR_OF_A_KIND, highCard);
            }
            highCard = entry.getKey();
        }
        if (threeCardsCounter == 0 && pairsCounter == 1) {
            return new CombinationPlusHighCard(Combinations.PAIR, highPairCard);
        }
        if (threeCardsCounter == 0 && pairsCounter >= 2) {
            return new CombinationPlusHighCard(Combinations.TWO_PAIRS, highPairCard);
        }
        if (threeCardsCounter >= 1 && pairsCounter == 0) {
            return new CombinationPlusHighCard(Combinations.THREE_OF_A_KIND, highSetCard);
        }
        if (threeCardsCounter == 1 && pairsCounter == 1) {
            return new CombinationPlusHighCard(Combinations.FULL_HOUSE, highSetCard * 10 + highPairCard);
        }
        return new CombinationPlusHighCard(Combinations.HIGH_CARD, highCard);
    }
}
