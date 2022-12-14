package ru.vsu.csf.poker.graphics;

import ru.vsu.csf.poker.enums.MoveType;
import ru.vsu.csf.poker.event.Parser;
import ru.vsu.csf.poker.model.Move;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class ServerConnection implements Runnable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private UUID myId;
    private final DrawPanel drawPanel;

    public ServerConnection(String host, int port, DrawPanel panel) {
        this.drawPanel = panel;
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            send("MyID:");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String s) {
        out.println(s);
    }


    @Override
    public void run() {
        try {
            while (true) {
                String s = in.readLine();
                String[] arr = s.split(":");
                String command = arr[0];
                switch (command) {
                    case "H" -> {
                        if (arr[1] != null) {
                            drawPanel.graphicsPlayerMap.get(UUID.fromString(arr[1])).highlight();
                        }
                    }
                    case "PTU" -> {
                        String[] s1 = arr[1].split("\\|");
                        drawPanel.updatePlayer(Parser.parsePlayerEvent(s1[0]));
                        drawPanel.updateTable(Parser.parseTableEvent(s1[1]));
                    }
                    case "TU" -> {
                        drawPanel.updateTable(Parser.parseTableEvent(arr[1]));
//                        drawPanel.resetWinners();
                    }
                    case "WU" -> {
                        drawPanel.showCards();
                        drawPanel.updateWinners(Parser.parseWinnerEvent(arr[1]));
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    case "PI" -> {
                        for (String idStr : arr[1].split(",")) {
                            UUID id = UUID.fromString(idStr);
                            drawPanel.addPlayer(id, myId.equals(id));
                        }
                        send("R:");
                    }
                    case "MyID" -> {
                        myId = UUID.fromString(arr[1]);
                        send("PI:");
                    }
                    case "Move" -> {
                        send("Move:" + drawPanel.askPlayer());
                    }
                    case "New" -> {
                        UUID id = UUID.fromString(arr[1]);
                        drawPanel.addPlayer(id, id.equals(myId));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
