package chess.core;

public class Square {
    private Piece piece;
    public Square() {
        this.piece = null;
    }

    /**
     * Determine if square is occupied.
     * @return true iff a piece occupies this square
     */
    public boolean occupied() {
        return this.piece != null;
    }

    /**
     * Get the piece occupying the square.
     * @return the piece occupying this
     */
    public Piece getPiece() {
        assert this.occupied();
        return piece;
    }

    /**
     * Place piece in square.
     * @param piece
     *      chess.core.Piece to be placed
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }
}
