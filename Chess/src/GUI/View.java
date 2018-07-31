package GUI;

import java.util.ArrayList;

import Game.Board;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class View extends Application{
    private Controller controller;
    public final static int Y_OFFSET = 7;
    private GridPane squares;
    private GridPane pieces;

    public static void main(String args) {
        launch(args);
    }
    private void createMVC() {  
        Model model = new Model();
        this.controller = new Controller(this, model);
    }

    public static final int TILE_SIZE = 100;

    private Parent createContent() {
    	Group root = new Group();
        squares = new GridPane();
        pieces = new GridPane();
        
        root.getChildren().add(squares);
        root.getChildren().add(pieces);

        return root;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        createMVC();
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
    	launch(args);
    }
    
    public GridPane getPieces() {
    	return this.pieces;
    }
    
    public GridPane getSquares() {
    	return this.squares;
    }



}
