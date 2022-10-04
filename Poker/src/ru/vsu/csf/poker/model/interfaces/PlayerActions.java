package ru.vsu.csf.poker.model.interfaces;

public interface PlayerActions {
    void call();

    void fold();

    void raise(int newBet);

    void check();

}
