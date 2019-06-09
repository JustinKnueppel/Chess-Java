package Game.Pieces;

import Game.Coordinates;
import Game.MoveType;
import Game.Square;

public final class Move {
    private boolean wasCapture;
    private Piece oldPiece;
    private Piece newPiece;
    private boolean newPieceHadMoved;
    private Coordinates oldCoordinates;
    private Coordinates newCoordinates;
    private MoveType moveType;
    private Piece castleRook;
    private Piece enPassantPawn;

    public Move(Piece movingPiece, Square newSquare, MoveType moveType) {
        this.newPiece = movingPiece;
        this.newPieceHadMoved = movingPiece.hasMoved();
        this.newCoordinates = newSquare.getID();
        this.oldCoordinates = movingPiece.getCoordinates();
        this.wasCapture = newSquare.isOccupied() || moveType == MoveType.EN_PASSANT;
        /*
        Old piece will be separate from a pawn captured via En Passant
         */
        this.oldPiece = newSquare.isOccupied() ? newSquare.getPiece() : null;
        this.moveType = moveType;
    }
    public boolean wasCapture() {return wasCapture;}
    public Piece getOldPiece() {
        return oldPiece;
    }

    public Piece getNewPiece() {
        return newPiece;
    }

    public boolean newPieceHadMoved() {return newPieceHadMoved; }

    public Coordinates getNewCoordinates() {
        return newCoordinates;
    }

    public Coordinates getOldCoordinates() { return oldCoordinates; }

    public MoveType getMoveType() {
        return moveType;
    }

    public Piece getCastleRook() {
        return castleRook;
    }

    public void setCastleRook(Piece castleRook) {
        this.castleRook = castleRook;
    }

    public void setEnPassantPawn(Piece enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }

    public Piece getEnPassantPawn() {
        return enPassantPawn;
    }
}
