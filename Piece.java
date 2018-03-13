public interface Piece {
    /**
     * Moves the given piece to a new square.
     * @param newSquare
     *      The target square for this
     */
    void move(Square newSquare);

    /**
     * @updates this.possibleMoves with the possible moves
     */
    void updatePossibleMoves();

    /**
     * Prints the move logic for the piece.
     */
    void help ();
}