package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.Combinations;
import ru.vsu.csf.poker.enums.PlayerState;
import ru.vsu.csf.poker.enums.Suit;

import java.util.*;

public class Game {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random rnd = new Random();
    protected static int currentBet = 0, bank = 0;
    protected static int amountOfFolds = 0;

    public static int getCurrentBet() {
        return Game.currentBet;
    }

    public static void setCurrentBet(int currentBet) {
        Game.currentBet = currentBet;
    }

    public static void generateHand(Player player, int cardsCounter, Deck deck) { // Сгенерировать 2 карты игрока
        Card[] hand = new Card[2];
        int num = rnd.nextInt(cardsCounter);
        hand[0] = deck.deck.get(num);
        deck.deck.remove(num);
        num = rnd.nextInt(cardsCounter - 1);
        hand[1] = deck.deck.get(num);
        deck.deck.remove(num);
        player.setHand(hand);
    }

    public static void generateThreeCards(Card[] table, int cardsCounter, Deck deck) { // Сгенерировать 3 карты на столе
        int num = rnd.nextInt(cardsCounter);
        table[0] = deck.deck.get(num);
        deck.deck.remove(num);
        num = rnd.nextInt(cardsCounter - 1);
        table[1] = deck.deck.get(num);
        deck.deck.remove(num);
        num = rnd.nextInt(cardsCounter - 2);
        table[2] = deck.deck.get(num);
        deck.deck.remove(num);
    }

    public static void generateOneCard(Card[] table, int cardsCounter, int cardNum, Deck deck) { // Сгенерировать 1 карту на столе
        int num = rnd.nextInt(cardsCounter);
        table[cardNum - 1] = deck.deck.get(num);
        deck.deck.remove(num);
    }

    public static boolean betIsTheSame(Player[] players) { // Ставка у всех одинаковая
        int lastNotFold = 0;
        for (int i = 1; i < players.length; i++) {
            if (!players[i].state.equals(PlayerState.FOLD)) {
                if (players[i].bet != players[lastNotFold].bet) {
                    return false;
                }
                lastNotFold = i;
            }
        }
        return true;
    }

    public static boolean everybodyAllIn(Player[] players) {
        for (Player player : players) {
            if (player.cash != player.bet) {
                return false;
            }
        }
        return true;
    }

    public static boolean betIsZero(Player[] players) {// Ставка у каждого игрока - 0 (Начальная ситуация перед выкладыванием карт)
        for (Player player : players) {
            if (player.bet != 0) {
                return false;
            }
        }
        return true;
    }

    public static int bankrupts(Player[] players) {// Количество банкротов
        int counter = 0;
        for (Player player : players) {
            if (player.cash == 0) {
                counter += 1;
            }
        }
        return counter;
    }

    public static boolean endGameConditions(Player[] players) { // Условия, при которых раунд не может продолжаться
        if ((amountOfFolds >= players.length - 1)) {
            if (amountOfFolds == players.length - 1) {
                for (Player player : players) {
                    if (player.state != PlayerState.FOLD) {
                        player.cash += bank;
                    }
                }
            }
            if (amountOfFolds == players.length) {
                bank = 0;
            }
            System.out.println("Round is over");
            System.out.println(Arrays.toString(players));
            return true;
        }
        return false;
    }

    public static void playerDecisions(Player[] players, int i) {// Решения игрока, за которого мы играем
        if (i == 0) {
            System.out.println("Enter your decision: ");
            String dec = scanner.next();
            switch (dec) {
                case "fold":
                    players[i].fold();
                    amountOfFolds += 1;
                    break;
                case "call":
                    if (players[i].bet < currentBet || players[i].bet > currentBet) {
                        players[i].call();
                    } else {
                        System.out.println("You don't need to call, your bet is current bet in game, so check");
                        players[i].check();
                    }
                    players[i].call();
                    break;
                case "check":
                    if (players[i].bet == currentBet) {
                        players[i].check();
                    } else {
                        System.out.println("You can't check, current bet is higher than yours. Call, raise or fold: ");
                        String dec1 = scanner.next();
                        switch (dec1) {
                            case "fold":
                                players[i].fold();
                                amountOfFolds += 1;
                                break;
                            case "call":
                                if (players[i].cash <= currentBet) {
                                    players[i].call();
                                } else {
                                    players[i].bet = players[i].cash;
                                }
                                break;
                            case "raise":
                                System.out.println("Enter new bet: ");
                                int newBet = scanner.nextInt();
                                while (players[i].cash <= newBet || newBet <= currentBet) {
                                    System.out.println("You haven't got enough cash or bet is less than last. Enter new bet: : ");
                                    newBet = scanner.nextInt();
                                }
                                players[i].raise(newBet);
                                break;
                            default:
                                System.out.println("Impossible action");
                        }

                    }
                    break;
                case "raise":
                    System.out.println("Enter new bet: ");
                    int newBet = scanner.nextInt();
                    while (players[i].cash < newBet || newBet <= currentBet) {
                        System.out.println("You haven't got enough cash or bet is less than last. Enter new bet: : ");
                        newBet = scanner.nextInt();
                    }
                    players[i].raise(newBet);
                    break;
                default:
                    System.out.println("Impossible action");
            }
        }
    }

    public static void botsDecisions(Player[] players, int i) {// Все решения, принимаемые ботами
        if (players[i].state.equals(PlayerState.FOLD)) {
            return;
        }
        int decision = 1 + rnd.nextInt(9);
        if (decision == 1 && i != 0) {
            int newBet = currentBet + 100 + (rnd.nextInt(currentBet + 100) / 100) * 100;
            while (players[i].cash <= newBet) {
                newBet = currentBet + 100 + (rnd.nextInt(currentBet) / 100) * 100;
            }
            players[i].raise(newBet);
            System.out.printf("Player %d raises to %d\n", i, players[i].bet);
        }
        if (decision == 2 && i != 0) {
            players[i].fold();
            System.out.printf("Player %d folds\n", i);
            amountOfFolds += 1;
            return;
        }
        if (decision > 2 && i != 0) {
            if (currentBet > players[i].bet) {
                if (currentBet <= players[i].cash) {
                    players[i].call();
                    System.out.printf("Player %d calls\n", i);
                } else  {
                    players[i].bet = players[i].cash;
                    System.out.printf("Player %d going all-in\n", i);
                }
            } else {
                players[i].check();
                System.out.printf("Player %d checks\n", i);
            }
        }
        System.out.println("Current bet is " + currentBet);
    }

    public static void oneBet(Player[] players, int i) {// Первая ставка перед каждым открытием карт (Можно заменить на блайнды)
        System.out.println(players[i]);
        System.out.print("Use blind - blind, raise - raise or fold - fold\n");
        String blind = scanner.next();
        if (blind.equals("blind")) {
            currentBet = 100;
            players[0].bet = 100;
            return;
        }
        if (blind.equals("fold")) {
            players[i].fold();
            amountOfFolds += 1;
            return;
        }
        if (blind.equals("raise")) {
            System.out.print("Enter your bet: ");
            int someBet = scanner.nextInt();
            while (someBet > players[i].cash || someBet <= 0) {
                System.out.print("This bet is impossible. Enter another bet: ");
                someBet = scanner.nextInt();
            }
            currentBet = someBet;
            players[0].bet = someBet;
        } else {
            System.out.print("Use blind - blind, raise - raise or fold - fold\n");
            String dec = scanner.next();
            while (!dec.equals("blind") && !dec.equals("raise") && !dec.equals("fold")) {
                System.out.print("Use blind - blind, raise - raise or fold - fold\n");
                dec = scanner.next();
            }
            if (dec.equals("blind")) {
                currentBet = 100;
                players[0].bet = 100;
                return;
            }
            if (dec.equals("fold")) {
                players[i].fold();
                amountOfFolds += 1;
            } else {
                System.out.print("Enter your bet: ");
                int someBet = scanner.nextInt();
                while (someBet > players[i].cash || someBet <= 0) {
                    System.out.print("This bet is impossible. Enter another bet: ");
                    someBet = scanner.nextInt();
                }
                currentBet = someBet;
                players[0].bet = someBet;
            }
        }
    }

    public static boolean everybodyChecks(Player[] players) {
        for (Player player : players) {
            if (player.state == PlayerState.DEFAULT) {
                return false;
            }
        }
        return true;
    }

    public static int bettingCircle(Player[] players, int circleNumber) { // Один ставочный круг
        boolean flag = false;
        while (!betIsTheSame(players) || !everybodyChecks(players)) {
            if (everybodyAllIn(players)) {break;}
            for (int i = 0; i < players.length; i++) {
                if (i == 0 && currentBet == 0 && circleNumber == 1) {
                    oneBet(players, i);
                }

                if (endGameConditions(players)) {
                    return 1;
                }

                if (i > 0) {
                    botsDecisions(players, i);
                    flag = true;
                }

                if (endGameConditions(players)) {
                    return 1;
                }

                if (i == 0 && flag) {
                    playerDecisions(players, i);
                }

                if (endGameConditions(players)) {
                    return 1;
                }

            }
        }
        for (Player player : players) {
            if (player.state.equals(PlayerState.CHECK)) {
                player.state = PlayerState.DEFAULT;
            }
        }
        return 0;
    }

    public static void checkCombination(Player player, Card[] table) {
        Map<Integer, Integer> rankMap = new HashMap<>(); // Ключ - ранг, значение - количество
        Map<Suit, Integer> suitMap = new HashMap<>(); // Ключ - масть, значение - количество
        Map<Integer, Suit> rankSuitMap = new HashMap<>(); // Ключ - ранг, значение - масть
        for (Card card : player.hand) {
            if (rankMap.containsKey(card.rank.getRank())) {
                rankMap.put(card.rank.getRank(), rankMap.get(card.rank.getRank()) + 1);
            } else {
                rankMap.put(card.rank.getRank(), 1);
            }
            if (suitMap.containsKey(card.suit)) {
                suitMap.put(card.suit, suitMap.get(card.suit) + 1);
            } else {
                suitMap.put(card.suit, 1);
            }
            rankSuitMap.put(card.rank.getRank(), card.suit);
        }
        for (Card card : table) {
            if (rankMap.containsKey(card.rank.getRank())) {
                rankMap.put(card.rank.getRank(), rankMap.get(card.rank.getRank()) + 1);
            } else {
                rankMap.put(card.rank.getRank(), 1);
            }
            if (suitMap.containsKey(card.suit)) {
                suitMap.put(card.suit, suitMap.get(card.suit) + 1);
            } else {
                suitMap.put(card.suit, 1);
            }
            rankSuitMap.put(card.rank.getRank(), card.suit);
        }// Заполнили словари

        if (Objects.equals(isRoyalFlush(rankMap, suitMap, rankSuitMap), Combinations.ROYAL_FLUSH)) {
            player.combination = new CombinationPlusHighCard(Combinations.ROYAL_FLUSH, 14);
            return;
        }

        CombinationPlusHighCard straightFlush = isStraightFlush(rankMap, suitMap, rankSuitMap);
        if (straightFlush.combination.equals(Combinations.STRAIGHT_FLUSH)) {
            player.combination = new CombinationPlusHighCard(Combinations.STRAIGHT_FLUSH, straightFlush.highCard);
            return;
        }

        CombinationPlusHighCard multiplicityCombo = multiplicityOfCards(rankMap);
        if (multiplicityCombo.combination.equals(Combinations.FOUR_OF_A_KIND)) {
            player.combination = new CombinationPlusHighCard(Combinations.FOUR_OF_A_KIND, multiplicityCombo.highCard);
            return;
        }

        if (multiplicityCombo.combination.equals(Combinations.FULL_HOUSE)) {
            player.combination = new CombinationPlusHighCard(Combinations.FULL_HOUSE, multiplicityCombo.highCard);
            return;
        }

        CombinationPlusHighCard flush = isFlash(suitMap, rankSuitMap);
        if (flush.combination.equals(Combinations.FLUSH)) {
            player.combination = new CombinationPlusHighCard(Combinations.FLUSH, flush.highCard);
            return;
        }

        CombinationPlusHighCard straight = isStraight(rankMap);
        if (straight.combination.equals(Combinations.STRAIGHT)) {
            player.combination = new CombinationPlusHighCard(Combinations.STRAIGHT, straight.highCard);
            return;
        }

        if (multiplicityCombo.combination.equals(Combinations.THREE_OF_A_KIND)) {
            player.combination = new CombinationPlusHighCard(Combinations.THREE_OF_A_KIND, multiplicityCombo.highCard);
            return;
        }

        if (multiplicityCombo.combination.equals(Combinations.TWO_PAIRS)) {
            player.combination = new CombinationPlusHighCard(Combinations.TWO_PAIRS, multiplicityCombo.highCard);
            return;
        }

        if (multiplicityCombo.combination.equals(Combinations.PAIR)) {
            player.combination = new CombinationPlusHighCard(Combinations.PAIR, multiplicityCombo.highCard);
            return;
        }

        player.combination = new CombinationPlusHighCard(Combinations.HIGH_CARD, highCard(rankMap));
    }

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

    public static CombinationPlusHighCard isStraight(Map<Integer, Integer> rankMap) {
        int counterStraightCards = 1;
        int highCardIndex = 0;
        int[] cardsRank = new int[7];
        int[] rankOfComboCards = new int[7];
        int i = 0;
        int cardsDif = 0;
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
                    return new CombinationPlusHighCard(Combinations.STRAIGHT, rankOfComboCards[highCardIndex]);
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
            return new CombinationPlusHighCard(Combinations.STRAIGHT, rankOfComboCards[highCardIndex]);
        }
        return new CombinationPlusHighCard(Combinations.HIGH_CARD, cardsRank[cardsRank.length - 1]);
    }

    public static int highCard(Map<Integer, Integer> rankMap) {
        int highestCardStrength = 0;
        for (Map.Entry<Integer, Integer> entry : rankMap.entrySet()) {
            highestCardStrength = entry.getKey();
        }
        return highestCardStrength;
    }


    public static void game(Player[] players, Deck deck) {
        Card[] table = new Card[5];
        int cardsCounter = 52;
        for (Player player : players) {
            player.state = PlayerState.DEFAULT;
            generateHand(player, cardsCounter, deck);
            cardsCounter -= 2;
        }

        System.out.println("New game!");

        if (bettingCircle(players, 1) == 1) {// Первое определение ставки, после чего достаются 3 карты
            return;
        }

        for (Player player : players) {
            bank += player.bet;
            player.cash -= player.bet;
            player.bet = 0;
        }
        currentBet = 0;

        boolean allIn = bankrupts(players) > 0;

        generateThreeCards(table, cardsCounter, deck);
        cardsCounter -= 3;
        System.out.println(Arrays.toString(table));

        if (!allIn) {
            if (bettingCircle(players, 2) == 1) {// Второе определение ставки, после чего достется 4 карта
                return;
            }

            for (Player player : players) {
                bank += player.bet;
                player.cash -= player.bet;
                player.bet = 0;
            }
            currentBet = 0;
        }

        generateOneCard(table, cardsCounter, 4, deck);
        cardsCounter -= 1;
        System.out.println(Arrays.toString(table));

        if (bankrupts(players) > 0) {
            allIn = true;
        }

        if (!allIn) {
            if (bettingCircle(players, 3) == 1) {// Третье определение ставки, после чего достется 5 карта
                return;
            }
            for (Player player : players) {
                bank += player.bet;
                player.cash -= player.bet;
                player.bet = 0;
            }
            currentBet = 0;
        }

        generateOneCard(table, cardsCounter, 5, deck);
        System.out.println(Arrays.toString(table));

        if (bankrupts(players) > 0) {
            allIn = true;
        }

        if (!allIn) {
            if (bettingCircle(players, 4) == 1) {// Четвертое определение ставки, когда все карты открыты
                return;
            }
            for (Player player : players) {
                bank += player.bet;
                player.cash -= player.bet;
                player.bet = 0;
            }
            currentBet = 0;
        }


        System.out.println(Arrays.toString(table));

        System.out.println("Opening up!");


        for (Player player : players) {
            if (!player.state.equals(PlayerState.FOLD)) {
                checkCombination(player, table);
            } else {
                player.combination = new CombinationPlusHighCard(Combinations.HIGH_CARD, 0);
            }
        }

        String winner = players[0].name;
        int winNumber = 0;
        String winnerCombination = players[0].combination.combination.toString();
        for (int i = 1; i < players.length; i++) {
            if (players[i].combination.combination.getStrenth() >= players[i - 1].combination.combination.getStrenth()) {
                if (players[i].combination.combination.getStrenth() > players[i - 1].combination.combination.getStrenth()) {
                    winner = players[i].name;
                    winnerCombination = players[i].combination.combination.toString();
                    winNumber = i;
                } else {
                    if (players[i].combination.highCard > players[i - 1].combination.highCard) {
                        winner = players[i].name;
                        winnerCombination = players[i].combination.combination.toString();
                        winNumber = i;
                    } else if (players[i].combination.highCard < players[i - 1].combination.highCard) {
                        winNumber = i - 1;
                    } else {
                        winner = "Draw";
                        winNumber = -2;
                        winnerCombination = players[i].combination.combination.toString();
                        for (Player player : players) {
                            if (player.state != PlayerState.FOLD && player.combination.combination.toString().equals(winnerCombination)) {
                                player.cash += bank / players.length;
                            }
                        }
                    }
                }
            } else {
                winNumber = i - 1;
            }
        }
        if (winNumber != -1 && winNumber != -2) {
            players[winNumber].cash += bank;
        }

        System.out.println("Winner: " + winner + " with combination " + winnerCombination);
        System.out.println(Arrays.toString(players));


    }

}
