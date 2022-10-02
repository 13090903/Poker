package ru.vsu.csf.poker.combination;

import java.util.Map;

public class HighCard {
    public static int highCard(Map<Integer, Integer> rankMap) {
        int highestCardStrength = 0;
        for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
            highestCardStrength = entry.getKey();
        }
        return highestCardStrength;
    }
}
