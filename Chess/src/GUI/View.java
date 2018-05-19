package GUI;

import Game.Square;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        GridPane root = new GridPane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        for (int rank = 0; rank < HEIGHT; rank++) {
            for (int file = 0; file < WIDTH; file++) {
                Square square = controller.getModel().getGrid()[rank][file];
                tileGroup.getChildren().add(square);
                if (square.isOccupied()) {
                    pieceGroup.getChildren().add(square.getPiece());
                }
            }
        }
        root.getChildren().addAll(tileGroup, pieceGroup);

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
