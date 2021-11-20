package chess.game;

import chess.core.Game;
import chess.view.Controller;
import chess.view.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Game model = new Game();
        View  view = new View();
        Controller controller = new Controller(model, view);
        view.setController(controller);

        primaryStage.setTitle("Chess");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }
}
