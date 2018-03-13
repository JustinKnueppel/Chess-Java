public class Square {
    private String id;
    private boolean occupied;
    private Piece piece;
    /**
     * Public constructor to initialize a square.
     * @param id
     *      Name of this
     */
    public Square(String id) {
        this.id = id;
        this.occupied = false;
    }

    /**
     *
     * @return
     *      this.occupied
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     *
     * @param occupied
     *      new status of this.occupied
     * @updates this.occupied
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * Place a piece on the this.
     * @param piece
     *      Piece to be placed on this
     */
    public void putPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     *
     * @return piece on this.
     */
    public Piece getPiece() {
        return this.piece;
    }
}