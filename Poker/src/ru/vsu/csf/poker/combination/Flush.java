package ru.vsu.csf.poker.combination;

import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.enums.Suit;
import ru.vsu.csf.poker.model.CombinationPlusHighCard;

import java.util.Map;

public class Flush {
    public static CombinationPlusHighCard isFlash(Map<Suit, Integer> suitMap, Map<Integer, Suit> rankSuitMap) {
        int highCard = 0;
        Suit combinationSuit = null;
        for (Map.Entry<Suit, Integer> entry : suitMap.entrySet()) {
            if (entry.getValue() >= 5) {
                combinationSuit = entry.getKey();
            }
        }
        for (Map.Entry<Integer, Suit> entry : rankSuitMap.entrySet()) {
            if (entry.getValue().equals(combinationSuit) && entry.getKey() > highCard) {
                highCard = entry.getKey();
            }
        }
        if (combinationSuit != null) {
            return new CombinationPlusHighCard(Combinations.FLUSH, highCard);
        }
        return new CombinationPlusHighCard(Combinations.HIGH_CARD, highCard);
    }
}
