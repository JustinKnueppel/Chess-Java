package Game.Pieces;

import Game.Coordinates;
import Game.MoveType;
import Game.Square;

public final class Move {
    private Piece oldPiece;
    private Piece newPiece;
    private Coordinates oldCoordinates;
    private Coordinates newCoordinates;
    private MoveType moveType;

    public Move(Piece movingPiece, Square newSquare, MoveType moveType) {
        this.newPiece = movingPiece;
        this.newCoordinates = newSquare.getID();
        this.oldCoordinates = movingPiece.getCoordinates();
        this.oldPiece = newSquare.isOccupied() ? newSquare.getPiece() : null;
        this.moveType = moveType;
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

    public MoveType getMoveType() {
        return moveType;
    }
}
