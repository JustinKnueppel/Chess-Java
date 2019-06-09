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
    
    public Controller(View view) {
        this.view = view;
        this.model = new Model();
        this.view.placePieces(this.model.getPieces());
    }

    public void processMove(Coordinates oldCoords, Coordinates newCoords) {
        if (this.model.legalMove(oldCoords, newCoords)) {
            this.model.makeMove(oldCoords, newCoords);
            this.view.clearBoard();
            this.view.placePieces(this.model.getPieces());
        }
    }


}
