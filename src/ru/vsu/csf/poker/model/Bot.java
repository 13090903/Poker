package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.MoveType;

import java.util.Objects;
import java.util.Random;

public class Bot extends Player {
    private final Random rnd = new Random();

    public Bot(String name, int cash, Table table) {
        super(name, cash, table);
    }

    @Override
    public void makeMove() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int randomInt = rnd.nextInt(50);
        Move move = null;
        switch (randomInt) {
            case 0 -> move = new Move(MoveType.RAISE, getTable().getCurrentBet() + (rnd.nextInt(cash - getTable().getCurrentBet()) / 10) * 10);
            case 1 -> move = new Move(MoveType.FOLD);
            default -> {
                if (bet < getTable().getCurrentBet()) {
                    move = new Move(MoveType.CALL);
                }
                if (bet == getTable().getCurrentBet()) {
                    move = new Move(MoveType.CHECK);
                }
                if (cash < getTable().getCurrentBet()) {
                    move = new Move(MoveType.FOLD);
                }
            }
        }
        switch (Objects.requireNonNull(move).getMoveType()) {
            case CALL -> call();
            case CHECK -> check();
            case FOLD -> {
                fold();
                getTable().amountOfFolds += 1;
            }
            case RAISE -> raise(move.getRaiseValue());
        }
    }

}
