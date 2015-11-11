package net.joshstevens.tictactoe.models;

/**
 * Created by joshstevens on 11/8/15.
 */
public class Game {
    private int id;
    private Square[][] squares;
    private String currentPlayer;

    public int getId() {
        return id;
    }

    public Game(int id) {
        this.id = id;
        squares = new Square[][]{
                new Square[]{Square.EMPTY, Square.EMPTY, Square.EMPTY},
                new Square[]{Square.EMPTY, Square.EMPTY, Square.EMPTY},
                new Square[]{Square.EMPTY, Square.EMPTY, Square.EMPTY},};
        currentPlayer = "X";
    }

    public Square[][] getBoard() {
        return squares;
    }

    public boolean checkSquare(int row, int col) {
        return squares[row][col] == Square.EMPTY;
    }

    public void makeMove(int row, int col) {

        if (currentPlayer == "X") {
            squares[row][col] = Square.X;
            currentPlayer = "O";
        } else {
            squares[row][col] = Square.O;
            currentPlayer = "X";
        }
    }
}
