package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.enums.Suit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CombinationDeterminator {

    private final Player player;
    private Table table;

    public CombinationDeterminator(Player player, Table table) {
        this.player = player;
        this.table = table;
    }

    public CombinationPlusHighCard getCombination() {
        table = player.getTable();
        CombinationPlusHighCard playerCombination;
        Map<Integer, Integer> rankMap = new HashMap<>(); // Ключ - ранг, значение - количество
        Map<Suit, Integer> suitMap = new HashMap<>(); // Ключ - масть, значение - количество
        Map<Integer, Suit> rankSuitMap = new HashMap<>(); // Ключ - ранг, значение - масть
        for (Card card : player.getHand()) {
            FillMaps(rankMap, suitMap, rankSuitMap, card);
        }
        for (Card card : table.cards) {
            FillMaps(rankMap, suitMap, rankSuitMap, card);
        }// Заполнили словари

        Integer[] hc = highCards(rankMap);

        if (Objects.equals(isRoyalFlush(rankMap, suitMap, rankSuitMap), Combinations.ROYAL_FLUSH)) {
            playerCombination = new CombinationPlusHighCard(Combinations.ROYAL_FLUSH, 14, hc);
            return playerCombination;
        }

        CombinationPlusHighCard straightFlush = isStraightFlush(rankMap, suitMap, rankSuitMap);
        if (straightFlush.combination.equals(Combinations.STRAIGHT_FLUSH)) {
            playerCombination = new CombinationPlusHighCard(Combinations.STRAIGHT_FLUSH, straightFlush.highCard, hc);
            return playerCombination;
        }

        CombinationPlusHighCard multiplicityCombo = multiplicityOfCards(rankMap);
        if (multiplicityCombo.combination.equals(Combinations.FOUR_OF_A_KIND)) {
            playerCombination = new CombinationPlusHighCard(Combinations.FOUR_OF_A_KIND, multiplicityCombo.highCard, hc);
            return playerCombination;
        }

        if (multiplicityCombo.combination.equals(Combinations.FULL_HOUSE)) {
            playerCombination = new CombinationPlusHighCard(Combinations.FULL_HOUSE, multiplicityCombo.highCard, hc);
            return playerCombination;
        }

        CombinationPlusHighCard flush = isFlush(suitMap, rankSuitMap);
        if (flush.combination.equals(Combinations.FLUSH)) {
            playerCombination = new CombinationPlusHighCard(Combinations.FLUSH, flush.highCard, hc);
            return playerCombination;
        }

        CombinationPlusHighCard straight = isStraight(rankMap);
        if (straight.combination.equals(Combinations.STRAIGHT)) {
            playerCombination = new CombinationPlusHighCard(Combinations.STRAIGHT, straight.highCard, hc);
            return playerCombination;
        }

        if (multiplicityCombo.combination.equals(Combinations.THREE_OF_A_KIND)) {
            playerCombination = new CombinationPlusHighCard(Combinations.THREE_OF_A_KIND, multiplicityCombo.highCard, hc);
            return playerCombination;
        }

        if (multiplicityCombo.combination.equals(Combinations.TWO_PAIRS)) {
            playerCombination = new CombinationPlusHighCard(Combinations.TWO_PAIRS, multiplicityCombo.highCard, hc);
            return playerCombination;
        }

        if (multiplicityCombo.combination.equals(Combinations.PAIR)) {
            playerCombination = new CombinationPlusHighCard(Combinations.PAIR, multiplicityCombo.highCard, hc);
            return playerCombination;
        }

        playerCombination = new CombinationPlusHighCard(Combinations.HIGH_CARD, highCard(rankMap), hc);
        return playerCombination;
    }

    private void FillMaps(Map<Integer, Integer> rankMap, Map<Suit, Integer> suitMap, Map<Integer, Suit> rankSuitMap, Card card) {
        if (rankMap.containsKey(card.getRank().getRank())) {
            rankMap.put(card.getRank().getRank(), rankMap.get(card.getRank().getRank()) + 1);
        } else {
            rankMap.put(card.getRank().getRank(), 1);
        }
        if (suitMap.containsKey(card.getSuit())) {
            suitMap.put(card.getSuit(), suitMap.get(card.getSuit()) + 1);
        } else {
            suitMap.put(card.getSuit(), 1);
        }
        rankSuitMap.put(card.getRank().getRank(), card.getSuit());
    }

    private Combinations isRoyalFlush(Map<Integer, Integer> rankMap, Map<Suit, Integer> suitMap, Map<Integer, Suit> rankSuitMap) {
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

    private CombinationPlusHighCard isStraightFlush(Map<Integer, Integer> rankMap, Map<Suit, Integer> suitMap, Map<Integer, Suit> rankSuitMap) {
        int counterStraightCards = 1;
        int[] rankOfComboCards = new int[rankMap.size()];// Какие карты образуют комбинацию, для определения старшей карты комбинации
        int[] cardsRank = new int[rankMap.size()];
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
                    return new CombinationPlusHighCard(Combinations.STRAIGHT_FLUSH, rankOfComboCards[highCardIndex], null);
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
            return new CombinationPlusHighCard(Combinations.STRAIGHT_FLUSH, rankOfComboCards[highCardIndex], null);
        }
        return new CombinationPlusHighCard(Combinations.HIGH_CARD, cardsRank[cardsRank.length - 1], null);
    }

    private CombinationPlusHighCard isFlush(Map<Suit, Integer> suitMap, Map<Integer, Suit> rankSuitMap) {
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
            return new CombinationPlusHighCard(Combinations.FLUSH, highCard, null);
        }
        return new CombinationPlusHighCard(Combinations.HIGH_CARD, highCard, null);
    }

    private CombinationPlusHighCard isStraight(Map<Integer, Integer> rankMap) {
        int counterStraightCards = 1;
        int highCardIndex = 0;
        int[] cardsRank = new int[rankMap.size()];
        int[] rankOfComboCards = new int[rankMap.size()];
        int i = 0;
        int cardsDif;
        for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
            cardsRank[i] = entry.getKey();
            i++;
        }
        for (int j = cardsRank.length - 1; j >= 1; j--) {
            if (cardsRank[cardsRank.length - 1] == 14 && cardsRank[0] == 2 && j == 1) {
                counterStraightCards += 1;
            }
            cardsDif = Math.abs(cardsRank[j] - cardsRank[j - 1]);
            if (cardsDif == 1 || cardsDif == 12) {
                counterStraightCards += 1;
                rankOfComboCards[j] = cardsRank[j];
            } else if (counterStraightCards < 5) {
                counterStraightCards = 1;
                if (j != cardsRank.length - 1) {
                    rankOfComboCards[j + 1] = 0;
                }
            }
        }
        if (counterStraightCards >= 5 && !(cardsRank[cardsRank.length - 1] == 14 && cardsRank[0] == 2)) {
            for (int k = rankOfComboCards.length - 1; k > 0; k--) {
                if (rankOfComboCards[k] != 0) {
                    highCardIndex = k;
                    return new CombinationPlusHighCard(Combinations.STRAIGHT, rankOfComboCards[highCardIndex], null);
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
            return new CombinationPlusHighCard(Combinations.STRAIGHT, rankOfComboCards[highCardIndex], null);
        }
        return new CombinationPlusHighCard(Combinations.HIGH_CARD, cardsRank[cardsRank.length - 1], null);
    }

    private CombinationPlusHighCard multiplicityOfCards(Map<Integer, Integer> rankMap) {
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
                return new CombinationPlusHighCard(Combinations.FOUR_OF_A_KIND, highCard, null);
            }
            highCard = entry.getKey();
        }
        if (threeCardsCounter == 0 && pairsCounter == 1) {
            return new CombinationPlusHighCard(Combinations.PAIR, highPairCard, null);
        }
        if (threeCardsCounter == 0 && pairsCounter >= 2) {
            return new CombinationPlusHighCard(Combinations.TWO_PAIRS, highPairCard, null);
        }
        if (threeCardsCounter >= 1 && pairsCounter == 0) {
            return new CombinationPlusHighCard(Combinations.THREE_OF_A_KIND, highSetCard, null);
        }
        if (threeCardsCounter == 1 && pairsCounter == 1) {
            return new CombinationPlusHighCard(Combinations.FULL_HOUSE, highSetCard * 10 + highPairCard, null);
        }
        return new CombinationPlusHighCard(Combinations.HIGH_CARD, highCard, null);
    }

    private int highCard(Map<Integer, Integer> rankMap) {
        int highestCardStrength = 0;
        for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
            highestCardStrength = entry.getKey();
        }
        return highestCardStrength;
    }

    private Integer[] highCards(Map<Integer, Integer> rankMap) {
        Integer[] hc = new Integer[7];
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
            for (int j = 0; j < entry.getValue(); j++) {
                hc[i] = entry.getKey();
                i+=1;
            }
        }
        i = 2;
        Integer[] res = new Integer[5];
        for (int j = 4; j >= 0; j--) {
            res[j] = hc[i];
            i+=1;
        }
        return res;
    }
}
