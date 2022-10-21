package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.MoveType;

public class Move {
    private MoveType moveType;
    private int raiseValue;

    public Move(MoveType mt, int raiseValue) {
        this.moveType = mt;
        this.raiseValue = raiseValue;
    }

    public Move(MoveType mt) {
        this.moveType = mt;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public int getRaiseValue() {
        return raiseValue;
    }
}
