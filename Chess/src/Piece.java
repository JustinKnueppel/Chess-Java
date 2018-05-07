import java.util.ArrayList;

public interface Piece {
    /**
     * Get the team of this.
     * @return
     *      true for team 1, false for team 2
     */
    boolean getTeam();
    /**
     * Moves the given piece to a new square.
     * @param newSquare
     *      The target square for this
     */
    void move(Square newSquare);

    /**
     * Initial placement of this.
     * @param square
     *      The initial square for this
     */
    void setSquare(Square square);

    /**
     * Updates this.moves with possible moves, then returns them.
     * @return a list of possible moves.
     */
    ArrayList<String> getPossibleMoves();

    /**
     * Checks if the pawn has moved.
     * @return true iff the pawn has moved
     */
    boolean hasMoved();

    /**
     * Get the type of piece.
     * @return String representation of piece type
     */
    String getType();

}