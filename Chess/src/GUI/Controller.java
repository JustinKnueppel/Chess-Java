package GUI;

import Game.Board;
import Game.Square;
import javafx.scene.image.Image;

public class Controller {
    private View view;
    private Model model;
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public View getView() {
        return view;
    }
    private void updateView() {
    	Square[][] grid = model.getGrid();
    	Image[][] pieces = view.getPieces();
    	for (int x = 0; x < model.getBoard().GRID_SIZE; x++) {
    		for (int y = 0; y < model.getBoard().GRID_SIZE; y++ ) {
    			pieces[x][y] = grid[x][y].isOccupied() ? grid[x][y].getPiece().getImage() : null;
    		}
    	}
    }
}
