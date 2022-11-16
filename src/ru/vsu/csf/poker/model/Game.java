package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.GameStages;
import ru.vsu.csf.poker.enums.PlayerState;

import java.util.*;
import java.util.function.Consumer;

import static ru.vsu.csf.poker.Main.rb;


public class Game {
    private String newGame = rb.getString("newGame");
    private String myCards = rb.getString("myCards");
    private String opening = rb.getString("opening");
    private String end = rb.getString("end");
    private String roundOver = rb.getString("roundOver");

    private Consumer<UUID> callback;

    private List<Player> players = new ArrayList<>();
    private List<Player> roundWinners = new ArrayList<>();

    protected Table table = new Table();;
    private WinnerDeterminator wd;

    public void setCallback(Consumer<UUID> callback) {
        this.callback = callback;
    }

    public Table getTable() {
        return table;
    }

    public int bankrupts(List<Player> players) {// Количество банкротов
        int counter = 0;
        for (Player player : players) {
            if (player.cash == 0) {
                counter += 1;
            }
        }
        return counter;
    }

    public boolean everybodyChecks(List<Player> players) {
        for (Player player : players) {
            if (player.getState() == PlayerState.DEFAULT) {
                return false;
            }
        }
        return true;
    }

    public boolean endGameConditions(List<Player> players) { // Условия, при которых раунд не может продолжаться
        Table table = players.get(0).getTable();
        if ((table.amountOfFolds >= players.size() - 1)) {
            if (table.amountOfFolds == players.size() - 1) {
                for (Player player : players) {
                    if (player.getState() != PlayerState.FOLD) {
                        player.setCash(player.getCash() + table.getBank());
                    }
                }
            }
            if (table.amountOfFolds == players.size()) {
                table.setBank(0);
            }
            System.out.println(roundOver);
            System.out.println(players);
            return true;
        }
        return false;
    }

    public boolean everybodyAllIn(List<Player> players) {
        for (Player player : players) {
            if (player.getCash() != player.getBet()) {
                return false;
            }
        }
        return true;
    }

    public boolean betIsTheSame(List<Player> players) { // Ставка у всех одинаковая
        int lastNotFold = 0;
        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).getState().equals(PlayerState.FOLD) && players.get(i).cash > 0) {
                if (players.get(i).getBet() != players.get(lastNotFold).getBet()) {
                    return false;
                }
                lastNotFold = i;
            }
        }
        return true;
    }

    public boolean bettingCircle(List<Player> players) { // Один ставочный круг
        while (!betIsTheSame(players) || !everybodyChecks(players)) {
            if (everybodyAllIn(players)) {
                break;
            }
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getState().equals(PlayerState.FOLD) || players.get(i).cash <= 0) {
                    continue;
                }
                if (endGameConditions(players)) {
                    return true;
                }
                if (callback != null) {
                    callback.accept(players.get(i).getId());
                }
                players.get(i).makeMove();

                if (callback != null) {
                    callback.accept(null);
                }

            }
        }
        for (Player player : players) {
            if (player.getState().equals(PlayerState.CHECK)) {
                player.setState(PlayerState.DEFAULT);
            }
        }
        return false;
    }

    public void oneRound(List<Player> players, Table table) {

        for (Player player : players) {
            player.state = PlayerState.DEFAULT;
            player.generateHand(table.getDeck());
        }

        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage("\n" + newGame);
            }
        }

        players.get(0).bet = 100;
        table.setCurrentBet(100);

        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage(myCards + ": ");
                player.ui.showHand(players.get(0).getHand());
            }
        }

        boolean allIn;

        for (GameStages gs : GameStages.values()) {
            allIn = bankrupts(players) > 0;

            if (!allIn) {
                if (bettingCircle(players)) {
                    return;
                }
                for (Player player : players) {
                    table.setBank(table.getBank() + player.bet);
                    if (player.getState() != PlayerState.FOLD) {
                        player.cash -= player.bet;
                    }
                    player.bet = 0;
                }
                table.setCurrentBet(0);
            }
            for (int i = 0; i < gs.getCardAmount(); i++) {
                table.openCard();
            }
            for (Player player : players) {
                if (player.ui != null) {
                    player.ui.showTable(table);
                }
            }
        }

        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage(opening);
            }
        }

        wd = new WinnerDeterminator(players);
        Player[] winners = wd.determineTheWinner();
        roundWinners = new ArrayList<>(Arrays.asList(winners));
        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage(rb.getString("winners") + ": ");
            }
        }
        for (Player player : players) {
            for (Player winner : winners) {
                if (player.equals(winner)) {
                    if (player.ui != null) {
                        player.ui.showMessage(player.name);
                    } else {
//                        players.get(0).ui.showMessage(player.name);
                    }
                    player.cash += (table.getBank()) / winners.length;
                }
            }
        }

        if (callback != null) {
            callback.accept(null);
        }

        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage(rb.getString("withComb") + " " + winners[0].combination.combination.toString());
            }
        }

        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage(players.toString());
            }
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void start() {
        for (Player player : players) {
            player.setTable(table);
        }
        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage(players.toString());
            }
        }
        new Thread(() -> {
            while (true) {
                if (bankrupts(players) > 0) {
                    for (Player player : players) {
                        if (player.ui != null) {
                            player.ui.showMessage(end);
                            return;
                        }
                    }
                }
                oneRound(players, table);
                roundWinners.clear();
                table.setBank(0);
                table.setCurrentBet(0);
                table.reset();
                table.amountOfFolds = 0;
                for (Player player : players) {
                    player.combination = null;
                }
            }
        }).start();
    }

    public List<Player> getRoundWinners() {
        return roundWinners;
    }
}
