package ru.vsu.csf.poker.event;

import ru.vsu.csf.poker.model.Card;
import ru.vsu.csf.poker.model.Player;
import ru.vsu.csf.poker.model.Table;

import java.util.List;

public class TableEvent {
    private int bank;
    private int currentBet;
    private List<Card> tableCards;

    public TableEvent(int bank, int currentBet, List<Card> tableCards) {
        this.bank = bank;
        this.currentBet = currentBet;
        this.tableCards = tableCards;
    }

    public TableEvent(Table table) {
        bank = table.getBank();
        currentBet = table.getCurrentBet();
        tableCards = table.getCards();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(bank).append(" ").append(currentBet).append(" ");
        sb.append("[");
        for (Card card : tableCards) {
            sb.append(card.toString()).append(";");
        }
        if (tableCards.size() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append("]");
        return sb.toString().trim();
    }

    public int getBank() {
        return bank;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public List<Card> getTableCards() {
        return tableCards;
    }
}
