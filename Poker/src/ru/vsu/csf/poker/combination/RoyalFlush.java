package ru.vsu.csf.poker.combination;

import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.enums.Suit;

import java.util.Map;

public class RoyalFlush {
    public static Combinations isRoyalFlush(Map<Integer, Integer> rankMap, Map<Suit, Integer> suitMap, Map<Integer, Suit> rankSuitMap) {
        int counterTenToAce = 0;
        Suit combinationSuit = null;
        for (Map.Entry<Suit, Integer> entry : suitMap.entrySet()) {
            if (entry.getValue() >= 5) {
                combinationSuit = entry.getKey();
                break;
            }
        }
        for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
            if (entry.getKey() >= 10 && entry.getKey() <= 14) {
                if (rankSuitMap.get(entry.getKey()).equals(combinationSuit)) {
                    counterTenToAce += 1;
                }
            }
        }
        if (counterTenToAce == 5) {
            return Combinations.ROYAL_FLUSH;
        }
        return null;
    }

}
