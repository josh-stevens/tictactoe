package net.joshstevens.tictactoe.models;

/**
 * Created by joshstevens on 11/8/15.
 */
public class Game {
    private int id;
    private Square[][] squares;

    public int getId() {
        return id;
    }

    public Game(int id) {
        this.id = id;
        squares = new Square[][]{
                new Square[]{Square.EMPTY, Square.EMPTY, Square.EMPTY},
                new Square[]{Square.EMPTY, Square.EMPTY, Square.EMPTY},
                new Square[]{Square.EMPTY, Square.EMPTY, Square.EMPTY},};
    }
}
