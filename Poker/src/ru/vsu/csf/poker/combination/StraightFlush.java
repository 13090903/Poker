package ru.vsu.csf.poker.combination;

import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.enums.Suit;
import ru.vsu.csf.poker.model.CombinationPlusHighCard;

import java.util.Map;

public class StraightFlush {
    public static CombinationPlusHighCard isStraightFlush(Map<Integer, Integer> rankMap, Map<Suit, Integer> suitMap, Map<Integer, Suit> rankSuitMap) {
        int counterStraightCards = 1;
        int[] rankOfComboCards = new int[7];// Какие карты образуют комбинацию, для определения старшей карты комбинации
        int[] cardsRank = new int[7];
        int cardsDiff;
        int i = 0;
        int highCardIndex = 0;
        Suit combinationSuit = null;
        for (Map.Entry<Suit, Integer> entry : suitMap.entrySet()) {
            if (entry.getValue() >= 5) {
                combinationSuit = entry.getKey();
                break;
            }
        }
        for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
            cardsRank[i] = entry.getKey();
            i++;
        }
        for (int j = cardsRank.length - 1; j >= 1; j--) {
            if (cardsRank[cardsRank.length - 1] == 14 && cardsRank[0] == 2 && j == 1) {
                counterStraightCards += 1;
            }
            cardsDiff = Math.abs(cardsRank[j] - cardsRank[j - 1]);
            if (cardsDiff == 1 || cardsDiff == 12) {
                if (rankSuitMap.get(cardsRank[j - 1]).equals(combinationSuit) && rankSuitMap.get(cardsRank[j]).equals(combinationSuit)) {
                    counterStraightCards += 1;
                    rankOfComboCards[j] = cardsRank[j];
                }
            } else if (counterStraightCards < 5) {
                counterStraightCards = 1;
                if (j != cardsRank.length - 1) {
                    rankOfComboCards[j + 1] = 0;
                }
            }
        }
        if (counterStraightCards >= 5 && !(cardsRank[cardsRank.length - 1] == 14 && cardsRank[0] == 2)) {
            for (int k = rankOfComboCards.length - 1; k >= 0; k--) {
                if (rankOfComboCards[k] != 0) {
                    highCardIndex = k;
                    return new CombinationPlusHighCard(Combinations.STRAIGHT_FLUSH, rankOfComboCards[highCardIndex]);
                }
            }
        }
        if (counterStraightCards >= 5 && (cardsRank[cardsRank.length - 1] == 14 && cardsRank[0] == 2)) {
            for (int k = 0; k < rankOfComboCards.length; k++) {
                if (rankOfComboCards[k] == 0 && k != 0) {
                    highCardIndex = k - 1;
                    break;
                }
            }
            return new CombinationPlusHighCard(Combinations.STRAIGHT_FLUSH, rankOfComboCards[highCardIndex]);
        }
        return new CombinationPlusHighCard(Combinations.HIGH_CARD, cardsRank[cardsRank.length - 1]);
    }
}
