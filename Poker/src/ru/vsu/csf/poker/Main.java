package ru.vsu.csf.poker;

import ru.vsu.csf.poker.model.Game;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Чтобы запустить конслольное приложение введите - 1, графическое - 2");
        String type = scanner.next();
        if (type.equals("1")) {
            Game game = new Game();
            game.gameSimulation("I", 5000, 1, true);
        }
    }
}
