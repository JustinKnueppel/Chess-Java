package GUI;

import java.util.ArrayList;

import Game.Board;
import Game.Coordinates;
import Game.Square;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller {
    private View view;
    private Model model;

    public final static int Y_OFFSET = 7;

    public Controller(View view) {
        this.view = view;
        this.model = new Model();
        this.view.placePieces(this.model.getPieces());
    }

    /**
     * Process potential move.
     * @param oldX
     *      Original x coordinate
     * @param oldY
     *      Original y coordinate
     * @param newX
     *      New x coordinate
     * @param newY
     *      New y coordinate
     */
    public void processMove(double oldX, double oldY, double newX, double newY) {
        int xCoord = (int)(oldX / (double)View.TILE_SIZE);
        int yCoord = Y_OFFSET - (int)(oldY / (double)View.TILE_SIZE);
        Coordinates oldCoords = new Coordinates(xCoord, yCoord);

        xCoord = (int)(newX / (double)View.TILE_SIZE);
        yCoord = Y_OFFSET - (int)(newY / (double)View.TILE_SIZE);
        Coordinates newCoords = new Coordinates(xCoord, yCoord);

        if (this.model.legalMove(oldCoords, newCoords)) {
            this.model.makeMove(oldCoords, newCoords);
        }
        this.view.clearBoard();
        this.view.placePieces(this.model.getPieces());
    }

    /**
     * Convert board coordinates to visual coordinates
     * @param coordinates
     *          coordinates of piece on board
     * @return coordinates of corresponding square on view
     */
    static Coordinates convertBoardCoordinates(Coordinates coordinates) {
        return new Coordinates(coordinates.getX(), (Y_OFFSET - coordinates.getY()));
    }


}
