package ru.vsu.csf.poker.model;

import java.util.List;
import java.util.UUID;

public class GameCallBackObj {
    public UUID id;
    public boolean moveDone;
    private List<Player> playerList;

    public GameCallBackObj(UUID id, boolean moveDone) {
        this.id = id;
        this.moveDone = moveDone;
    }

    public GameCallBackObj(List<Player> playerList) {
        this.playerList = playerList;
    }


}
