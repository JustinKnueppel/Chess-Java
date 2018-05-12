import java.util.ArrayList;

public abstract class Piece {
    protected Board.TeamColor team;
    protected boolean hasMoved;
    protected Square square;
    protected ArrayList<String> moves;
    protected Board.PieceType type;
    protected Board board;
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
    ArrayList<String> getPossibleMoves() {
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

}