package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.MoveType;
import ru.vsu.csf.poker.graphics.DrawPanel;
import ru.vsu.csf.poker.interfaces.GameUI;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import static ru.vsu.csf.poker.Main.rb;


public class ConsoleGameUI implements GameUI {

    private Scanner in;
    private PrintWriter out;


    private String myBetText = rb.getString("myBet");
    private String currBetText = rb.getString("currBet");
    private String bankText = rb.getString("bank");

    public ConsoleGameUI(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintWriter(out, true);
    }

    @Override
    public Move prompt() {
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
    public void showHand(Card[] cards) {
        out.println(Arrays.toString(cards));
    }

    @Override
    public void showTable(Table table) {
        out.println(table.cards);
    }

    @Override
    public void showGameState(int playerBet, int currBet, int bank) {
        out.println(myBetText + ": " + playerBet);
        out.println(currBetText + ": " + currBet);
        out.println(bankText + ": " + bank);
    }
}
