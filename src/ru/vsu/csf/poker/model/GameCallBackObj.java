package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.Combinations;

import java.util.List;
import java.util.UUID;

public class GameCallBackObj {

    public UUID id;
    public boolean moveDone;
    public List<UUID> winnersID;
    public Combinations combination;

    public enum Type {
        MOVE, WIN, NEW
    }

    public final Type type;

    public GameCallBackObj(UUID id, boolean moveDone) {
        this.id = id;
        this.moveDone = moveDone;
        type = Type.MOVE;
    }

    public GameCallBackObj(List<UUID> winnersID, Combinations combination) {
        this.winnersID = winnersID;
        this.combination = combination;
        type = Type.WIN;
    }

    public GameCallBackObj(UUID id) {
        this.id = id;
        type = Type.NEW;
    }

//    public GameCallBackObj(UUID id, boolean moveDone, List<UUID> winnersID, Combinations combination) {
//        this.id = id;
//        this.moveDone = moveDone;
//        this.winnersID = winnersID;
//        this.combination = combination;
//    }


}
