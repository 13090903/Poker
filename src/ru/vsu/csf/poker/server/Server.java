package ru.vsu.csf.poker.server;

import ru.vsu.csf.poker.event.PlayerEvent;
import ru.vsu.csf.poker.event.TableEvent;
import ru.vsu.csf.poker.event.WinnerEvent;
import ru.vsu.csf.poker.model.Bot;
import ru.vsu.csf.poker.model.Game;
import ru.vsu.csf.poker.model.GameCallBackObj;
import ru.vsu.csf.poker.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final Game game;
    private List<ClientHandler> clientHandlers = new ArrayList<>();

    public Server() {
        game = new Game();

        game.setCallback(obj -> {
            if (obj.type == GameCallBackObj.Type.MOVE && !obj.moveDone) {
                for (ClientHandler c : clientHandlers) {
                    if (!c.ready()) {
                        continue;
                    }
                    c.send("H:" + obj.id);
                }
            } else if (obj.moveDone) {
                for (ClientHandler c : clientHandlers) {
                    if (!c.ready()) {
                        continue;
                    }
                    Player player = game.getPlayerByID(obj.id);
                    PlayerEvent playerEvent = new PlayerEvent(player);
                    TableEvent tableEvent = new TableEvent(game.getTable());
                    c.send("PTU:" + playerEvent + "|" + tableEvent);
                }
            } else if (obj.type == GameCallBackObj.Type.WIN) {
                for (ClientHandler c : clientHandlers) {
                    WinnerEvent winnerEvent = new WinnerEvent(game.getRoundWinners());
                    c.send("WU:" + winnerEvent);
                    if (game.getPlayerByID(obj.id) != null) {
                        Player player = game.getPlayerByID(obj.id);
                        PlayerEvent playerEvent = new PlayerEvent(player);
                        TableEvent tableEvent = new TableEvent(game.getTable());
                        c.send("PTU:" + playerEvent + "|" + tableEvent);
                    }
                }

            } else {
                for (ClientHandler c : clientHandlers) {
                    c.send("New:" + obj.id);
                }
            }
        });

        for (int i = 0; i < 3; i++) {
            Bot bot = new Bot("Bot" + i, 4000, null);
            game.addPlayer(bot);
        }

        new Thread(game::start).start();

        try {
            ServerSocket server = new ServerSocket(9999);
            while (true) {
                if (game.getPlayers().size() >= 6) {
                    return;
                }
                System.out.println("Server waiting for clients...");
                Socket socket = server.accept();
                System.out.println("Client connected.");
                clientHandlers.add(new ClientHandler(socket, game));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
