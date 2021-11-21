package chess.view;

import chess.core.Board;
import chess.core.Piece;
import chess.core.PieceType;
import chess.core.TeamColor;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class JavafxView implements View{

    public static final int TILE_SIZE = 100;
    private static final String DARK_SQUARE_COLOR = "#769656";
    private static final String LIGHT_SQUARE_COLOR = "#eeeed2";
    private final Scene scene;
    private Controller controller;
    private GridPane pieces;
    private final StackPane root;

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
                piecesGrid.add(imageView, i, j);
            }
        }

        return piecesGrid;
    }

    private Group createMainContent() {
        Group main = new Group();
        this.pieces = getPiecesGrid();

        main.getChildren().add(getSquares());
        main.getChildren().add(this.pieces);

        return main;
    }
    private Parent createContent() {
        StackPane root = new StackPane();

        Group mainContent = createMainContent();

        root.getChildren().add(mainContent);
        return root;
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
        this.controller.processMove((int)(oldX/TILE_SIZE),
                (int)(oldY/TILE_SIZE),
                (int)(newX/TILE_SIZE),
                (int)(newY/TILE_SIZE));
    }

    private void processPromotion(PieceType pieceType) {
        this.controller.processPromotion(pieceType);
    }

    private Node getSquare(int x, int y) {
        Node result = null;

        for (Node node: this.pieces.getChildren()) {
            if (GridPane.getColumnIndex(node) == x && GridPane.getRowIndex(node) == y) {
                result = node;
                break;
            }
        }
        return result;
    }

    private String getImagePath(TeamColor teamColor, PieceType pieceType) {
        StringBuilder picturePath = new StringBuilder("/images/");
        picturePath.append(teamColor.equals(TeamColor.WHITE) ? "White" : "Black");

        picturePath.append("-");

        switch (pieceType) {
            case KING -> picturePath.append("King");
            case QUEEN -> picturePath.append("Queen");
            case ROOK -> picturePath.append("Rook");
            case PAWN -> picturePath.append("Pawn");
            case BISHOP -> picturePath.append("Bishop");
            case KNIGHT -> picturePath.append("Knight");
        }

        picturePath.append(".png");

        return picturePath.toString();
    }

    private Image getPieceImage(Piece piece) {
        String imagePath = getImagePath(piece.getColor(), piece.getType());

        return new Image(imagePath);
    }

    private LocatedImage getLocatedPieceImage(TeamColor teamColor, PieceType pieceType) {
        String imagePath = getImagePath(teamColor, pieceType);

        return new LocatedImage(imagePath);
    }

    private PieceType filepathToPieceType(String filepath) {
        String filepathLower = filepath.toLowerCase();
        if (filepathLower.contains("queen")) {
            return PieceType.QUEEN;
        } else if (filepathLower.contains("knight")) {
            return PieceType.KNIGHT;
        } else if (filepathLower.contains("rook")) {
            return PieceType.ROOK;
        } else if (filepathLower.contains("bishop")) {
            return PieceType.BISHOP;
        } else if (filepathLower.contains("pawn")) {
            return PieceType.PAWN;
        } else if (filepathLower.contains("king")) {
            return PieceType.KING;
        } else {
            return null;
        }
    }

    /*
     * ====================
     * == Event Handlers ==
     * ====================
     */

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    private final EventHandler<MouseEvent> imageViewOnMousePressedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    orgSceneX = mouseEvent.getSceneX();
                    orgSceneY = mouseEvent.getSceneY();
                    orgTranslateX = ((ImageView) (mouseEvent.getSource())).getTranslateX();
                    orgTranslateY = ((ImageView) (mouseEvent.getSource())).getTranslateY();
                }
            };

    private final EventHandler<MouseEvent> imageViewOnMouseDraggedEventHandler =
            new EventHandler<>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    double offsetX = mouseEvent.getSceneX() - orgSceneX;
                    double offsetY = mouseEvent.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

                    ((ImageView) (mouseEvent.getSource())).setTranslateX(newTranslateX);
                    ((ImageView) (mouseEvent.getSource())).setTranslateY(newTranslateY);
                }
            };

    private final EventHandler<MouseEvent> imageViewOnMouseReleasedEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    double newX = mouseEvent.getSceneX();
                    double newY = mouseEvent.getSceneY();

                    /* Reset ImageView location */
                    ((ImageView) (mouseEvent.getSource())).setTranslateX(orgTranslateX);
                    ((ImageView) (mouseEvent.getSource())).setTranslateY(orgTranslateY);

                    processMove(orgSceneX, orgSceneY, newX, newY);
                }
            };

    private final EventHandler<MouseEvent> promotionPieceOnMousePressedEventHandler =
            mouseEvent -> {
                ImageView imageView = (ImageView) mouseEvent.getSource();
                LocatedImage image = (LocatedImage) imageView.getImage();
                String imageFilepath = image.getURL();
                PieceType pieceType = filepathToPieceType(imageFilepath);
                processPromotion(pieceType);
            };

    /*
     * ======================
     * == Public interface ==
     * ======================
     */

    public JavafxView() {
        Parent root = createContent();
        this.scene = new Scene(root);

        this.root = (StackPane) root;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Scene getScene() {
        return this.scene;
    }

    public void clearBoard() {
        for (Node node: this.pieces.getChildren()) {
            if (!(node instanceof ImageView imageView)) {
                continue;
            }
            imageView.setImage(null);
        }
    }

    public void placePiece(int x, int y, Piece piece) {
        Image pieceImage = getPieceImage(piece);

        /*
         * Place piece
         */

        Node square = getSquare(x, y);
        if (!(square instanceof ImageView imageView)) {
            System.out.println("Image chess.view.View not found");
            return;
        }

        imageView.setImage(pieceImage);
    }

    public void displayPromotionOptions(TeamColor teamColor) {
        /* Possible promotion pieces */
        PieceType[] promotionPieces = new PieceType[] {PieceType.QUEEN, PieceType.KNIGHT, PieceType.ROOK, PieceType.BISHOP};


        LocatedImage[] pieceImages = new LocatedImage[promotionPieces.length];
        for (int i = 0; i < promotionPieces.length; i++) {
            pieceImages[i] = getLocatedPieceImage(teamColor, promotionPieces[i]);
        }

        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(5, 5,5 , 5));
        flowPane.setVgap(4);
        flowPane.setHgap(4);
        /* Center elements */
        flowPane.setRowValignment(VPos.CENTER);
        flowPane.setAlignment(Pos.CENTER);
        /* Set dimensions of popup */
        flowPane.setMaxWidth(TILE_SIZE * 3);
        flowPane.setMaxHeight(TILE_SIZE);
        flowPane.setPrefWrapLength(TILE_SIZE * 3);
        flowPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Image pieceImage : pieceImages) {
            ImageView imageView = new ImageView(pieceImage);
            imageView.setOnMousePressed(promotionPieceOnMousePressedEventHandler);
            flowPane.getChildren().add(imageView);
        }

        this.root.getChildren().add(flowPane);
    }

    public void disablePromotionOptions() {
        this.root.getChildren().remove(this.root.getChildren().size() - 1);
    }
}
