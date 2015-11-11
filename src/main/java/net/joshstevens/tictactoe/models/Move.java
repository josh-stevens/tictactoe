package net.joshstevens.tictactoe.models;

/**
 * Created by joshstevens on 11/11/15.
 */
public class Move {
    public int row, col;

    public Move(String row, String col) {
        this.row = Integer.parseInt(row);
        this.col = Integer.parseInt(col);
    }

    public Move() {

    }
}
