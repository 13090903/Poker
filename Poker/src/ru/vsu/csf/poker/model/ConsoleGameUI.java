package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.MoveType;
import ru.vsu.csf.poker.model.interfaces.GameUI;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;


public class ConsoleGameUI implements GameUI {

    private Scanner in;
    private PrintWriter out;

    public ConsoleGameUI(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintWriter(out, true);
    }

    @Override
    public Move prompt() {
//        showMessage("Your Turn: ");
        String[] input = in.nextLine().split(" ");
        String typeStr = input[0];
        switch (typeStr) {
            case "call" -> {
                return new Move(MoveType.CALL);
            }
            case "check" -> {
                return new Move(MoveType.CHECK);
            }
            case "fold" -> {
                return new Move(MoveType.FOLD);
            }
            case "raise" -> {
                return new Move(MoveType.RAISE, Integer.parseInt(input[1]));
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public void showMessage(String message) {
        out.println(message);
    }

    @Override
    public void showGameState(int playerBet, int currBet, int bank) {
        out.println("My bet: " + playerBet);
        out.println("Current bet: " + currBet);
        out.println("Bank: " + bank);
    }
}
