public interface Piece {
    /**
     * Moves the given piece to a new square.
     * @param newSquare
     *      The target square for this
     */
    void move(Square newSquare);

    /**
     * Capture a piece on the target square.
     * @param target
     *      the home square of the piece to be captured
     */
    void capture(Square target);

    /**
     * @updates this.possibleMoves with the possible moves
     */
    void updatePossibleMoves();

    /**
     *
     * @return a list of possible moves
     */
    String[] getPossibleMoves();

    /**
     * Prints the move logic for the piece.
     */
    void help ();
}