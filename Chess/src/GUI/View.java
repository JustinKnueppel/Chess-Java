package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

public class View extends Application{
    public static void main(String args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        final int TILE_SIZE = 100;
        final int WIDTH = 8;
        final int HEIGHT = 8;
        primaryStage.setTitle("Chess");


        GridPane root = new GridPane();
        primaryStage.setScene(new Scene(root, WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE));
        primaryStage.show();
    }


}
