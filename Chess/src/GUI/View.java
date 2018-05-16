package GUI;

import Game.Coordinates;
import Game.Square;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class View extends Application{
    private Controller controller;

    public static void main(String args) {
        launch(args);
    }
    private void createMVC() {
        Model model = new Model();
        this.controller = new Controller(this, model);
    }

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Square square = new Square((x + y) % 2 == 0, new Coordinates(x, y));
                tileGroup.getChildren().add(square);
            }
        }

        return root;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        createMVC();
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



}
