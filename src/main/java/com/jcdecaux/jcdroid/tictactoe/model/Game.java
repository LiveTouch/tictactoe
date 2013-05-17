package com.jcdecaux.jcdroid.tictactoe.model;

import java.util.HashMap;
import java.util.Map;

import static com.jcdecaux.jcdroid.tictactoe.model.Game.Player.*;
import static com.jcdecaux.jcdroid.tictactoe.model.Game.State.*;

public class Game {

    private static final int GAMEBOARD_SIZE = 3;

    private static final int EMPTY = -1;

    private final GameListener gameListener;

    private Player turn;

    private State state = PLAYING;

    private Map<Player, Integer> scores = new HashMap<Player, Integer>();

    private int[][] gameboard;

    public Game(GameListener listener, Player startingPlayer) {
        if (listener == null)
            throw new IllegalArgumentException("Listener shouldn't be null");
        gameListener = listener;
        scores.put(YOU, 0);
        scores.put(HIM, 0);
        turn = startingPlayer;
        gameboard = generateGameboard(GAMEBOARD_SIZE);
    }

    public void startNewGame() {
        if (state == PLAYING)
            throw new IllegalStateException("Game already in progress");

        state = PLAYING;
        gameboard = generateGameboard(GAMEBOARD_SIZE);
        gameListener.onGameboardChanged();
        setTurn(not(turn));
    }

    public boolean canPlay(int line, int column) {
        return isInBounds(line, column) && gameboard[line][column] == EMPTY;
    }

    public void play(Player player, int line, int column) {
        if (!isTurn(player))
            throw new IllegalStateException("Not " + player + "'s turn");
        if (!canPlay(line, column))
            throw new IllegalArgumentException("Can't play here.");

        setGameboard(line, column, player);

        if (hasWon(player)) {
            state = WAIT_NEW;
            incrementScore(player);
            gameListener.onPlayerWin(player);

        } else if (isFull(gameboard)) {
            state = WAIT_NEW;
            gameListener.onDraw();

        } else {
            setTurn(not(player));
        }

    }

    private void incrementScore(Player player) {
        scores.put(player, scores.get(player) + 1);
        gameListener.onScoreChanged();
    }

    private void setGameboard(int line, int column, Player player) {
        gameboard[line][column] = player.id;
        gameListener.onGameboardChanged();
    }

    private boolean isFull(int[][] gameboard) {
        for (int[] lines : gameboard)
            for (int cell : lines)
                if (cell == -1) return false;
        return true;
    }

    private boolean hasWon(Player player) {
        boolean hasWon = false;
        for (int i = 0; i < GAMEBOARD_SIZE && !hasWon; i++) {
            hasWon |= hasWonLineFromInitialPoint(player, 0, i);
            if (i != 0 && !hasWon)
                hasWon |= hasWonLineFromInitialPoint(player, i, 0);
        }
        return hasWon;
    }

    private boolean hasWonLineFromInitialPoint(Player player, int initialLine, int initialColumn) {
        return hasWonLine(player, initialLine, initialColumn, +1, +1, 0)
                || hasWonLine(player, initialLine, initialColumn, +1, 0, 0)
                || hasWonLine(player, initialLine, initialColumn, +1, -1, 0)
                || hasWonLine(player, initialLine, initialColumn, +1, +1, 0)
                || hasWonLine(player, initialLine, initialColumn, 0, +1, 0)
                || hasWonLine(player, initialLine, initialColumn, -1, +1, 0);
    }

    private boolean hasWonLine(Player player, int initialLine, int initialColumn, int offsetLine, int offsetColumn, int accumulator) {
        if (accumulator == GAMEBOARD_SIZE) return true;
        else if (!isInBounds(initialLine, initialColumn)) return false;
        else if (gameboard[initialLine][initialColumn] == player.id) {
            return hasWonLine(player,
                    initialLine + offsetLine, initialColumn + offsetColumn,
                    offsetLine, offsetColumn,
                    accumulator + 1);
        } else return false;
    }

    private Player not(Player player) {
        return (player == YOU) ? HIM : YOU;
    }

    private int[][] generateGameboard(int size) {
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                result[i][j] = EMPTY;
        return result;
    }

    private boolean isInBounds(int line, int column) {
        return !(line < 0 || line >= GAMEBOARD_SIZE
                || column < 0 || column >= GAMEBOARD_SIZE);
    }

    public boolean isTurn(Player player) {
        return turn == player;
    }

    private void setTurn(Player player) {
        turn = player;
        gameListener.onTurnChanged();
    }

    public Map<Player, Integer> getScores() {
        return scores;
    }

    public int[][] getGameboard() {
        return gameboard;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int[] lines : gameboard) {
            for (int cell : lines) {
                builder.append(cell == -1 ? '-' : String.valueOf(cell));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public static enum State {
        PLAYING, WAIT_NEW
    }

    public static enum Player {
        YOU(0), HIM(1);

        int id;

        Player(int id) {
            this.id = id;
        }
    }

    public static interface GameListener {
        void onScoreChanged();

        void onGameboardChanged();

        void onTurnChanged();

        void onPlayerWin(Player player);

        void onDraw();
    }

}
