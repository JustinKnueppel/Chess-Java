public class Square {
    private String id;
    private boolean occupied;

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
}