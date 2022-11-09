package ru.vsu.csf.poker.model;

import ru.vsu.csf.poker.enums.GameStages;
import ru.vsu.csf.poker.enums.PlayerState;

import java.util.*;


public class Game {

    protected Table table;
    private WinnerDeterminator wd;

    public Game() {
        table = new Table();
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
            System.out.println("Round is over");
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
        for (int i = 1; i < players.size(); i++) {
            if (!players.get(i).getState().equals(PlayerState.FOLD)) {
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
            for (Player player : players) {
                if (player.getState().equals(PlayerState.FOLD)) {
                    continue;
                }
                if (endGameConditions(players)) {
                    return true;
                }
                player.makeMove();
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
                player.ui.showMessage("\nNew game!");
            }
        }

        players.get(0).bet = 100;
        table.setCurrentBet(100);

        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage("My cards: ");
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
                    player.cash -= player.bet;
                    player.bet = 0;
                }
                table.setCurrentBet(0);
            }
            int destPos = gs.getNum() == 1 ? 0 : gs.getNum() == 2 ? 3 : gs.getNum() == 3 ? 4 : 5;
            System.arraycopy(table.generateCards(gs), 0, table.table, destPos, gs.getCardAmount());
            for (Player player : players) {
                if (player.ui != null) {
                    player.ui.showTable(table);
                }
            }
        }

        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage("Opening up!");
            }
        }

        wd = new WinnerDeterminator(players);
        Player[] winners = wd.determineTheWinner();
        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage("Winners: ");
            }
        }
        for (Player player : players) {
            for (Player winner : winners) {
                if (player.equals(winner)) {
                    if (player.ui != null) {
                        player.ui.showMessage(player.name);
                    } else {
                        players.get(0).ui.showMessage(player.name);
                    }
                    player.cash += (table.getBank()) / winners.length;
                }
            }
        }

        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage("with combination " + winners[0].combination.combination.toString());
            }
        }

        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage(players.toString());
            }
        }
    }

    public void start(List<Player> players) {
        for (Player player : players) {
            player.setTable(table);
        }
        for (Player player : players) {
            if (player.ui != null) {
                player.ui.showMessage(players.toString());
            }
        }
        while (true) {
            if (bankrupts(players) > 0) {
                for (Player player : players) {
                    if (player.ui != null) {
                        player.ui.showMessage("Game is end! Somebody haven't got money");
                        return;
                    }
                }
            }
            oneRound(players, table);
            table.setBank(0);
            table.setCurrentBet(0);
            table.table = new Card[5];
            table.amountOfFolds = 0;
            for (Player player : players) {
                player.combination = null;
            }
        }
    }

}
