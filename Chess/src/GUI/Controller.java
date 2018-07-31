package GUI;

import java.util.ArrayList;

import Game.Board;
import Game.Square;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Controller {
    private View view;
    private Model model;
    
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        updateView();
    }

    public Model getModel() {
        return model;
    }

    public View getView() {
        return view;
    }
    private void updateView() {
    	Square[][] grid = model.getGrid();
    	GridPane pieces = view.getPieces();
    	
    	for (int x = 0; x < Board.GRID_SIZE; x++) {
    		for (int y = 0; y < Board.GRID_SIZE; y++ ) {
    			/*
    			 * Add the pieces to the gridpane, then delete all children before the first added
    			 */
    			Image img = grid[x][y].isOccupied() ? grid[x][y].getPiece().getImage() : null;
    		}
    	}
    }
}
