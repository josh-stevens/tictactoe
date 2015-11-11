package net.joshstevens.tictactoe.models;

/**
 * Created by joshstevens on 11/8/15.
 */
public class Game {
    private int id;
    private char[][] board;
    private char currentPlayer;
    private char winner;
    private boolean inProgress;

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
        inProgress = true;
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
        if (inProgress) {
            boardString.append("Next player: ").append(getPlayer()).append("\n\n");
        } else {
            boardString.append("Game over! Player ").append(getWinner()).append(" wins!\n\n");
        }

        return boardString.toString();
    }

    public char getPlayer() {
        return currentPlayer;
    }

    public char getWinner() {
        return winner;
    }

    public boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            // First time through, we'll check diagonals
            if (i == 0) {
                if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[0][0] == board[2][2]) return true;
                if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[0][2] == board[2][0]) return true;
            }
            // Check each row and column
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][0] == board[i][2]) return true;
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[0][i] == board[2][i]) return true;
        }
        return false;
    }

    public boolean checkProgress() {
        return inProgress;
    }

    public boolean checkSquare(int row, int col) {
        return board[row][col] == ' ';
    }

    public void makeMove(int row, int col) {
        board[row][col] = currentPlayer;
        if (checkWinner()) {
            inProgress = false;
            winner = currentPlayer;
        }
        else if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }
}
