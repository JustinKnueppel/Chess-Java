import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class View extends Application {

    public static final int TILE_SIZE = 100;
    private static final String DARK_SQUARE_COLOR = "#769656";
    private static final String LIGHT_SQUARE_COLOR = "#eeeed2";

    /**
     * Create square of a given color.
     * @param light
     *          Color of the target square
     * @return Finished square
     */
    private Rectangle makeSquare(boolean light) {
        Rectangle square = new Rectangle();
        square.setWidth(TILE_SIZE);
        square.setHeight(TILE_SIZE);
        String color = light ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR;
        square.setFill(Paint.valueOf(color));
        return square;
    }

    /**
     * Create chess board.
     * @return Visual representation of chess board
     */
    private GridPane getSquares() {
        GridPane squares = new GridPane();

        for (int i = 0; i < Board.GRID_SIZE; i++) {
            for (int j = 0; j < Board.GRID_SIZE; j++) {
                squares.add(makeSquare((i + j) % 2 == 0), i, j);
            }
        }

        return squares;
    }

    private Parent createContent() {
        Group root = new Group();

        root.getChildren().add(getSquares());

        return root;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
