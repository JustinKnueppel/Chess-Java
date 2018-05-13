public class Square {
    private Board.Coordinates id;
    private boolean occupied;
    private Piece piece;
    /**
     * Public constructor to initialize a square.
     * @param id
     *      Name of this
     */
    Square(Board.Coordinates id) {
        this.id = id;
        this.occupied = false;
    }

    /**
     * Determines whether or not the given square is occupied.
     * @return
     *      this.occupied
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Set piece to null and occupied to false.
     * @replaces this.piece
     * @replaces this.occupied
     */
    public void setVacant() {
        this.occupied = false;
        this.piece = null;
    }

    /**
     * Place a piece on the this.
     * @param piece
     *      Piece to be placed on this
     */
    public void putPiece(Piece piece) {
        this.piece = piece;
        this.occupied = true;
    }

    /**
     *
     * @return piece on this.
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Retrieve the ID for the square
     * @return this.id
     */
    public Board.Coordinates getID(){
        return this.id;
    }
}