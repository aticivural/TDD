package com.vural;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * Created by vural on 01-Jun-17.
 */
public class Connect4 {

    public enum Color {
        RED('R'), GREEN('G'), EMPTY(' ');

        private final char value;

        Color(char value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public static final int COLUMNS = 7;
    public static final int ROWS = 7;

    private Color[][] board = new Color[COLUMNS][ROWS];

    public Connect4() {
        for (Color[] column : board) {
            Arrays.fill(column, Color.EMPTY);
        }
    }


    public void putDisc(int column) {
        if (column > 0 && column <= COLUMNS) {
            int numOfDisc = getNumberOfDiscInColumn(column - 1);
            if (numOfDisc < ROWS) {
                board[column - 1][numOfDisc] = currentPlayer;
                printBoard();
                switchPlayer();
            } else {
                System.out.println(numOfDisc);
                System.out.println("There is no room for a new disc in this column");
                printBoard();
            }
        } else {
            System.out.println("Column out of bounds");
            printBoard();
        }
    }

    private int getNumberOfDiscInColumn(int column) {
        if (column >= 0 && column < COLUMNS) {
            int row;
            for (row = 0; row < ROWS; row++) {
                if (Color.EMPTY == board[column][row]) {
                    return row;
                }
            }
            return row;
        }
        return -1;
    }

    private Color currentPlayer = Color.RED;
    private static final String DELIMITER = "|";

    private void switchPlayer() {
        if (Color.RED == currentPlayer) {
            currentPlayer = Color.GREEN;
        } else {
            currentPlayer = Color.GREEN;
        }
        System.out.println("Current turn: " + currentPlayer);
    }

    public void printBoard() {
        for (int row = ROWS - 1; row >= 0; --row) {
            StringJoiner stringJoiner = new StringJoiner(DELIMITER, DELIMITER, DELIMITER);

            for (int col = 0; col < COLUMNS; ++col) {
                stringJoiner.add(board[col][row].toString());
            }
            System.out.println(stringJoiner.toString());
        }
    }

    public boolean isFinished() {
        if (winner != null) {
            return true;
        }
        int numOfDisc = 0;
        for (int col = 0; col < COLUMNS; ++col) {
            numOfDisc += getNumberOfDiscInColumn(col);
        }

        if (numOfDisc >= COLUMNS * ROWS) {
            System.out.println("It's a draw");
            return true;
        }
        return false;
    }

    private Color winner;

    public static final int DISCS_FOR_WIN = 4;


    private void checkWinCondition(int col, int row) {
        Pattern winPattern = Pattern.compile(".*" + currentPlayer + "{" + DISCS_FOR_WIN + "}.*");

        //vertical check
        StringJoiner stringJoiner = new StringJoiner("");
        for (int auxRow = 0; auxRow < ROWS; ++auxRow) {
            stringJoiner.add(board[col][auxRow].toString());
        }

        if (winPattern.matcher(stringJoiner.toString()).matches()) {
            winner = currentPlayer;
            System.out.println(currentPlayer + " wins");
        }

        //horizontal check
        stringJoiner = new StringJoiner("");
        for (int column = 0; column < COLUMNS; ++column) {
            stringJoiner.add(board[column][row].toString());
        }

        if (winPattern.matcher(stringJoiner.toString()).matches()) {
            winner = currentPlayer;
            System.out.println(currentPlayer + " wins");
            return;
        }

        //diagonal checks

        int startOffset = Math.min(col, row);
        int column = col - startOffset;
        int auxRow = row - startOffset;

        stringJoiner = new StringJoiner("");
        do {
            stringJoiner.add(board[column++][auxRow++].toString());
        } while (column < COLUMNS && auxRow < ROWS);

        if (winPattern.matcher(stringJoiner.toString()).matches()){
            winner = currentPlayer;
            System.out.println(currentPlayer + " wins.");
            return;
        }

        startOffset = Math.min(col, ROWS - 1 - row);
        column = col - startOffset;
        auxRow = row + startOffset;
        do {
            stringJoiner.add(board[column++][auxRow--].toString());
        }while (column < COLUMNS && auxRow >= 0);

        if (winPattern.matcher(stringJoiner.toString()).matches()){
            winner = currentPlayer;
            System.out.println(currentPlayer + " wins");
        }
    }

}




























