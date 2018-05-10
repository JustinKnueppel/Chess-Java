import java.util.ArrayList;

public interface Piece {
    /**
     * Get the team of this.
     * @return
     *      TeamColor of this
     */
    Board.TeamColor getTeam();
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
     * Get the type of piece.
     * @return piece type
     */
    Board.PieceType getType();

}