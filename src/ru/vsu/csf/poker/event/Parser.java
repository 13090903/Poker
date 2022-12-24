package ru.vsu.csf.poker.event;

import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.enums.MoveType;
import ru.vsu.csf.poker.enums.Rank;
import ru.vsu.csf.poker.enums.Suit;
import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Parser {
    public static PlayerEvent parsePlayerEvent(String s) {
        int bet, cash;
        UUID id; String name;
        Card[] hand = new Card[2];
        String[] arr = s.split(" ");
        id = UUID.fromString(arr[0]);
        name = arr[1];
        cash = Integer.parseInt(arr[2]);
        bet = Integer.parseInt(arr[3]);
        String handString = arr[4].substring(1, arr[4].length() - 1);
        int i = 0;
        for (String cardString : handString.split(";")) {
            hand[i++] = parseCard(cardString);
        }
        return new PlayerEvent(id, name, cash, bet, hand);
    }

    public static TableEvent parseTableEvent(String s) {
        List<Card> tableCards = new ArrayList<>();
        String[] arr = s.split("\\s");
        int bank = Integer.parseInt(arr[0]);
        int currentBet = Integer.parseInt(arr[1]);
        String cardsString = arr[2].substring(1, arr[2].length() - 1).trim();
        if (!cardsString.isEmpty()) {
            for (String cardString : cardsString.split(";")) {
                tableCards.add(parseCard(cardString));
            }
        }

        return new TableEvent(bank, currentBet, tableCards);
    }

    public static Card parseCard(String s) {
        String[] str = s.split("_");
        return new Card(Rank.valueOf(str[0], -1), Suit.valueOf(str[1], -1));
    }

    public static WinnerEvent parseWinnerEvent(String s) {
        String[] arr = s.split("\\|");
        Combinations comb = Combinations.valueOf(arr[1], -1);
        String[] arr1 = arr[0].split(" ");
        List<String> winnersNames = new ArrayList<>(Arrays.asList(arr1));
        return new WinnerEvent(winnersNames, comb);
    }

    public static Move parseMove(String s) {
        Move move;
        String[] arr = s.split(",");
        if (arr.length == 1) {
            move = new Move(MoveType.valueOf(arr[0].toUpperCase()));
        } else {
            move = new Move(MoveType.valueOf(arr[0].toUpperCase()), Integer.parseInt(arr[1]));
        }
        return move;
    }

}
