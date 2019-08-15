package GUI;

import java.util.ArrayList;

import Game.Board;
import Game.Coordinates;
import Game.Pieces.Piece;
import Game.TeamColor;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
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
     * Get Pieces grid of proper size.
     * @return grid of placeholders for the pieces
     */
    private GridPane getPiecesGrid() {
        GridPane piecesGrid = new GridPane();

        for (int i = 0; i < Board.GRID_SIZE; i++) {
            piecesGrid.getColumnConstraints().add(new ColumnConstraints(TILE_SIZE));
        }

        for (int i = 0; i < Board.GRID_SIZE; i++) {
            piecesGrid.getRowConstraints().add(new RowConstraints(TILE_SIZE));
        }

        for (int i = 0; i < Board.GRID_SIZE; i++) {
            for (int j = 0; j < Board.GRID_SIZE; j++) {
                ImageView imageView = new ImageView();

                imageView.setFitHeight(TILE_SIZE);
                imageView.setFitWidth(TILE_SIZE);

                /*
                 * Set event handlers
                 */

                imageView.setOnMousePressed(imageViewOnMousePressedEventHandler);
                imageView.setOnMouseDragged(imageViewOnMouseDraggedEventHandler);
                imageView.setOnMouseReleased(imageViewOnMouseReleasedEventHandler);

                /*
                 * Place view
                 */
                Coordinates coordinates = Controller.convertBoardCoordinates(new Coordinates(i, j));
                piecesGrid.add(imageView, coordinates.getX(), coordinates.getY());
            }
        }

        return piecesGrid;
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
        this.pieces = getPiecesGrid();
        
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
     * Return the square at the given coordinates.
     * @param viewCoordinates
     *          Coordinates of desired square
     * @return  the square at the given coordinates.
     */
    private Node getSquare(final Coordinates viewCoordinates) {
        Node result = null;

        for (Node node: this.pieces.getChildren()) {
            if (this.pieces.getColumnIndex(node) == viewCoordinates.getX() && this.pieces.getRowIndex(node) == viewCoordinates.getY()) {
                result = node;
                break;
            }
        }
        return result;
    }

    /**
     * Place piece on board.
     * @param piece
     *          Piece to be placed
     */
    private void placePiece(Piece piece) {
        StringBuilder picturePath = new StringBuilder("file:Chess/Images/");
        picturePath.append(piece.getTeam().equals(TeamColor.WHITE) ? "White" : "Black");

        switch (piece.getType()) {
            case KING:
                picturePath.append("King");
                break;
            case QUEEN:
                picturePath.append("Queen");
                break;
            case ROOK:
                picturePath.append("Rook");
                break;
            case PAWN:
                picturePath.append("Pawn");
                break;
            case BISHOP:
                picturePath.append("Bishop");
                break;
            case KNIGHT:
                picturePath.append("Knight");
        }

        picturePath.append(".png");

        Image pieceImage = new Image(picturePath.toString());

        /*
         * Place piece
         */

        Node square = getSquare(Controller.convertBoardCoordinates(piece.getCoordinates()));
        if (!(square instanceof ImageView)) {
            System.out.println("Image View not found");
            return;
        }

        ImageView imageView = (ImageView)square;
        imageView.setImage(pieceImage);
    }

    /**
     * Process potential move on mouse released.
     * @param oldX
     *      Original x coordinate
     * @param oldY
     *      Original y coordinate
     * @param newX
     *      New x coordinate
     * @param newY
     *      New y coordinate
     */
    private void processMove(double oldX, double oldY, double newX, double newY) {
        this.controller.processMove(oldX, oldY, newX, newY);
    }

    /*
     * ==================
     * Event handlers
     * ==================
     */

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private Coordinates start, finish;

    private EventHandler<MouseEvent> imageViewOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    orgSceneX = mouseEvent.getSceneX();
                    orgSceneY = mouseEvent.getSceneY();
                    orgTranslateX = ((ImageView)(mouseEvent.getSource())).getTranslateX();
                    orgTranslateY = ((ImageView)(mouseEvent.getSource())).getTranslateY();
                }
            };

    private EventHandler<MouseEvent> imageViewOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    double offsetX = mouseEvent.getSceneX() - orgSceneX;
                    double offsetY = mouseEvent.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((ImageView)(mouseEvent.getSource())).setTranslateX(newTranslateX);
                    ((ImageView)(mouseEvent.getSource())).setTranslateY(newTranslateY);
                }
            };

    private EventHandler<MouseEvent> imageViewOnMouseReleasedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    double newX = mouseEvent.getSceneX();
                    double newY = mouseEvent.getSceneY();

                    processMove(orgSceneX, orgSceneY, newX, newY);
                }
            };

    /*
     * =====================================
     * Public methods for controller to use.
     * =====================================
     */

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
        for (Node node: this.pieces.getChildren()) {
            if (!(node instanceof ImageView)) {
                continue;
            }
            ImageView imageView = (ImageView) node;
            imageView.setImage(null);
        }
    }
}
