package Game.Pieces;

import Game.Coordinates;
import Game.Square;

public final class MoveType {
    private Piece oldPiece;
    private Piece newPiece;
    private Coordinates oldCoordinates;
    private Coordinates newCoordinates;

    public MoveType(Piece movingPiece, Square newSquare) {
        this.newPiece = movingPiece;
        this.newCoordinates = newSquare.getID();
        this.oldCoordinates = movingPiece.getCoordinates();
        this.oldPiece = newSquare.isOccupied() ? newSquare.getPiece() : null;
    }
    public Piece getOldPiece() {
        return oldPiece;
    }

    public Piece getNewPiece() {
        return newPiece;
    }

    public Coordinates getNewCoordinates() {
        return newCoordinates;
    }

    public Coordinates getOldCoordinates() {

        return oldCoordinates;
    }
}
