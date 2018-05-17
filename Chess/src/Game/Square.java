package Game;

import GUI.View;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle{
    private Coordinates id;
    private boolean occupied;
    private Piece piece;
    /**
     * Public constructor to initialize a square.
     * @param id
     *      Name of this
     * @param light
     *      True if this is a light square
     */
    public Square(boolean light, Coordinates id) {
        this.id = id;
        this.occupied = false;
        setWidth(View.TILE_SIZE);
        setHeight(View.TILE_SIZE);
        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));

        relocate(id.getFile() * View.TILE_SIZE, id.getRank() * View.TILE_SIZE);
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
     */
    public void setVacant() {
        this.occupied = false;
        this.piece = null;
    }

    /**
     * Place a piece on the this.
     * @param piece
     *      Game.Piece to be placed on this
     */
    public void putPiece(Piece piece) {
        this.piece = piece;
        this.occupied = true;
        this.piece.setSquare(this);
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
    public Coordinates getID(){
        return this.id;
    }
}