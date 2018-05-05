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
     * Set piece to null and occupied to false.
     * @updates this.piece
     * @updates this.occupied
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
    public String getId(){
        return this.id;
    }
}