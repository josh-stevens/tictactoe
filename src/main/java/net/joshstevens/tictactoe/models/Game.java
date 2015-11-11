package net.joshstevens.tictactoe.models;

/**
 * Created by joshstevens on 11/8/15.
 */
public class Game {
    private int id;
    private char[][] board;
    private char currentPlayer;

    public int getId() {
        return id;
    }

    public Game(int id) {
        this.id = id;
        board = new char[][]{
                new char[]{' ', ' ', ' '},
                new char[]{' ', ' ', ' '},
                new char[]{' ', ' ', ' '},};
        currentPlayer = 'X';
    }

    public String parseBoard() {
        /*
        Build a string by iterating through the rows of the board. Adds an extra dashed line after the
        final row, which is deleted after the for loop. Tell the user who goes next after printing the
        board.
        */
        StringBuilder boardString = new StringBuilder("\n");
        for (char[] row : board) {
            boardString.append(row[0]).append("|").append(row[1]).append("|").append(row[2]).append("\n-----\n");
        }
        boardString.delete(31,38);
        boardString.append("Next player: ").append(getPlayer()).append("\n\n");
        return boardString.toString();
    }

    public char getPlayer() {
        return currentPlayer;
    }

    public boolean checkSquare(int row, int col) {
        return board[row][col] == ' ';
    }

    public void makeMove(int row, int col) {
        board[row][col] = currentPlayer;
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }
}
