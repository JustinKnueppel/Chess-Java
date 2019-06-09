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
        this.board.initializePieces();
    }

    public ArrayList<Piece> getPieces() {
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces.addAll(this.board.getBlackPieces());
        pieces.addAll(this.board.getWhitePieces());
        return pieces;
    }
    public boolean legalMove(Coordinates oldCoords, Coordinates newCoords) {
        //TODO: Determine if move is legal
        return true;
    }
    public void makeMove(Coordinates oldCoords, Coordinates newCoords) {
        //TODO: Make move
    }
}
