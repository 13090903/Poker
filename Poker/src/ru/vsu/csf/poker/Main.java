package ru.vsu.csf.poker;

import ru.vsu.csf.poker.enums.Rank;
import ru.vsu.csf.poker.enums.Suit;
import ru.vsu.csf.poker.graphics.GraphicsGameUI;
import ru.vsu.csf.poker.graphics.MainWindow;
import ru.vsu.csf.poker.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Чтобы запустить конслольное приложение введите - 1, графическое - 2");
        String type = scanner.next();
        if (type.equals("1")) {
            Game game = new Game();
            System.out.println("Введите имя, начальный банк и количество ботов через пробел");
            String[] s = new String[3];
            for (int i = 0; i < 3; i++) {
                s[i] = scanner.next();
            }
            List<Player> players = new ArrayList<>();
            players.add(new Player(s[0], Integer.parseInt(s[1]), null, new ConsoleGameUI(System.in, System.out)));
            for (int i = 0; i < Integer.parseInt(s[2]); i++) {
                players.add(new Bot("Bot" + i, Integer.parseInt(s[1]), null));
            }
            game.start(players);
        } else if (type.equals("2")){
            MainWindow w = new MainWindow();
        }
    }
}
