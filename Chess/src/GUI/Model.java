package GUI;

import Game.Board;
import Game.Square;

public class Model {
    private Board board;
    private Square[][] grid;
    public Model() {
        this.board = new Board();
        this.grid = board.getGrid();
    }
}
