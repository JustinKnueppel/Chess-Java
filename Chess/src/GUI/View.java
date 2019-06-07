package GUI;

import java.util.ArrayList;

import Game.Board;
import Game.Coordinates;
import Game.Pieces.Piece;
import Game.TeamColor;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class View extends Application{
    private Controller controller;
    public final static int Y_OFFSET = 7;
    private GridPane squares;
    private GridPane pieces;

    public static final int TILE_SIZE = 100;
    private static final String DARK_SQUARE_COLOR = "#769656";
    private static final String LIGHT_SQUARE_COLOR = "#eeeed2";

    /**
     * Create a square for the chess board.
     * @param light
     *          Determines if the square is light or dark
     * @return a light square if light else a dark square
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
        squares = new GridPane();

        for (int i = 0; i < Board.GRID_SIZE; i++) {
            for (int j = 0; j < Board.GRID_SIZE; j++) {
                squares.add(makeSquare((i + j) % 2 == 0), i, j);
            }
        }

        return squares;
    }

    private Parent createContent() {
        Group root = new Group();
        this.pieces = new GridPane();
        
        root.getChildren().add(getSquares());
        root.getChildren().add(this.pieces);

        return root;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        this.controller = new Controller(this);
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
    	launch(args);
    }

    /**
     * Place piece on board.
     * @param piece
     *          Piece to be placed
     */
    private void placePiece(Piece piece) {

    }

    /*
     * Public methods for controller to use.
     */

    /**
     * Process potential move.
     * @param oldCoords
     *          Starting coordinates of moving piece.
     * @param newCoords
     *          Ending coordinates of moving piece.
     */
    public void processMove(Coordinates oldCoords, Coordinates newCoords) {
        this.controller.processMove(oldCoords, newCoords);
    }
    /**
     * Place given pieces on board.
     * @param pieces
     *      List of pieces to be placed on board
     */
    public void placePieces(ArrayList<Piece> pieces) {
        for (Piece piece: pieces) {
            placePiece(piece);
        }
    }

    /**
     * Remove all pieces from board.
     */
    public void clearBoard() {
        //TODO: Remove all pieces from board
    }



}
