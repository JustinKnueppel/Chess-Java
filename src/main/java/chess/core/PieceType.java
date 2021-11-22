package chess.core;

public enum PieceType {
    PAWN, KING, KNIGHT, BISHOP, ROOK, QUEEN;

    @Override
    public String toString() {
        return switch(this) {
            case KING -> "King";
            case QUEEN -> "Queen";
            case BISHOP -> "Bishop";
            case KNIGHT -> "Knight";
            case ROOK -> "Rook";
            case PAWN -> "Pawn";
        };
    }
}
