package GUI;

import Game.Board;
import Game.Coordinates;
import Game.Pieces.Piece;
import Game.Square;

import java.util.ArrayList;

public class Model {
    private Board board;
    private Square[][] grid;
    public Model() {
        this.board = new Board();
        this.grid = board.getGrid();
    }

    public ArrayList<Piece> getPieces() {
        //TODO: Get pieces from board
        return new ArrayList<>();
    }
    public boolean legalMove(Coordinates oldCoords, Coordinates newCoords) {
        //TODO: Determine if move is legal
        return false;
    }
    public void makeMove(Coordinates oldCoords, Coordinates newCoords) {
        //TODO: Make move
    }
}
