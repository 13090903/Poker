package ru.vsu.csf.poker.model;

public interface PlayerActions {
    void call();

    void fold();

    void raise(int newBet);

    void check();

}
