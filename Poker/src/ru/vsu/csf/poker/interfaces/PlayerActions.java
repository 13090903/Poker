package ru.vsu.csf.poker.interfaces;

public interface PlayerActions {
    void call();

    void fold();

    void raise(int newBet);

    void check();

}
