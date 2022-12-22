package ru.vsu.csf.poker.server;

import ru.vsu.csf.poker.model.Game;
import ru.vsu.csf.poker.model.Move;
import ru.vsu.csf.poker.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class ClientHandler {

    private boolean ready = false;
    private UUID id;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, Game game) {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            id = game.addPlayer(new Player("Player" + game.getPlayers().size(), 5000, null));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                while (true) {
                    String s = in.readLine();
                    switch (s) {
                        case "PI" -> {
                            StringBuilder playersIds = new StringBuilder();
                            for (Player player : game.getPlayers()) {
                                playersIds.append(player.getId()).append(",");
                            }
                            for (Player player : game.getWaitingPlayers()) {
                                playersIds.append(player.getId()).append(",");
                            }
                            out.println("PI:" + playersIds.substring(0, playersIds.length()));
                        }
                        case "PS" -> out.println("PS:" + game.getPlayers().size());
                        case "R" -> ready = true;
                        case "MyID" -> out.println("MyID:" + id);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public Move requestMove() {

    }

    public boolean ready() {
        return ready;
    }

    public void send(String s) {
        out.println(s);
    }
}
