package ru.vsu.csf.poker.enums;

public enum GameStages {
    FIRST(1, 3),
    SECOND(2, 1),
    THIRD(3, 1),
    FOURTH(4, 0);

    private final int num;
    private final int cardAmount;

    GameStages(int num, int cardAmount) {
        this.num = num;
        this.cardAmount = cardAmount;
    }

    public int getCardAmount() {
        return cardAmount;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "GameStages{" +
                "num=" + num +
                '}';
    }
}
