import java.util.ArrayList;

public abstract class Piece {
    Board.TeamColor team;
    boolean hasMoved;
    Square square;
    ArrayList<Coordinates> moves;
    Board.PieceType type;
    Board board;
    /**
     * Get the team of this.
     * @return
     *      TeamColor of this
     */
    Board.TeamColor getTeam() {
        return this.team;
    }
    /**
     * Moves the given piece to a new square.
     * @param newSquare
     *      The target square for this
     */
    void move(Square newSquare) {
        this.square.setVacant();
        this.square = newSquare;
        this.hasMoved = true;
    }

    /**
     * Initial placement of this.
     * @param square
     *      The initial square for this
     */
    void setSquare(Square square) {
        this.square = square;
    }
    /**
     * Updates the possible moves based on the current state of board, and the piece logic.
     */
    protected abstract void updatePossibleMoves();

    /**
     * Updates this.moves with possible moves, then returns them.
     * @return a list of possible moves.
     */
    ArrayList<Coordinates> getPossibleMoves() {
        updatePossibleMoves();
        return this.moves;
    }

    /**
     * Get the type of piece.
     * @return piece type
     */
    Board.PieceType getType() {
        return this.type;
    }

    /**
     * Determine if the piece has moves.
     * @return this.hasMoved
     */
    public boolean hasMoved() {
        return hasMoved;
    }
}