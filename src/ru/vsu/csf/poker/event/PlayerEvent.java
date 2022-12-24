package ru.vsu.csf.poker.event;

import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Player;

import java.util.UUID;

public class PlayerEvent {
    private UUID id;
    private String name;
    private final int cash;
    private final int bet;
    private final Card[] hand;

    public PlayerEvent(UUID id, String name, int cash, int bet, Card[] hand) {
        this.id = id;
        this.name = name;
        this.cash = cash;
        this.bet = bet;
        this.hand = hand;
    }

    public PlayerEvent(Player player) {
        id = player.getId();
        name = player.getName();
        cash = player.getCash();
        bet = player.getBet();
        hand = player.getHand();
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ").append(name).append(" ").append(cash).append(" ").append(bet).append(" ");
        sb.append("[");
        for (Card card : hand) {
            sb.append(card.toString()).append(";");
        }
        if (hand.length > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append("]");
        return sb.toString().trim();
    }

    public UUID getId() {
        return id;
    }

    public int getBet() {
        return bet;
    }


    public Card[] getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public int getCash() {
        return cash;
    }
}
