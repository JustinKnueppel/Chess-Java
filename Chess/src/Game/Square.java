package Game;

import GUI.View;
import Game.Pieces.Piece;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle{
    private final String LIGHT_COLOR = "#feb";
    private final String DARK_COLOR = "#582";
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
        setVacant();
        setWidth(View.TILE_SIZE);
        setHeight(View.TILE_SIZE);
        setFill(light ? Color.valueOf(LIGHT_COLOR) : Color.valueOf(DARK_COLOR));

        relocate(id.getY() * View.TILE_SIZE, id.getX() * View.TILE_SIZE);
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
     *      Game.Pieces.Piece to be placed on this
     */
    public void putPiece(Piece piece) {
        this.piece = piece;
        this.piece.move(this.id);
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
    public Coordinates getID(){
        return this.id;
    }
}