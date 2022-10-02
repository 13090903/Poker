package ru.vsu.csf.poker.combination;

import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.enums.Suit;
import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.CombinationPlusHighCard;
import ru.vsu.csf.poker.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ru.vsu.csf.poker.combination.Flush.isFlash;
import static ru.vsu.csf.poker.combination.HighCard.highCard;
import static ru.vsu.csf.poker.combination.MultipleCards.multiplicityOfCards;
import static ru.vsu.csf.poker.combination.RoyalFlush.isRoyalFlush;
import static ru.vsu.csf.poker.combination.Straight.isStraight;
import static ru.vsu.csf.poker.combination.StraightFlush.isStraightFlush;


public class Combination {
    public static void checkCombination(Player player, Card[] table) {
        Map<Integer, Integer> rankMap = new HashMap<>(); // Ключ - ранг, значение - количество
        Map<Suit, Integer> suitMap = new HashMap<>(); // Ключ - масть, значение - количество
        Map<Integer, Suit> rankSuitMap = new HashMap<>(); // Ключ - ранг, значение - масть
        for (Card card : player.getHand()) {
            FillMaps(rankMap, suitMap, rankSuitMap, card);
        }
        for (Card card : table) {
            FillMaps(rankMap, suitMap, rankSuitMap, card);
        }// Заполнили словари

        if (Objects.equals(isRoyalFlush(rankMap, suitMap, rankSuitMap), Combinations.ROYAL_FLUSH)) {
            player.setCombination(new CombinationPlusHighCard(Combinations.ROYAL_FLUSH, 14));
            return;
        }

        CombinationPlusHighCard straightFlush = isStraightFlush(rankMap, suitMap, rankSuitMap);
        if (straightFlush.combination.equals(Combinations.STRAIGHT_FLUSH)) {
            player.setCombination(new CombinationPlusHighCard(Combinations.STRAIGHT_FLUSH, straightFlush.highCard));
            return;
        }

        CombinationPlusHighCard multiplicityCombo = multiplicityOfCards(rankMap);
        if (multiplicityCombo.combination.equals(Combinations.FOUR_OF_A_KIND)) {
            player.setCombination(new CombinationPlusHighCard(Combinations.FOUR_OF_A_KIND, multiplicityCombo.highCard));
            return;
        }

        if (multiplicityCombo.combination.equals(Combinations.FULL_HOUSE)) {
            player.setCombination(new CombinationPlusHighCard(Combinations.FULL_HOUSE, multiplicityCombo.highCard));
            return;
        }

        CombinationPlusHighCard flush = isFlash(suitMap, rankSuitMap);
        if (flush.combination.equals(Combinations.FLUSH)) {
            player.setCombination(new CombinationPlusHighCard(Combinations.FLUSH, flush.highCard));
            return;
        }

        CombinationPlusHighCard straight = isStraight(rankMap);
        if (straight.combination.equals(Combinations.STRAIGHT)) {
            player.setCombination(new CombinationPlusHighCard(Combinations.STRAIGHT, straight.highCard));
            return;
        }

        if (multiplicityCombo.combination.equals(Combinations.THREE_OF_A_KIND)) {
            player.setCombination(new CombinationPlusHighCard(Combinations.THREE_OF_A_KIND, multiplicityCombo.highCard));
            return;
        }

        if (multiplicityCombo.combination.equals(Combinations.TWO_PAIRS)) {
            player.setCombination(new CombinationPlusHighCard(Combinations.TWO_PAIRS, multiplicityCombo.highCard));
            return;
        }

        if (multiplicityCombo.combination.equals(Combinations.PAIR)) {
            player.setCombination(new CombinationPlusHighCard(Combinations.PAIR, multiplicityCombo.highCard));
            return;
        }

        player.setCombination(new CombinationPlusHighCard(Combinations.HIGH_CARD, highCard(rankMap)));
    }

    public static void FillMaps(Map<Integer, Integer> rankMap, Map<Suit, Integer> suitMap, Map<Integer, Suit> rankSuitMap, Card card) {
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
}
