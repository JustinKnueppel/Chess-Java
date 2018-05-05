public interface Pawn extends Piece {
    /**
     * Promote pawn to a new piece.
     * @param newPiece
     *      piece to replace the pawn
     */
    void PromoteTo(Piece newPiece);

    /**
     * Checks if the pawn has moved.
     * @return true iff the pawn has moved
     */
    boolean hasMoved();
}