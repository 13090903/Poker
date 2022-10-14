package ru.vsu.csf.poker.enums;

public enum GameStages {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4);

    private final int num;

    GameStages(int num) {
        this.num = num;
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
